package com.pascoapp.wba02_android;

import com.pascoapp.wba02_android.dataFetching.Course;
import com.pascoapp.wba02_android.dataFetching.DatabaseManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import trikita.jedux.Action;
import trikita.jedux.Store;


/**
 * Created by peter on 7/30/16.
 */

public class RootReducer implements Store.Reducer<Action, State> {

    @Override
    public State reduce(Action action, State oldState) {
        State newSTate = oldState;

        newSTate = showScreenReducer(action, oldState);
        if (!newSTate.equals(oldState)) { return newSTate; }

        newSTate = selectCourseReducer(action, oldState);
        if (!newSTate.equals(oldState)) { return newSTate; }

        DatabaseManager<Course> databaseManager = new DatabaseManager(Course.class);

        newSTate = databaseManager.requestInitiatedReducer(action, oldState);
        if (!newSTate.equals(oldState)) { return newSTate; }

        newSTate = databaseManager.requestSuccessfulReducer(action, oldState);
        if (!newSTate.equals(oldState)) { return newSTate; }

        newSTate = databaseManager.requestFailedReducer(action, oldState);
        if (!newSTate.equals(oldState)) { return newSTate; }

        newSTate = databaseManager.listRequestInitiatedReducer(action, oldState);
        if (!newSTate.equals(oldState)) { return newSTate; }

        newSTate = databaseManager.listRequestSuccessfulReducer(action, oldState);
        if (!newSTate.equals(oldState)) { return newSTate; }

        newSTate = databaseManager.listRequestFailedReducer(action, oldState);
        if (!newSTate.equals(oldState)) { return newSTate; }

        return newSTate;
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

}
