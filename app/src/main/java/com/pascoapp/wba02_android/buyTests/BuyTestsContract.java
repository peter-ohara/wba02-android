package com.pascoapp.wba02_android.buyTests;

import android.support.annotation.Nullable;

import com.pascoapp.wba02_android.data.test.Test;

import java.util.List;

interface BuyTestsContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showCourseCode(String courseCode);

        void showCourseName(String courseName);

        void showQuizCount(String quizCount);

        void showMidsemCount(String midsemCount);

        void showClassTestCount(String classTestCount);

        void showEndOfSemCount(String endOfSemCount);

        void showAssignmentCount(String assignmentCount);

        void showTests(List<Test> tests);

        void showError(String message);
    }

    interface UserActionsListener {

        void openCourse(@Nullable Integer courseId);

        void buyTest(@Nullable List<Test> tests);
    }
}
