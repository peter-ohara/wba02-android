package com.pascoapp.wba02_android.testDetail;

import android.support.annotation.NonNull;

import com.pascoapp.wba02_android.data.test.Test;
import com.pascoapp.wba02_android.data.test.TestRepository;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

class TestDetailPresenter implements TestDetailContract.UserActionsListener {

    private final TestRepository mTestRepository;
    private final TestDetailContract.View mTestDetailView;
    private Test mTest;

    TestDetailPresenter(
            @NonNull TestRepository testRepository,
            @NonNull TestDetailContract.View testDetailView) {
        mTestRepository = checkNotNull(testRepository, "testRepository cannot be null");
        mTestDetailView = checkNotNull(testDetailView, "testDetailView cannot be null");
    }


    @Override
    public void openTest(@NonNull Test test) {
        if (test == null) {
            // TODO: Implement ShowMissingTest
            //mTestDetailView.showMissingTest();
            return;
        }

        mTestDetailView.setProgressIndicator(false);
        mTest = test;
        showTest(mTest);
    }

    @Override
    public void takeTest() {
        checkNotNull(mTest);
        mTestDetailView.showTakeTestUi(mTest);
    }


    private void showTest(Test test) {
        String courseCode = test.courseCode;
        String courseName = test.courseName;
        String testName = test.testName;
        String testDuration = test.testDuration;
        List<String> instructions = test.instructions;


        if (courseCode != null && courseCode.isEmpty()) {
            mTestDetailView.hideCourseCode();
        } else {
            mTestDetailView.showCourseCode(courseCode);
        }

        if (courseName != null && courseName.isEmpty()) {
            mTestDetailView.hideCourseName();
        } else {
            mTestDetailView.showCourseName(courseName);
        }

        if (testName != null && testName.isEmpty()) {
            mTestDetailView.hideTestName();
        } else {
            mTestDetailView.showTestName(testName);
        }

        if (testDuration != null && test.testDuration.isEmpty()) {
            mTestDetailView.hideTestDuration();
        } else {
            mTestDetailView.showTestDuration(testDuration);
        }

        mTestDetailView.showInstructions(instructions);
    }
}
