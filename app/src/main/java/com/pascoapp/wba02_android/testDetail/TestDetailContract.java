package com.pascoapp.wba02_android.testDetail;

import android.support.annotation.NonNull;

import com.pascoapp.wba02_android.data.test.Test;

import java.util.List;


class TestDetailContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showTakeTestUi(Test test);

        void hideCourseCode();

        void showCourseCode(String courseCode);

        void hideCourseName();

        void showCourseName(String courseName);

        void hideTestName();

        void showTestName(String testname);

        void hideTestDuration();

        void showTestDuration(String testDuration);

        void showInstructions(List<String> instructions);

        void showError(String errorMessage);
    }

    interface UserActionsListener {

        void openTest(@NonNull Test test);

        void takeTest();
    }
}
