package com.pascoapp.wba02_android.dataFetching;

import com.google.firebase.database.Query;

import java.util.List;

import trikita.jedux.Action;

/**
 * Created by peter on 8/4/16.
 */

public class CourseDatabaseActions {

    public enum ActionType {
        REQUEST_COURSE,
        RECEIVE_COURSE_WITH_SUCCESS,
        RECEIVE_COURSE_WITH_FAILURE,

        REQUEST_LIST_OF_COURSES,
        RECEIVE_LIST_OF_COURSES_WITH_SUCCESS,
        RECEIVE_LIST_OF_COURSES_WITH_FAILURE
    }

    public static Action requestCourse(String key) {
        return new Action<>(ActionType.REQUEST_COURSE,
                key);
    }

    public static Action receiveCourseWithSuccess(Course course) {
        return new Action<>(ActionType.RECEIVE_COURSE_WITH_SUCCESS,
                course);
    }

    public static Action receiveCourseWithFailure(String databaseErrorMessage) {
        return new Action<>(ActionType.RECEIVE_COURSE_WITH_FAILURE,
                databaseErrorMessage);
    }


    public static Action requestListOfCourses(Query key) {
        return new Action<>(ActionType.REQUEST_LIST_OF_COURSES,
                key);
    }

    public static Action receiveListOfCoursesWithSuccess(List<Course> courses) {
        return new Action<>(ActionType.RECEIVE_LIST_OF_COURSES_WITH_SUCCESS,
                courses);
    }

    public static Action receiveListOfCoursesWithFailure(String databaseErrorMessage) {
        return new Action<>(ActionType.RECEIVE_LIST_OF_COURSES_WITH_FAILURE,
                databaseErrorMessage);
    }
}
