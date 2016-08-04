package com.pascoapp.wba02_android.dataFetching;

import com.google.firebase.database.Query;

import java.util.List;

import trikita.jedux.Action;

/**
 * Created by peter on 8/4/16.
 */

public class UserDatabaseActions {

    private enum ActionType {
        REQUEST_USER,
        RECEIVE_USER_WITH_SUCCEESS,
        RECEIVE_USER_WITH_FAILURE,

        REQUEST_LIST_OF_USERS,
        RECEIVE_LIST_OF_USERS_WITH_SUCCEESS,
        RECEIVE_LIST_OF_USERS_WITH_FAILURE
    }

    public static Action requestUser(String key) {
        return new Action<>(ActionType.REQUEST_USER,
                key);
    }

    public static Action receiveUserWithSuccess(User user) {
        return new Action<>(ActionType.RECEIVE_USER_WITH_SUCCEESS,
                user);
    }

    public static Action receiveUserWithFailure(String databaseErrorMessage) {
        return new Action<>(ActionType.RECEIVE_USER_WITH_FAILURE,
                databaseErrorMessage);
    }


    public static Action requestListOfUsers(Query key) {
        return new Action<>(ActionType.REQUEST_LIST_OF_USERS,
                key);
    }

    public static Action receiveListOfUsersWithSuccess(List<User> users) {
        return new Action<>(ActionType.RECEIVE_LIST_OF_USERS_WITH_SUCCEESS,
                users);
    }

    public static Action receiveListOfUsersWithFailure(String databaseErrorMessage) {
        return new Action<>(ActionType.RECEIVE_LIST_OF_USERS_WITH_FAILURE,
                databaseErrorMessage);
    }
}
