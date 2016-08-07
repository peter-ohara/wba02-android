package com.pascoapp.wba02_android.services.courses;

import com.google.firebase.database.Query;
import com.pascoapp.wba02_android.Actions.ActionType;

import java.util.List;

import trikita.jedux.Action;

/**
 * Created by peter on 8/4/16.
 */

public class CourseActions {

    public static Action courseRequestInitiated(String key) {
        return new Action<>(ActionType.COURSE_REQUEST_INITIATED,
                key);
    }

    public static Action courseRequestSucceeded(Course course) {
        return new Action<>(ActionType.COURSE_REQUEST_SUCCESSFUL,
                course);
    }

    public static Action courseRequestFailed(String databaseErrorMessage) {
        return new Action<>(ActionType.COURSE_REQUEST_FAILED,
                databaseErrorMessage);
    }


    public static Action listCourseRequestInitiated(Query key) {
        return new Action<>(ActionType.LIST_COURSE_REQUEST_INITIATED,
                key);
    }

    public static Action listCourseRequestSucceeded(List<Course> courses) {
        return new Action<>(ActionType.LIST_COURSE_REQUEST_SUCCESSFUL,
                courses);
    }

    public static Action listCourseRequestFailed(String databaseErrorMessage) {
        return new Action<>(ActionType.LIST_COURSE_REQUEST_FAILED,
                databaseErrorMessage);
    }
}
