package com.pascoapp.wba02_android.views.takeTest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.services.courses.Course;
import com.pascoapp.wba02_android.services.courses.Courses;
import com.pascoapp.wba02_android.services.questions.Question;
import com.pascoapp.wba02_android.services.questions.Questions;
import com.pascoapp.wba02_android.services.tests.Test;
import com.pascoapp.wba02_android.services.tests.Tests;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

public class TakeTestActivity extends AppCompatActivity {

    public static final String EXTRA_TEST_KEY = "com.pascoapp.wba02_android.testKey";

    public static String POSITION = "POSITION";

    private ArrayList<Question> mQuestions = new ArrayList<>();
    private PagerAdapter mPagerAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.loading_indicator)
    AVLoadingIndicatorView loadingIndicator;
    @BindView(R.id.snackbarPosition)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.sliding_tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager mPager;

    private Test test;
    private Course course;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_test);
        ButterKnife.bind(this);

        setupToolbar();

        setupViewPager();

        refreshData(getTestKey());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, tabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // TODO: Figure out why this call doesn't work and as a consequence
        // changing screen orientation does not preserve the users position
        Log.d("TakeTestActivity", "onRestoreInstanceState: " + mPager.getCurrentItem());
        Log.d("TakeTestActivity", "onRestoreInstanceState: " + savedInstanceState.getInt(POSITION));
        mPager.setCurrentItem(savedInstanceState.getInt(POSITION));
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        setTitle("");
    }

    private void setupViewPager() {
        mPager.setOffscreenPageLimit(10);
        mPagerAdapter = new QuestionsPagerAdapter(getSupportFragmentManager(), mQuestions);
        mPager.setAdapter(mPagerAdapter);
        // Give the TabLayout the ViewPager
        tabLayout.setupWithViewPager(mPager);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public String getTestKey() {
        Intent intent = getIntent();
        return intent.getStringExtra(EXTRA_TEST_KEY);
    }


    private void refreshData(String testKey) {
        loadingIndicator.show();
        Tests.fetchTest(testKey)
                .concatMap(test -> {
                    this.test = test;
                    return Courses.fetchCourse(test.getCourseKey());
                })
                .concatMap(course -> {
                    this.course = course;
                    return Observable.from(
                            Helpers.sortMapByValue(test.getQuestionKeys()).keySet()
                    );
                })
                .concatMap(questionKey -> Questions.fetchQuestion(questionKey) )
                .toList()
                .subscribe(questions -> {
                    loadingIndicator.hide();
                    setTitle(course.getCode() + " " + Helpers.getTestName(test));

                    Question endPageQuestion = new Question();
                    endPageQuestion.setType(QuestionsPagerAdapter.TYPE_END_PAGE);
                    endPageQuestion.setTitle("END");
                    questions.add(endPageQuestion);

                    mQuestions.clear();
                    mQuestions.addAll(questions);
                    mPagerAdapter.notifyDataSetChanged();
                }, firebaseException -> {
                    loadingIndicator.hide();
                    Snackbar.make(coordinatorLayout, firebaseException.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Retry", view -> refreshData(testKey))
                            .show();
                });
    }

    public void moveToPreviousScreen() {
        if (mPager.getCurrentItem() == 0) {
            quit();
        } else {
            // Show the previous question
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public void moveToNextScreen() {
        if (mPager.getCurrentItem() == mPagerAdapter.getCount() - 1) {
            Toast.makeText(this, "Should Quit", Toast.LENGTH_SHORT).show();
        } else {
            // Show the previous question
            mPager.setCurrentItem(mPager.getCurrentItem() + 1);
        }
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            quit();
        } else {
            // Show the previous question
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public void quit() {
        super.onBackPressed();
    }

}