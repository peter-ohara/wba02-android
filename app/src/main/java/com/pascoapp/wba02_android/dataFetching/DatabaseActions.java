package com.pascoapp.wba02_android.dataFetching;

import com.google.firebase.database.Query;
import com.pascoapp.wba02_android.Actions.ActionType;
import com.pascoapp.wba02_android.FirebaseItem;

import java.util.List;

import trikita.jedux.Action;

/**
 * Created by peter on 8/4/16.
 */

public class DatabaseActions {

    public static Action requestInitiated(String key) {
        return new Action<>(ActionType.REQUEST_INITIATED,
                key);
    }

    public static <T extends FirebaseItem> Action requestSuccessful(T item) {
        return new Action<>(ActionType.REQUEST_SUCCESSFUL,
                item);
    }

    public static Action requestFailed(String databaseErrorMessage) {
        return new Action<>(ActionType.REQUEST_FAILED,
                databaseErrorMessage);
    }


    public static Action listRequestInitiated(Query query) {
        return new Action<>(ActionType.LIST_REQUEST_INITIATED,
                query);
    }

    public static <T extends FirebaseItem> Action listRequestSuccessful(List<T> items) {
        return new Action<>(ActionType.LIST_REQUEST_SUCCESSFUL,
                items);
    }

    public static Action listRequestFailed(String databaseErrorMessage) {
        return new Action<>(ActionType.LIST_REQUEST_FAILED,
                databaseErrorMessage);
    }


}
