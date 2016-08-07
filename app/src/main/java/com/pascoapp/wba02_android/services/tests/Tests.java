package com.pascoapp.wba02_android.services.tests;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.Actions;
import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.ImmutableState;
import com.pascoapp.wba02_android.State;

import org.jdeferred.Deferred;
import org.jdeferred.Promise;
import org.jdeferred.impl.DeferredObject;

import java.util.ArrayList;
import java.util.List;

import trikita.jedux.Action;
import trikita.jedux.Store;

/**
 * Created by peter on 8/4/16.
 */

public class Tests {

    public static final String TESTS_KEY = "tests";
    public static final DatabaseReference TESTS_REF
            = FirebaseDatabase.getInstance().getReference().child(TESTS_KEY);

    public static Promise fetchTest(Store<Action, State> store, String key) {
        store.dispatch(TestActions.testRequestInitiated(key));
        Deferred deferred = new DeferredObject();
        TESTS_REF.child(key)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() == null) {
                            String errorMessage = "Item doesn't exist in the database";
                            store.dispatch(TestActions
                                    .testRequestFailed(errorMessage));
                            deferred.reject(errorMessage);
                            return;
                        }

                        Test test = dataSnapshot.getValue(Test.class);
                        test.key = dataSnapshot.getKey();
                        store.dispatch(TestActions.testRequestSucceeded(test));
                        deferred.resolve(test);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        store.dispatch(TestActions
                                .testRequestFailed(databaseError.getMessage()));
                        deferred.reject(databaseError.getMessage());
                    }
                });
        return deferred.promise();
    }

    public static Promise fetchListOfTests(Store<Action, State> store, Query query) {
        store.dispatch(TestActions.listTestRequestInitiated(query));
        Deferred deferred = new DeferredObject();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    String errorMessage = "Item doesn't exist in the database";
                    store.dispatch(TestActions
                            .testRequestFailed(errorMessage));
                    deferred.reject(errorMessage);
                    return;
                }

                List<Test> tests = new ArrayList<>();
                for (DataSnapshot testSnapshot: dataSnapshot.getChildren()) {
                    Test test = testSnapshot.getValue(Test.class);
                    test.key = testSnapshot.getKey();
                    tests.add(test);
                }
                store.dispatch(TestActions.listTestRequestSucceeded(tests));
                deferred.resolve(tests);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                store.dispatch(TestActions
                        .testRequestFailed(databaseError.getMessage()));
                deferred.reject(databaseError.getMessage());
            }
        });
        return deferred.promise();
    }






    public static State testRequestInitiatedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case TEST_REQUEST_INITIATED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(true)
                        .build();
            default:
                return oldState;
        }
    }

    public static State testRequestSuccessfulReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case TEST_REQUEST_SUCCESSFUL:
                Test test = (Test) action.value;
                return ImmutableState.builder()
                        .from(oldState)
                        // Merge new Test Data
                        .putTests(test.getKey(), test)
                        // Set isFetching Flag to false
                        .isFetching(false)
                        .build();
            default:
                return oldState;
        }
    }

    public static State testRequestFailedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case TEST_REQUEST_FAILED:
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


    public static State testListRequestInitiatedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LIST_TEST_REQUEST_INITIATED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(true)
                        .build();
            default:
                return oldState;
        }
    }

    public static State testListRequestSuccessfulReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LIST_TEST_REQUEST_SUCCESSFUL:
                List<Test> tests = (List<Test>) action.value;
                List<String> testKeys = Stream.of(tests)
                        .map(test -> test.getKey())
                        .collect(Collectors.toList());
                return ImmutableState.builder()
                        .from(oldState)
                        // Merge new Test Data
                        .putAllTests(Helpers.convertToMap(tests))
                        // Set isFetching Flag to false
                        .isFetching(false)
                        .build();
            default:
                return oldState;
        }
    }

    public static State testListRequestFailedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LIST_TEST_REQUEST_FAILED:
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
