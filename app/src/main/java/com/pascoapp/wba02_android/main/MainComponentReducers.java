package com.pascoapp.wba02_android.main;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.ImmutableState;
import com.pascoapp.wba02_android.State;
import com.pascoapp.wba02_android.services.courses.Course;

import java.util.List;

import trikita.jedux.Action;

import static com.pascoapp.wba02_android.Actions.ActionType;

public class MainComponentReducers {

    public static State boughtCoursesRequestInitiated(Action action, State oldState) {
        ActionType type = (ActionType) action.type;
        switch(type) {
            case BOUGHT_COURSES_REQUEST_INITIATED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(true)
                        .build();
            default:
                return oldState;
        }
    }

    public static State boughtCoursesRequestSuccessful(Action action, State oldState) {
        ActionType type = (ActionType) action.type;
        switch(type) {
            case BOUGHT_COURSES_REQUEST_SUCCESSFUL:
                List<Course> courses = (List<Course>) action.value;
                List<String> courseKeys = Stream.of(courses)
                        .map(course -> course.getKey())
                        .collect(Collectors.toList());
                return ImmutableState.builder()
                        .from(oldState)
                        // Merge new Course Data
                        .putAllCourses(Helpers.convertToMap(courses))
                        // Set isFetching Flag to false
                        .isFetching(false)
                        .build();
            default:
                return oldState;
        }
    }

    public static State boughtCoursesRequestFailed(Action action, State oldState) {
        ActionType type = (ActionType) action.type;
        switch(type) {
            case BOUGHT_COURSES_REQUEST_FAILED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(false)
                        .displayErrorMessage((String) action.value)
                        .build();
            default:
                return oldState;
        }
    }

}
