package com.pascoapp.wba02_android.testDetail;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.pascoapp.wba02_android.Injection;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.data.test.Test;
import com.pascoapp.wba02_android.takeTest.TakeTestActivity;
import com.pascoapp.wba02_android.testDetail.adapter.InstructionAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestDetailActivity extends AppCompatActivity implements TestDetailContract.View {

    public static final String EXTRA_TEST_ID = "com.pascoapp.wba02_android.quizId";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.lowerContent) View lowerContent;
    @BindView(R.id.snackbarPosition) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.loading_indicator) AVLoadingIndicatorView loadingIndicator;
    @BindView(R.id.courseCode) TextView courseCodeView;
    @BindView(R.id.courseName) TextView courseNameView;
    @BindView(R.id.testName) TextView testNameView;
    @BindView(R.id.testDuration) TextView testDurationView;
    @BindView(R.id.instructionsList) RecyclerView instructionsRecyclerView;
    @BindView(R.id.bottomBar) View bottomBar;

    private TestDetailContract.UserActionsListener mActionsListener;

    private InstructionAdapter instructionsAdapter;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActionsListener = new TestDetailPresenter(Injection.provideTestsRepository(this), this);

        setContentView(R.layout.activity_test_overview);
        ButterKnife.bind(this);
        setupToolbar();
        setUpInstructionsRecyclerView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Test test = getTest();
        mActionsListener.openTest(test);
    }

    @NonNull
    private Test getTest() {
        return getIntent().getParcelableExtra(EXTRA_TEST_ID);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setUpInstructionsRecyclerView() {
        instructionsRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        instructionsRecyclerView.setLayoutManager(layoutManager2);

        instructionsAdapter = new InstructionAdapter(new ArrayList<>());
        instructionsRecyclerView.setAdapter(instructionsAdapter);
    }

    @OnClick(R.id.bottomBar)
    public void startTakeTestActivity(View view) {
        mActionsListener.takeTest();
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
    public void showTakeTestUi(Test test) {
        Intent intent = new Intent(this, TakeTestActivity.class);
        intent.putExtra(TakeTestActivity.EXTRA_TEST_ID, test);
        startActivity(intent);
    }

    @Override
    public void hideCourseCode() {
        courseCodeView.setVisibility(View.GONE);
    }

    @Override
    public void showCourseCode(String courseCode) {
        courseCodeView.setVisibility(View.VISIBLE);
        courseCodeView.setText(courseCode);
    }

    @Override
    public void hideCourseName() {
        courseNameView.setVisibility(View.GONE);
    }

    @Override
    public void showCourseName(String courseName) {
        courseNameView.setVisibility(View.VISIBLE);
        courseNameView.setText(courseName);
    }

    @Override
    public void hideTestName() {
        testNameView.setVisibility(View.GONE);
    }

    @Override
    public void showTestName(String testName) {
        testNameView.setVisibility(View.VISIBLE);
        testNameView.setText(testName);
    }

    @Override
    public void hideTestDuration() {
        testDurationView.setVisibility(View.GONE);
    }

    @Override
    public void showTestDuration(String testDuration) {
        testDurationView.setVisibility(View.VISIBLE);
        testDurationView.setText(testDuration);
    }

    @Override
    public void showInstructions(List<String> instructions) {
        instructionsAdapter.replaceData(instructions);
    }

    @Override
    public void showError(String errorMessage) {
        Snackbar.make(coordinatorLayout, errorMessage, Snackbar.LENGTH_LONG)
                .setAction("Retry", view -> mActionsListener.openTest(getTest()))
                .show();
    }


}
