package com.pascoapp.wba02_android.takeTest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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
}
