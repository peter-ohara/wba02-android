package com.pascoapp.wba02_android.takeTest;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.databinding.ActivityTakeTestBinding;
import com.pascoapp.wba02_android.takeTest.TestOverview.TestOverviewFragment;
import com.pascoapp.wba02_android.firebasePojos.Question;
import com.pascoapp.wba02_android.firebasePojos.Test;
import com.pascoapp.wba02_android.takeTest.TestSection.QuestionComparator;
import com.pascoapp.wba02_android.takeTest.TestSection.TestSectionFragment;

import java.util.ArrayList;
import java.util.Collections;

public class TakeTestActivity extends AppCompatActivity
        implements TestOverviewFragment.OnFragmentInteractionListener,
        TestSectionFragment.OnFragmentInteractionListener {

    public static final String EXTRA_TEST_KEY =
            "com.pascoapp.wba02_android.takeTest.TakeTestActivity.testId";
    public static final String EXTRA_TEST_TITLE =
            "com.pascoapp.wba02_android.takeTest.TakeTestActivity.testTitle";
    public static final String EXTRA_TEST_POJO =
            "com.pascoapp.wba02_android.takeTest.TakeTestActivity.testPojo";

    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;
    private ArrayList<Question> mQuestions;
    private ProgressBar loadingIndicator;
    private View coordinatorLayoutView;
    private DatabaseReference mQuestionsRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTakeTestBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_take_test);
        binding.setTest(new TestViewModel());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);
//        coordinatorLayoutView = findViewById(R.id.snackbarPosition);
//        mPager = (ViewPager) findViewById(R.id.viewpager);
//        mPager.setOffscreenPageLimit(10);
//
//        showTestOverview();
//
//        mQuestions = new ArrayList<>();
//        mPagerAdapter = new QuestionsPagerAdapter(getSupportFragmentManager(), getTest(), mQuestions);
//        mPager.setAdapter(mPagerAdapter);

        showTestOverview();

//        String testId = getTestId();
//        getQuestions(testId);
    }

    private void showTestOverview() {
        // Create fragment and give it an argument specifying the article it should show
        TestOverviewFragment newFragment = TestOverviewFragment.newInstance(new Test());
        //Bundle args = new Bundle();
        //args.putInt(TestOverviewFragment.ARG_POSITION, position);
        //newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }

    private void showSectionScreen() {

    }

    private void showMcqScreen() {

    }

    private void showEssayScreen() {

    }

    private void showFillInScreen() {

    }

    private void showEndScreen() {

    }

    private void showScoreScreen() {

    }

    public String getTestId() {
        Intent intent = getIntent();
        return intent.getStringExtra(EXTRA_TEST_KEY);
    }

    public Test getTest() {
        Intent intent = getIntent();
        return (Test) intent.getSerializableExtra(EXTRA_TEST_POJO);
    }

    private void getQuestions(final String testKey) {
        loadingIndicator.setVisibility(View.VISIBLE);

        mQuestionsRef = FirebaseDatabase.getInstance().getReference().child("questions");
        Query questionsQuery = mQuestionsRef.orderByChild("test").equalTo(testKey);
        questionsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mQuestions.clear();
                for (DataSnapshot questionSnapshot: dataSnapshot.getChildren()) {
                    Question question = questionSnapshot.getValue(Question.class);
                    question.key = dataSnapshot.getKey();
                    mQuestions.add(question);
                }

                // Sort by ascending question number
                Collections.sort(mQuestions, new QuestionComparator());

                mPagerAdapter.notifyDataSetChanged();
                loadingIndicator.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
