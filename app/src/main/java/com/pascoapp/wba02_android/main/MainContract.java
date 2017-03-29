package com.pascoapp.wba02_android.main;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.pascoapp.wba02_android.data.course.Course;
import com.pascoapp.wba02_android.data.test.Test;
import com.pascoapp.wba02_android.data.user.User;
import com.pascoapp.wba02_android.main.adapter.MainScreenItem;

import java.util.List;

interface MainContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showMainItems(List<MainScreenItem> mainScreenItems);

        void showTestDetailUi(Test test);

        void showCourseDetailUi(Course course);

        void showError(String message);

        void showStore();

        void showInvite();

        void showPascoUpload();

        void showFeedbackForm();

        void showHelp();

        void showSignIn();
    }

    interface UserActionsListener {

        void loadTests(boolean forceUpdate);

        void openCourseDetails(Course requestedCourse);

        void openTestDetails(@NonNull Test requestedTest);

        void openStore();

        void openInvite();

        void openPascoUpload();

        void openFeedbackForm();

        void openHelp();

        void logout(FragmentActivity activity);
    }
}
