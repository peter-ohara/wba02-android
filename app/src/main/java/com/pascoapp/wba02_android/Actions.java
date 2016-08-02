package com.pascoapp.wba02_android;

import com.pascoapp.wba02_android.firebasePojos.Course;
import com.pascoapp.wba02_android.firebasePojos.Test;
import com.pascoapp.wba02_android.takeTest.TestViewModel;

import java.util.List;

import trikita.jedux.Action;

/**
 * Created by peter on 7/31/16.
 */

public class Actions {

    public enum ActionType {
        // Main screen actions
        SHOW_SCREEN,
        SELECT_COURSE,

        // Course Network Actions
        INVALIDATE_COURSE,
        REQUEST_COURSES,
        RECEIVE_COURSES_WITH_SUCCEESS,
        RECEIVE_COURSES_WITH_FAILURE,

        // Test Network Actions
        REQUEST_TESTS,
        RECEIVE_TESTS_WITH_SUCCEESS,
        RECEIVE_TESTS_WITH_FAILURE,
    }

    public static Action showScreen(Screens screen) {
        return new Action<>(ActionType.SHOW_SCREEN,
                screen);
    }

    public static Action selectCourse(TestViewModel testViewModel) {
        return new Action<>(ActionType.SELECT_COURSE,
                testViewModel.getCourseCode());
    }

    public static Action requestCourses(String key) {
        return new Action<>(ActionType.REQUEST_COURSES,
                key);
    }

    public static Action receiveCourseWithSuccess(List<Course> courses) {
        return new Action<>(ActionType.RECEIVE_COURSES_WITH_SUCCEESS,
                courses);
    }

    public static Action receiveCourseWithFailure(String databaseError) {
        return new Action<>(ActionType.RECEIVE_COURSES_WITH_FAILURE,
                databaseError);
    }

    public static Action requestTests(String courseKey) {
        return new Action<>(ActionType.REQUEST_TESTS,
                courseKey);
    }

    public static Action receiveTestsWithSuccess(List<Test> tests) {
        return new Action<>(ActionType.RECEIVE_TESTS_WITH_SUCCEESS,
                tests);
    }

    public static Action receiveTestsWithFailure(String databaseError) {
        return new Action<>(ActionType.RECEIVE_TESTS_WITH_SUCCEESS,
                databaseError);
    }
}
