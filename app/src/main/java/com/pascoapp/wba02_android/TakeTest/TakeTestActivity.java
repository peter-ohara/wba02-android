package com.pascoapp.wba02_android.TakeTest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.pascoapp.wba02_android.Question.Question;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.Test.Test;

import java.util.ArrayList;
import java.util.List;

public class TakeTestActivity extends AppCompatActivity implements QuestionFragment.OnFragmentInteractionListener {

    public static final String EXTRA_TEST_ID =
            "com.pascoapp.wba02_android.TakeTest.TakeTestActivity.testId";

    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;
    private ArrayList<Question> mQuestions;
    private ProgressBar loadingIndicator;
    private View coordinatorLayoutView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);
        coordinatorLayoutView = findViewById(R.id.snackbarPosition);
        mPager = (ViewPager) findViewById(R.id.viewpager);

        mQuestions = new ArrayList<>();
        mPagerAdapter = new QuestionsPagerAdapter(getSupportFragmentManager(), mQuestions);
        mPager.setAdapter(mPagerAdapter);

        String testId = getTestId();
        getQuestions(testId);
    }

    public String getTestId() {
        Intent intent = getIntent();
        return intent.getStringExtra(EXTRA_TEST_ID);
    }

    private void getQuestions(final String testId) {
        loadingIndicator.setVisibility(View.VISIBLE);

        ParseQuery<Question> query = Question.getQuery();

        query.whereEqualTo("test",
                ParseObject.createWithoutData(Test.class, testId));

        query.findInBackground(new FindCallback<Question>() {
            @Override
            public void done(List<Question> questions, ParseException e) {
                loadingIndicator.setVisibility(View.GONE);
                if (e == null) {
                    mQuestions.clear();
                    mQuestions.addAll(questions);
                    mPagerAdapter.notifyDataSetChanged();
                } else {
                    System.out.println("Questions" + e.getCode() + " : " + e.getMessage());
                    Snackbar.make(coordinatorLayoutView,
                            e.getCode() + " : " + e.getMessage(),
                            Snackbar.LENGTH_LONG)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    getQuestions(testId);
                                }
                            }).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // User is looking at the first question, allow system to return to parent activity
            super.onBackPressed();
        } else {
            // Show the previous question
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void onPrevious() {
        if (mPager.getCurrentItem() == 0) {
            // User is looking at the first question, so do nothing
            // TODO: Do something to make user know he's at the first item.
        } else {
            // Show the previous question
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void onNext() {
        if (mPager.getCurrentItem() == mQuestions.size()) {
            // User is looking at the last question, ask whether he wants to score the test
            // TODO: Call code for scoring test.
        } else {
            // Show the previous question
            mPager.setCurrentItem(mPager.getCurrentItem() + 1);
        }
    }
}
