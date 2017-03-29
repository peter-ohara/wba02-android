package com.pascoapp.wba02_android.buyTests;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.pascoapp.wba02_android.Injection;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.buyTests.adapter.TestsListAdapter;
import com.pascoapp.wba02_android.data.test.Test;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.pascoapp.wba02_android.store.adapter.CourseListAdapter.EXTRA_COURSE_ID;

public class BuyTestsActivity extends AppCompatActivity implements BuyTestsContract.View {

    @BindView(R.id.snackbarPosition) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.loading_indicator) AVLoadingIndicatorView loadingIndicator;

    @BindView(R.id.buy_button) AppCompatButton buyTests;

    @BindView(R.id.course_code) TextView courseCodeTextView;
    @BindView(R.id.course_name) TextView courseNameTextView;
    @BindView(R.id.test_number) TextView testsNumberTextView;
    @BindView(R.id.midsems_number) TextView midsemsNumberTextView;
    @BindView(R.id.class_tests_number) TextView classTestsNumberTextView;
    @BindView(R.id.end_of_sem_number) TextView endOfSemNumberTextView;
    @BindView(R.id.assignments_number) TextView assignmentsNumberTextView;

    @BindView(R.id.price) TextView priceTextView;
    @BindView(R.id.test_list) RecyclerView buyTestView;

    BuyTestsContract.UserActionsListener mUserActionsListener;

    private TestsListAdapter mTestsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserActionsListener = new BuyTestsPresenter(Injection.provideCourseRepository(this), this);

        setContentView(R.layout.activity_buy_tests);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        buyTestView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        buyTestView.setLayoutManager(linearLayoutManager);
        mTestsListAdapter = new TestsListAdapter(new ArrayList<>(), mTestItemListener);
        buyTestView.setAdapter(mTestsListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUserActionsListener.openCourse(getCourseId());
    }

    public Integer getCourseId() {
        return getIntent().getIntExtra(EXTRA_COURSE_ID, -1);
    }

    @OnClick(R.id.buy_button)
    public void buyTests() {
        mUserActionsListener.buyTest(mTestsListAdapter.getSelectedTests());
    }

    /**
     * Listener for clicks on tests in the RecyclerView.
     */
    private TestItemListener mTestItemListener = this::updateSelectedTestsPrice;

    public void updateSelectedTestsPrice(){
        List<Test> tests = mTestsListAdapter.getSelectedTests();
        int currentTestPrice = 0;
        for(int i = 0; i < tests.size(); i++){
            currentTestPrice += tests.get(i).pascoCredits;
        }
        priceTextView.setText(currentTestPrice + " PC");
    }

    @Override
    public void setProgressIndicator(boolean active) {
        if (active) {
            loadingIndicator.post(() -> loadingIndicator.show());
        } else {
            loadingIndicator.post(() -> loadingIndicator.hide());
        }
    }

    @Override
    public void showCourseCode(String courseCode) {
        courseCodeTextView.setText(courseCode);
    }

    @Override
    public void showCourseName(String courseName) {
        courseNameTextView.setText(courseName);
    }

    @Override
    public void showQuizCount(String quizCount) {
        testsNumberTextView.setText(quizCount);
    }

    @Override
    public void showMidsemCount(String midsemCount) {
        midsemsNumberTextView.setText(midsemCount);
    }

    @Override
    public void showClassTestCount(String classTestCount) {
        classTestsNumberTextView.setText(classTestCount);
    }

    @Override
    public void showEndOfSemCount(String endOfSemCount) {
        endOfSemNumberTextView.setText(endOfSemCount);
    }

    @Override
    public void showAssignmentCount(String assignmentCount) {
        assignmentsNumberTextView.setText(assignmentCount);
    }

    @Override
    public void showTests(List<Test> tests) {
        mTestsListAdapter.replaceData(tests);
    }

    @Override
    public void showError(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG)
                .setAction("Retry", view -> mUserActionsListener.openCourse(getCourseId()) )
                .show();
    }

    public interface TestItemListener {

        void onTestSelected();
    }
}
