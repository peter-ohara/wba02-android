package com.pascoapp.wba02_android.services.users;

import com.google.firebase.database.Query;
import com.pascoapp.wba02_android.Actions.ActionType;

import java.util.List;

import trikita.jedux.Action;

/**
 * Created by peter on 8/4/16.
 */

public class UserActions {

    public static Action userRequestInitiated(String key) {
        return new Action<>(ActionType.USER_REQUEST_INITIATED,
                key);
    }

    public static Action userRequestSucceeded(User user) {
        return new Action<>(ActionType.USER_REQUEST_SUCCESSFUL,
                user);
    }

    public static Action userRequestFailed(String databaseErrorMessage) {
        return new Action<>(ActionType.USER_REQUEST_FAILED,
                databaseErrorMessage);
    }


    public static Action listUserRequestInitiated(Query key) {
        return new Action<>(ActionType.LIST_USER_REQUEST_INITIATED,
                key);
    }

    public static Action listUserRequestSucceeded(List<User> users) {
        return new Action<>(ActionType.LIST_USER_REQUEST_SUCCESSFUL,
                users);
    }

    public static Action listUserRequestFailed(String databaseErrorMessage) {
        return new Action<>(ActionType.LIST_USER_REQUEST_FAILED,
                databaseErrorMessage);
    }
}
