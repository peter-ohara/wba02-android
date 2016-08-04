package com.pascoapp.wba02_android.dataFetching;

import com.google.firebase.database.Query;

import java.util.List;

import trikita.jedux.Action;

/**
 * Created by peter on 8/4/16.
 */

public class MessageDatabaseActions {

    private enum ActionType {
        REQUEST_MESSAGE,
        RECEIVE_MESSAGE_WITH_SUCCEESS,
        RECEIVE_MESSAGE_WITH_FAILURE,

        REQUEST_LIST_OF_MESSAGES,
        RECEIVE_LIST_OF_MESSAGES_WITH_SUCCEESS,
        RECEIVE_LIST_OF_MESSAGES_WITH_FAILURE
    }

    public static Action requestMessage(String key) {
        return new Action<>(ActionType.REQUEST_MESSAGE,
                key);
    }

    public static Action receiveMessageWithSuccess(Message message) {
        return new Action<>(ActionType.RECEIVE_MESSAGE_WITH_SUCCEESS,
                message);
    }

    public static Action receiveMessageWithFailure(String databaseErrorMessage) {
        return new Action<>(ActionType.RECEIVE_MESSAGE_WITH_FAILURE,
                databaseErrorMessage);
    }


    public static Action requestListOfMessages(Query key) {
        return new Action<>(ActionType.REQUEST_LIST_OF_MESSAGES,
                key);
    }

    public static Action receiveListOfMessagesWithSuccess(List<Message> messages) {
        return new Action<>(ActionType.RECEIVE_LIST_OF_MESSAGES_WITH_SUCCEESS,
                messages);
    }

    public static Action receiveListOfMessagesWithFailure(String databaseErrorMessage) {
        return new Action<>(ActionType.RECEIVE_LIST_OF_MESSAGES_WITH_FAILURE,
                databaseErrorMessage);
    }
}
