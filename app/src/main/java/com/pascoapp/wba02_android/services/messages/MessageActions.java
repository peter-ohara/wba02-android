package com.pascoapp.wba02_android.services.messages;

import com.google.firebase.database.Query;
import com.pascoapp.wba02_android.Actions.ActionType;

import java.util.List;

import trikita.jedux.Action;

/**
 * Created by peter on 8/4/16.
 */

public class MessageActions {

    public static Action messageRequestInitiated(String key) {
        return new Action<>(ActionType.MESSAGE_REQUEST_INITIATED,
                key);
    }

    public static Action messageRequestSucceeded(Message message) {
        return new Action<>(ActionType.MESSAGE_REQUEST_SUCCESSFUL,
                message);
    }

    public static Action messageRequestFailed(String databaseErrorMessage) {
        return new Action<>(ActionType.MESSAGE_REQUEST_FAILED,
                databaseErrorMessage);
    }


    public static Action listMessageRequestInitiated(Query key) {
        return new Action<>(ActionType.LIST_MESSAGE_REQUEST_INITIATED,
                key);
    }

    public static Action listMessageRequestSucceeded(List<Message> messages) {
        return new Action<>(ActionType.LIST_MESSAGE_REQUEST_SUCCESSFUL,
                messages);
    }

    public static Action listMessageRequestFailed(String databaseErrorMessage) {
        return new Action<>(ActionType.LIST_MESSAGE_REQUEST_FAILED,
                databaseErrorMessage);
    }
}
