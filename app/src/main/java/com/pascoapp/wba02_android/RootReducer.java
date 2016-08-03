package com.pascoapp.wba02_android;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.pascoapp.wba02_android.firebasePojos.Course;
import com.pascoapp.wba02_android.firebasePojos.Test;

import java.util.List;
import java.util.Map;

import trikita.jedux.Action;
import trikita.jedux.Store;
/**
 * Created by peter on 7/30/16.
 */

public class RootReducer implements Store.Reducer<Action, State> {
    @Override
    public State reduce(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch (type) {
            case SHOW_SCREEN:
                return showScreenReducer(action, oldState);
            case SELECT_COURSE:
                return selectCourseReducer(action, oldState);
            case INVALIDATE_COURSE:
                return invalidateCourseReducer(action, oldState);
            case REQUEST_COURSES:
                return requestCourseReducer(action, oldState);
            case RECEIVE_COURSES_WITH_SUCCEESS:
                return receiveCourseWithSuccessReducer(action, oldState);
            case RECEIVE_COURSES_WITH_FAILURE:
                return receiveCourseWithFailureReducer(action, oldState);
            case REQUEST_TESTS:
                return requestTestReducer(action, oldState);
            case RECEIVE_TESTS_WITH_SUCCEESS:
                return receiveTestWithSuccessReducer(action, oldState);
            case RECEIVE_TESTS_WITH_FAILURE:
                return receiveTestWithFailureReducer(action, oldState);
            default:
                return oldState;
        }
    }

    private State showScreenReducer(Action action, State oldState) {
        return ImmutableState.builder()
                .from(oldState)
                .currentScreen((Screens) action.value)
                .build();
    }

    private State selectCourseReducer(Action action, State oldState) {
        return ImmutableState.builder()
                .from(oldState)
                .selectedCourse((String) action.value)
                .build();
    }

    private State invalidateCourseReducer(Action action, State oldState) {
        return oldState;
    }

    private State requestCourseReducer(Action action, State oldState) {
        ImmutableMainScreen mainScreen1 = ImmutableMainScreen.builder()
                .from(oldState.mainScreen())
                .isFetching(true)
                .build();
        return ImmutableState.builder()
                .from(oldState)
                .mainScreen(mainScreen1)
                .build();
    }

    private State receiveCourseWithSuccessReducer(Action action, State oldState) {
        List<Course> courses = (List<Course>) action.value;
        Map<String, Course> coursesMap = Stream.of(courses)
                .collect(Collectors.toMap(
                        course -> course.getKey(),
                        course -> course
                ));
        ImmutableMainScreen mainScreen2 = ImmutableMainScreen.builder()
                .from(oldState.mainScreen())
                .isFetching(false)
                .build();
        return ImmutableState.builder()
                .from(oldState)
                .mainScreen(mainScreen2)
                .courses(coursesMap)
                .build();
    }

    private State receiveCourseWithFailureReducer(Action action, State oldState) {
        return oldState;
    }

    private State requestTestReducer(Action action, State oldState) {
        ImmutableMainScreen mainScreen = ImmutableMainScreen.builder()
                .from(oldState.mainScreen())
                .isFetching(true)
                .build();
        return ImmutableState.builder()
                .from(oldState)
                .mainScreen(mainScreen)
                .build();
    }

    private State receiveTestWithSuccessReducer(Action action, State oldState) {
        List<Test> tests = (List<Test>) action.value;

        Map<String, List<String>> mainScreenItemsMap = Stream.of(tests)
                .collect(Collectors.groupingBy(
                        test -> test.getCourseKey(),
                        Collectors.mapping(test -> test.getKey(), Collectors.toList())
                ));
        Map<String, Test> testsMap = Stream.of(tests)
                .collect(Collectors.toMap(
                        test -> test.getKey(),
                        test -> test
                ));
        ImmutableMainScreen mainScreen = ImmutableMainScreen.builder()
                .from(oldState.mainScreen())
                .putAllItems(mainScreenItemsMap)
                .isFetching(false)
                .build();
        return ImmutableState.builder()
                .from(oldState)
                .mainScreen(mainScreen)
                .tests(testsMap)
                .build();
    }

    private State receiveTestWithFailureReducer(Action action, State oldState) {
        return oldState;
    }


}
