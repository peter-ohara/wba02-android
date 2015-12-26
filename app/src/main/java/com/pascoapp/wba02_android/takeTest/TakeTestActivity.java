package com.pascoapp.wba02_android.takeTest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.pascoapp.wba02_android.parseSubClasses.Question;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.Test;

import java.util.ArrayList;
import java.util.List;

public class TakeTestActivity extends AppCompatActivity
        implements QuestionFragment.OnFragmentInteractionListener {

    public static final String EXTRA_TEST_ID =
            "com.pascoapp.wba02_android.takeTest.TakeTestActivity.testId";

    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;
    private ArrayList<Question> mQuestions;
    private ProgressBar loadingIndicator;
    private View coordinatorLayoutView;

    private int mScore = 0;
    private int markPerQuestion = 1;
    private int wrongAnswerPenalty = 0;     // Some tests give penalties for wrong answers
    private int mMaxScore;

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
                    if (questions.size() != 0) {
                        mQuestions.clear();
                        mQuestions.addAll(questions);

                        // Add an empty question for the score fragment
                        Question scoreQuestion = new Question();
                        scoreQuestion.setType("score");

                        mQuestions.add(scoreQuestion);

                        mPagerAdapter.notifyDataSetChanged();
                        calculateMaxScore();
                    }
                } else {
                    Snackbar.make(coordinatorLayoutView,
                            e.getCode() + " : " + e.getMessage(),
                            Snackbar.LENGTH_INDEFINITE)
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

    private void calculateMaxScore() {
        mMaxScore = mQuestions.size() - 1;  // - 1 because we added a score question to mQuestions
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // User is looking at the first question, allow system to return to parent activity
            confirmQuitting();
        } else {
            // Show the previous question
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    private void confirmQuitting() {
        new AlertDialog.Builder(TakeTestActivity.this)
                .setTitle("Confirm Quit Test")
                .setMessage("Do you really want to quit this test?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        quit();
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    private void quit() {
        super.onBackPressed();
    }


    @Override
    public void onAnsweredCorrectly() {
        mScore += markPerQuestion;
    }

    @Override
    public void onAnsweredWrongly() {
        mScore -= wrongAnswerPenalty;
    }

    @Override
    public void onNext() {
        // Move to next page
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case android.R.id.home:
                confirmQuitting();
                super.onOptionsItemSelected(item);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public int getScore() {
        return mScore;
    }

    @Override
    public int getMaxScore() {
        return mMaxScore;
    }

    @Override
    public void onEndTest() {
        // TODO: Save Scores to pass before quiting
        quit();
    }
}
