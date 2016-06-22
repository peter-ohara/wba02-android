package com.pascoapp.wba02_android.takeTest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.Course;
import com.pascoapp.wba02_android.parseSubClasses.Question;

import java.util.ArrayList;

public class TakeTestActivity extends AppCompatActivity {

    public static final String EXTRA_TEST_ID =
            "com.pascoapp.wba02_android.takeTest.TakeTestActivity.testId";
    public static final String EXTRA_TEST_TITLE =
            "com.pascoapp.wba02_android.takeTest.TakeTestActivity.testTitle";

    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;
    private ArrayList<Question> mQuestions;
    private ProgressBar loadingIndicator;
    private View coordinatorLayoutView;
    private DatabaseReference mQuestionsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(getTestTitle());

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

    public String getTestTitle() {
        Intent intent = getIntent();
        return intent.getStringExtra(EXTRA_TEST_TITLE);
    }

    private void getQuestions(final String testId) {
        loadingIndicator.setVisibility(View.VISIBLE);

        mQuestionsRef = FirebaseDatabase.getInstance().getReference().child("questions");
        mQuestionsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mQuestions.clear();
                for (DataSnapshot questionSnapshot: dataSnapshot.getChildren()) {
                    Question question = questionSnapshot.getValue(Question.class);
                    mQuestions.add(question);
                }
                mPagerAdapter.notifyDataSetChanged();
                loadingIndicator.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

//        ParseQuery<Question> query = Question.getQuery();
//
//        query.whereEqualTo("test",
//                ParseObject.createWithoutData(Test.class, testId));
//
//        query.orderByAscending("number");
//
//        query.findInBackground(new FindCallback<Question>() {
//            @Override
//            public void done(List<Question> questions, ParseException e) {
//                if (e == null) {
//                    if (questions.size() != 0) {
//                        mQuestions.clear();
//                        mQuestions.addAll(questions);
//
//                        mPagerAdapter.notifyDataSetChanged();
//                    } else {
//                        // TODO: showEmptyView
//                        // showEmptyView();
//                    }
//                    loadingIndicator.setVisibility(View.GONE);
//                } else if (e.getCode() == 120) {
//                    // Result not cached Error. Ignore it
//                } else {
//                    Snackbar.make(coordinatorLayoutView,
//                            e.getCode() + " : " + e.getMessage(),
//                            Snackbar.LENGTH_INDEFINITE)
//                            .setAction("Retry", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    getQuestions(testId);
//                                }
//                            }).show();
//                    loadingIndicator.setVisibility(View.GONE);
//                }
//            }
//        });
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

    private void quit() {
        super.onBackPressed();
    }
}
