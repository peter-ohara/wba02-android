package com.pascoapp.wba02_android.main;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.firebase.ui.auth.AuthUI;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.data.course.Course;
import com.pascoapp.wba02_android.data.course.CourseRepository;
import com.pascoapp.wba02_android.data.test.Test;
import com.pascoapp.wba02_android.main.adapter.MainScreenHeader;
import com.pascoapp.wba02_android.main.adapter.MainScreenItem;
import com.pascoapp.wba02_android.main.adapter.MainScreenTest;

import java.util.ArrayList;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.Context.MODE_PRIVATE;
import static com.google.common.base.Preconditions.checkNotNull;

class MainPresenter implements MainContract.UserActionsListener {

    private final CourseRepository mCourseRepository;
    private final MainContract.View mMainView;

    MainPresenter(
            @NonNull CourseRepository courseRepository, @NonNull MainContract.View mainView) {
        mCourseRepository = checkNotNull(courseRepository, "courseRepository cannot be null!");
        mMainView = checkNotNull(mainView, "mainView cannot be null!)");
    }

    @Override
    public void loadTests(boolean forceUpdate) {
        mMainView.setProgressIndicator(true);

        if (forceUpdate) {
            mCourseRepository.refreshData();
        }

//        // The network request might be handled in a different thread so make sure Espresso knows
//        // that the app is busy until the response is handled.
//        EspressoIdlingResource.increment(); // App is busy until further notice
        mCourseRepository.getCourses()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(courses -> {
                    mMainView.setProgressIndicator(false);
                    showMainItems(courses);
                }, e -> {
                    mMainView.setProgressIndicator(false);
                    mMainView.showError(e.getMessage());
                });
    }

    @Override
    public void openStore() {
        mMainView.showStore();
    }

    @Override
    public void openTestDetails(@NonNull Test requestedTest) {
        checkNotNull(requestedTest, "requestedTest cannot be null!");
        mMainView.showTestDetailUi(requestedTest);
    }

    @Override
    public void openInvite() {
        mMainView.showInvite();
    }

    @Override
    public void openPascoUpload() {
        mMainView.showPascoUpload();
    }

    @Override
    public void openFeedbackForm() {
        mMainView.showFeedbackForm();
    }

    @Override
    public void openHelp() {
        mMainView.showHelp();
    }

    @Override
    public void logout(FragmentActivity activity) {
        AuthUI.getInstance()
                .signOut(activity)
                .addOnCompleteListener(task -> {
                    // user is now signed out
                    clearAllPreferences(activity);
                    // TODO: Clear the view
                    // TODO: Clear the OKHTTPCache

                    mMainView.showSignIn();
                });
    }

    @Override
    public void openCourseDetails(Course requestedCourse) {
        checkNotNull(requestedCourse, "requestedCourse cannot be null!");
        mMainView.showCourseDetailUi(requestedCourse);
    }

    private void showMainItems(List<Course> courses) {
        List<MainScreenItem> items = new ArrayList<>();

        for (Course course : courses) {
            MainScreenHeader header = new MainScreenHeader(course);
            items.add(header);

            for (Test test : course.tests) {
                MainScreenTest testItem = new MainScreenTest(test);
                items.add(testItem);
            }
        }
        mMainView.showMainItems(items);
    }

    private static void clearAllPreferences(Activity activity) {
        activity.getSharedPreferences(
                activity.getString(R.string.preference_file_auth_token),
                MODE_PRIVATE).edit().clear().commit();
    }

}
