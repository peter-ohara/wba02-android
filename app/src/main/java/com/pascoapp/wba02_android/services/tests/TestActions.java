package com.pascoapp.wba02_android.services.tests;

import com.google.firebase.database.Query;
import com.pascoapp.wba02_android.Actions.ActionType;

import java.util.List;

import trikita.jedux.Action;

/**
 * Created by peter on 8/4/16.
 */

public class TestActions {

    public static Action testRequestInitiated(String key) {
        return new Action<>(ActionType.TEST_REQUEST_INITIATED,
                key);
    }

    public static Action testRequestSucceeded(Test test) {
        return new Action<>(ActionType.TEST_REQUEST_SUCCESSFUL,
                test);
    }

    public static Action testRequestFailed(String databaseErrorMessage) {
        return new Action<>(ActionType.TEST_REQUEST_FAILED,
                databaseErrorMessage);
    }


    public static Action listTestRequestInitiated(Query key) {
        return new Action<>(ActionType.LIST_TEST_REQUEST_INITIATED,
                key);
    }

    public static Action listTestRequestSucceeded(List<Test> tests) {
        return new Action<>(ActionType.LIST_TEST_REQUEST_SUCCESSFUL,
                tests);
    }

    public static Action listTestRequestFailed(String databaseErrorMessage) {
        return new Action<>(ActionType.LIST_TEST_REQUEST_FAILED,
                databaseErrorMessage);
    }
}
