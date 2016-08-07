package com.pascoapp.wba02_android.services.users;

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

public class Users {

    public static final String USERS_KEY = "users";
    public static final DatabaseReference USERS_REF
            = FirebaseDatabase.getInstance().getReference().child(USERS_KEY);

    public static void fetchUser(Store<Action, State> store, String key) {
        store.dispatch(UserActions.userRequestInitiated(key));

        USERS_REF.child(key)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        user.key = dataSnapshot.getKey();
                        store.dispatch(UserActions.userRequestSucceeded(user));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        store.dispatch(UserActions
                                .userRequestFailed(databaseError.getMessage()));
                    }
                });
    }

    public static void fetchListOfUsers(Store<Action, State> store, Query query) {
        store.dispatch(UserActions.listUserRequestInitiated(query));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<>();
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    user.key = userSnapshot.getKey();
                    users.add(user);
                }
                store.dispatch(UserActions.listUserRequestSucceeded(users));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                store.dispatch(UserActions
                        .listUserRequestFailed(databaseError.getMessage()));
            }
        });
    }






    public static State userRequestInitiatedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case USER_REQUEST_INITIATED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(true)
                        .build();
            default:
                return oldState;
        }
    }

    public static State userRequestSuccessfulReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case USER_REQUEST_SUCCESSFUL:
                User user = (User) action.value;
                return ImmutableState.builder()
                        .from(oldState)
                        // Merge new User Data
                        .putUsers(user.getKey(), user)
                        // Set isFetching Flag to false
                        .isFetching(false)
                        .build();
            default:
                return oldState;
        }
    }

    public static State userRequestFailedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case USER_REQUEST_FAILED:
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


    public static State userListRequestInitiatedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LIST_USER_REQUEST_INITIATED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(true)
                        .build();
            default:
                return oldState;
        }
    }

    public static State userListRequestSuccessfulReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LIST_USER_REQUEST_SUCCESSFUL:
                List<User> users = (List<User>) action.value;
                List<String> userKeys = Stream.of(users)
                        .map(user -> user.getKey())
                        .collect(Collectors.toList());
                return ImmutableState.builder()
                        .from(oldState)
                        // Merge new User Data
                        .putAllUsers(Helpers.convertToMap(users))
                        // Set isFetching Flag to false
                        .isFetching(false)
                        .build();
            default:
                return oldState;
        }
    }

    public static State userListRequestFailedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LIST_USER_REQUEST_FAILED:
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
