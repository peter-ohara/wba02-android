package com.pascoapp.wba02_android.services.messages;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.Actions;
import com.pascoapp.wba02_android.ImmutableState;
import com.pascoapp.wba02_android.State;
import com.pascoapp.wba02_android.Helpers;

import java.util.ArrayList;
import java.util.List;

import trikita.jedux.Action;
import trikita.jedux.Store;

/**
 * Created by peter on 8/4/16.
 */

public class Messages {

    public static final String MESSAGES_KEY = "messages";
    public static final DatabaseReference MESSAGES_REF
            = FirebaseDatabase.getInstance().getReference().child(MESSAGES_KEY);

    public static void fetchMessage(Store<Action, State> store, String key) {
        store.dispatch(MessageActions.messageRequestInitiated(key));

        MESSAGES_REF.child(key)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Message message = dataSnapshot.getValue(Message.class);
                        message.key = dataSnapshot.getKey();
                        store.dispatch(MessageActions.messageRequestSucceeded(message));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        store.dispatch(MessageActions
                                .messageRequestFailed(databaseError.getMessage()));
                    }
                });
    }

    public static void fetchListOfMessages(Store<Action, State> store, Query query) {
        store.dispatch(MessageActions.listMessageRequestInitiated(query));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Message> messages = new ArrayList<>();
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    Message message = messageSnapshot.getValue(Message.class);
                    message.key = messageSnapshot.getKey();
                    messages.add(message);
                }
                store.dispatch(MessageActions.listMessageRequestSucceeded(messages));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                store.dispatch(MessageActions
                        .listMessageRequestFailed(databaseError.getMessage()));
            }
        });
    }






    public static State messageRequestInitiatedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case MESSAGE_REQUEST_INITIATED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(true)
                        .build();
            default:
                return oldState;
        }
    }

    public static State messageRequestSuccessfulReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case MESSAGE_REQUEST_SUCCESSFUL:
                Message message = (Message) action.value;
                return ImmutableState.builder()
                        .from(oldState)
                        // Merge new Message Data
                        .putMessages(message.getKey(), message)
                        // Set isFetching Flag to false
                        .isFetching(false)
                        .build();
            default:
                return oldState;
        }
    }

    public static State messageRequestFailedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case MESSAGE_REQUEST_FAILED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(false)
                        .displayErrorMessage((String) action.value)
                        .build();
            default:
                return oldState;
        }
    }


    public static State messageListRequestInitiatedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LIST_MESSAGE_REQUEST_INITIATED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(true)
                        .build();
            default:
                return oldState;
        }
    }

    public static State messageListRequestSuccessfulReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LIST_MESSAGE_REQUEST_SUCCESSFUL:
                List<Message> messages = (List<Message>) action.value;
                List<String> messageKeys = Stream.of(messages)
                        .map(message -> message.getKey())
                        .collect(Collectors.toList());
                return ImmutableState.builder()
                        .from(oldState)
                        // Merge new Message Data
                        .putAllMessages(Helpers.convertToMap(messages))
                        // Set isFetching Flag to false
                        .isFetching(false)
                        .build();
            default:
                return oldState;
        }
    }

    public static State messageListRequestFailedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LIST_MESSAGE_REQUEST_FAILED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(false)
                        .displayErrorMessage((String) action.value)
                        .build();
            default:
                return oldState;
        }
    }

}
