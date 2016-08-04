package com.pascoapp.wba02_android;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.pascoapp.wba02_android.dataFetching.Course;
import com.pascoapp.wba02_android.dataFetching.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        // TODO: Find a way of doing this without reflection
        Method[] methods = RootReducer.class.getDeclaredMethods();
        State currentState = oldState;
        for (Method m : methods) {
            if (m.getName().endsWith("Reducer")) {
                try {
                    currentState = (State) m.invoke(this, action, oldState);
                    if (!currentState.equals(oldState)) {
                        return currentState;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return currentState;
    }

    private State showScreenReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch (type) {
            case SHOW_SCREEN:
                return ImmutableState.builder()
                        .from(oldState)
                        .currentScreen((Screens) action.value)
                        .build();
            default:
                return oldState;
        }
    }

    private State selectCourseReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch (type) {
            case SELECT_COURSE:
                ImmutableTestOverviewComponent testOverviewComponent = ImmutableTestOverviewComponent.builder()
                        .from(oldState.testOverviewComponent())
                        .test((String) action.value)
                        .build();
                return ImmutableState.builder()
                        .from(oldState)
                        .selectedCourse((String) action.value)
                        .testOverviewComponent(testOverviewComponent)
                        .build();
            default:
                return oldState;
        }
    }

    private State invalidateCourseReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch (type) {
            case INVALIDATE_COURSE:
                return oldState;
            default:
                return oldState;
        }
    }

    private State requestCourseReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch (type) {
            case REQUEST_COURSES:
                ImmutableMainScreen mainScreen1 = ImmutableMainScreen.builder()
                        .from(oldState.mainScreen())
                        .isFetching(true)
                        .build();
                return ImmutableState.builder()
                        .from(oldState)
                        .mainScreen(mainScreen1)
                        .build();
            default:
                return oldState;
        }
    }

    private State receiveCourseWithSuccessReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch (type) {
            case RECEIVE_COURSES_WITH_SUCCEESS:
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
            default:
                return oldState;
        }
    }

    private State receiveCourseWithFailureReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch (type) {
            case RECEIVE_COURSES_WITH_FAILURE:
                return oldState;
            default:
                return oldState;
        }
    }

    private State requestTestReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch (type) {
            case REQUEST_TESTS:
                ImmutableMainScreen mainScreen = ImmutableMainScreen.builder()
                        .from(oldState.mainScreen())
                        .isFetching(true)
                        .build();
                return ImmutableState.builder()
                        .from(oldState)
                        .mainScreen(mainScreen)
                        .build();
            default:
                return oldState;
        }
    }

    private State receiveTestWithSuccessReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch (type) {
            case RECEIVE_TESTS_WITH_SUCCEESS:
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
            default:
                return oldState;
        }
    }

    private State receiveTestWithFailureReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch (type) {
            case RECEIVE_TESTS_WITH_FAILURE:
                return oldState;
            default:
                return oldState;
        }
    }
}
