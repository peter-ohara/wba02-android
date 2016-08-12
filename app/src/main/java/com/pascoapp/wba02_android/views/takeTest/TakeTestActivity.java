package com.pascoapp.wba02_android.views.takeTest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.services.questions.Question;
import com.pascoapp.wba02_android.views.discussion.DiscussionActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TakeTestActivity extends AppCompatActivity {

    public static final String EXTRA_TEST_KEY =
            "com.pascoapp.wba02_android.views.testFlow.Section.question.TakeTestActivity.testId";
    public static final String EXTRA_TEST_TITLE =
            "com.pascoapp.wba02_android.views.testFlow.Section.question.TakeTestActivity.testTitle";

    private ArrayList<Question> mQuestions = new ArrayList<>();
    private PagerAdapter mPagerAdapter;
    private DatabaseReference mQuestionsRef;

    @BindView(R.id.loading_indicator) ProgressBar loadingIndicator;
    @BindView(R.id.snackbarPosition) CoordinatorLayout coordinatorLayoutView;
    @BindView(R.id.viewpager) ViewPager mPager;
    @BindView(R.id.previousButton) Button previousButton;
    @BindView(R.id.nextButton) Button nextButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_test);
        ButterKnife.bind(this);

        setupToolbar();

        setupViewPager();

        disableAndHideButton(previousButton);
        disableAndHideButton(nextButton);

        getQuestions(getTestId());
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        setTitle(getTestTitle());
    }

    private void setupViewPager() {
        mPager.setOffscreenPageLimit(10);
        mPagerAdapter = new QuestionsPagerAdapter(getSupportFragmentManager(), mQuestions);
        mPager.setAdapter(mPagerAdapter);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    disableAndHideButton(previousButton);
                } else {
                    enableAndUnHideButton(previousButton);
                }

                if (position == mPagerAdapter.getCount() - 1) {
                    disableAndHideButton(nextButton);
                } else {
                    enableAndUnHideButton(nextButton);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void disableAndHideButton(Button button) {
        button.setVisibility(View.INVISIBLE);
        button.setEnabled(false);
    }

    private void enableAndUnHideButton(Button button) {
        button.setVisibility(View.VISIBLE);
        button.setEnabled(true);
    }

    public String getTestId() {
        Intent intent = getIntent();
        return "test1";
//        return intent.getStringExtra(EXTRA_TEST_KEY);
    }

    public String getTestTitle() {
        Intent intent = getIntent();
        return intent.getStringExtra(EXTRA_TEST_TITLE);
    }

    private void getQuestions(final String testKey) {
        loadingIndicator.setVisibility(View.VISIBLE);

        mQuestionsRef = FirebaseDatabase.getInstance().getReference().child("questions");
        Query questionsQuery = mQuestionsRef.orderByChild("testKey").equalTo(testKey);
        questionsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mQuestions.clear();
                for (DataSnapshot questionSnapshot: dataSnapshot.getChildren()) {
                    Question question = questionSnapshot.getValue(Question.class);
                    question.setKey(questionSnapshot.getKey());
                    mQuestions.add(question);
                }

                // Sort by ascending question number
                //Collections.sort(mQuestions, new QuestionComparator());

                mPagerAdapter.notifyDataSetChanged();
                Log.d("TakeTestActivity", "onDataChange: " + mQuestions);
                loadingIndicator.setVisibility(View.GONE);
                enableAndUnHideButton(nextButton);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @OnClick(R.id.previousButton)
    public void moveToPreviousScreen(View view) {
        if (mPager.getCurrentItem() == 0) {
            quit();
        } else {
            // Show the previous question
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @OnClick(R.id.nextButton)
    public void moveToNextScreen(View view) {
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

    public void openDiscussionActivity() {
        Intent intent = new Intent(this, DiscussionActivity.class);
        startActivity(intent);
    }

    public void quit() {
        super.onBackPressed();
    }

}