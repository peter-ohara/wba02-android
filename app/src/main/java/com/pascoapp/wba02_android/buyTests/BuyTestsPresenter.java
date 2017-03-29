package com.pascoapp.wba02_android.buyTests;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.pascoapp.wba02_android.data.course.Course;
import com.pascoapp.wba02_android.data.course.CourseRepository;
import com.pascoapp.wba02_android.data.test.Test;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

class BuyTestsPresenter implements BuyTestsContract.UserActionsListener {

    private final CourseRepository mCourseRepository;
    private final BuyTestsContract.View mBuyTestView;

    BuyTestsPresenter(
            @NonNull CourseRepository courseRepository, @NonNull BuyTestsContract.View buyTestView) {
        mCourseRepository = checkNotNull(courseRepository, "courseRepository cannot be null!");
        mBuyTestView = checkNotNull(buyTestView, "buyTestView cannot be null!)");
    }

    @Override
    public void openCourse(@Nullable Integer courseId) {
        if (courseId == null) {
            // TODO: Implement ShowMissingCourse
            //mBuyTestView.showMissingCourse();
            return;
        }

        mBuyTestView.setProgressIndicator(true);
        mCourseRepository.getCourse(courseId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(course -> {
                    mBuyTestView.setProgressIndicator(false);
                    showCourse(course);
                }, e -> {
                    mBuyTestView.setProgressIndicator(false);
                    mBuyTestView.showError(e.getMessage());
                });
    }

    @Override
    public void buyTest(@Nullable List<Test> tests) {
        Toast.makeText((Context) mBuyTestView,
                "Buying " + tests.size() + " tests...", Toast.LENGTH_SHORT).show();
    }

    private void showCourse(Course course) {
        String courseCode = course.code;
        String courseName = course.name;

        String quizCount = course.quizCount;
        String midsemCount = course.midsemCount;
        String classTestCount = course.classTestCount;
        String endOfSemCount = course.endOfSemCount;
        String assignmentCount = course.assignmentCount;

        List<Test> tests = course.tests;


        mBuyTestView.showCourseCode(courseCode);
        mBuyTestView.showCourseName(courseName);

        mBuyTestView.showQuizCount(quizCount);
        mBuyTestView.showMidsemCount(midsemCount);
        mBuyTestView.showClassTestCount(classTestCount);
        mBuyTestView.showEndOfSemCount(endOfSemCount);
        mBuyTestView.showAssignmentCount(assignmentCount);

        mBuyTestView.showTests(tests);
    }

}
