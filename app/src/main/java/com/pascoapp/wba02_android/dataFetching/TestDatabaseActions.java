package com.pascoapp.wba02_android.dataFetching;

import com.google.firebase.database.Query;

import java.util.List;

import trikita.jedux.Action;

/**
 * Created by peter on 8/4/16.
 */

public class TestDatabaseActions {

    private enum ActionType {
        REQUEST_TEST,
        RECEIVE_TEST_WITH_SUCCEESS,
        RECEIVE_TEST_WITH_FAILURE,

        REQUEST_LIST_OF_TESTS,
        RECEIVE_LIST_OF_TESTS_WITH_SUCCEESS,
        RECEIVE_LIST_OF_TESTS_WITH_FAILURE
    }

    public static Action requestTest(String key) {
        return new Action<>(ActionType.REQUEST_TEST,
                key);
    }

    public static Action receiveTestWithSuccess(Test test) {
        return new Action<>(ActionType.RECEIVE_TEST_WITH_SUCCEESS,
                test);
    }

    public static Action receiveTestWithFailure(String databaseErrorMessage) {
        return new Action<>(ActionType.RECEIVE_TEST_WITH_FAILURE,
                databaseErrorMessage);
    }


    public static Action requestListOfTests(Query key) {
        return new Action<>(ActionType.REQUEST_LIST_OF_TESTS,
                key);
    }

    public static Action receiveListOfTestsWithSuccess(List<Test> tests) {
        return new Action<>(ActionType.RECEIVE_LIST_OF_TESTS_WITH_SUCCEESS,
                tests);
    }

    public static Action receiveListOfTestsWithFailure(String databaseErrorMessage) {
        return new Action<>(ActionType.RECEIVE_LIST_OF_TESTS_WITH_FAILURE,
                databaseErrorMessage);
    }
}
