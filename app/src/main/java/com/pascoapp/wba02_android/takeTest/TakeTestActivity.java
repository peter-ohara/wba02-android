package com.pascoapp.wba02_android.takeTest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.R;
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
    private Button bottomButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActivityTakeTestBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_take_test);
//        TestViewModel testViewModel = new TestViewModel(TakeTestActivity.this, getTestKey());
//        binding.setTest(testViewModel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bottomButton = (Button) findViewById(R.id.bottomBar);

        showTestOverview(getTestKey());


//        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);
//        coordinatorLayoutView = findViewById(R.id.snackbarPosition);
//        mPager = (ViewPager) findViewById(R.id.viewpager);
//        mPager.setOffscreenPageLimit(10);
//
//        showTestOverview();
//
//        mQuestions = new ArrayList<>();
//        mPagerAdapter = new QuestionsPagerAdapter(getSupportFragmentManager(), getTestKey(), mQuestions);
//        mPager.setAdapter(mPagerAdapter);

//        String testKey = getTestKey();
//        getQuestions(testKey);
    }

    private void showTestOverview(String testKey) {
        // Create fragment and give it an argument specifying the article it should show
        TestOverviewFragment newFragment = TestOverviewFragment.newInstance(testKey);
        //Bundle args = new Bundle();
        //args.putInt(TestOverviewFragment.ARG_POSITION, position);
        //newFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the userKey can navigate back
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
        bottomButton.setText("Start &#10175;");
        bottomButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.arrow_right, 0);
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

    public String getTestKey() {
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
        Query questionsQuery = mQuestionsRef.orderByChild("testKey").equalTo(testKey);
        questionsQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mQuestions.clear();
                for (DataSnapshot questionSnapshot: dataSnapshot.getChildren()) {
                    Question question = questionSnapshot.getValue(Question.class);
                    question.key = dataSnapshot.getKey();
                    mQuestions.add(question);
                }

                // Sort by ascending questionKey number
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
            // Show the previous questionKey
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
