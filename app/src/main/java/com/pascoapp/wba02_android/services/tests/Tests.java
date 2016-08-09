package com.pascoapp.wba02_android.services.tests;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.firebase.FirebaseException;
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

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import trikita.jedux.Action;
import trikita.jedux.Store;

/**
 * Created by peter on 8/4/16.
 */

public class Tests {

    public static final String TESTS_KEY = "tests";
    public static final DatabaseReference TESTS_REF
            = FirebaseDatabase.getInstance().getReference().child(TESTS_KEY);


    public static Observable<Test> fetchTest(Store<Action, State> store, String key) {
        return Observable.create(subscriber -> {
            store.dispatch(TestActions.testRequestInitiated(key));
            TESTS_REF.child(key)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() == null) {
                                String errorMessage = "Item doesn't exist in the database";
                                store.dispatch(TestActions
                                        .testRequestFailed(errorMessage));
                                subscriber.onError(new FirebaseException(errorMessage));
                                return;
                            }

                            Test test = dataSnapshot.getValue(Test.class);
                            test.key = dataSnapshot.getKey();
                            store.dispatch(TestActions.testRequestSucceeded(test));
                            subscriber.onNext(test);
                            subscriber.onCompleted();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            store.dispatch(TestActions
                                    .testRequestFailed(databaseError.getMessage()));
                            subscriber.onError(new FirebaseException(databaseError.getMessage()));

                        }
                    });
        });
    }

    public static Observable<List<Test>> fetchListOfTests(Store<Action, State> store, Query query) {
        return Observable.create(subscriber -> {
            store.dispatch(TestActions.listTestRequestInitiated(query));
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null) {
                        String errorMessage = "Item doesn't exist in the database";
                        store.dispatch(TestActions
                                .testRequestFailed(errorMessage));
                        subscriber.onError(new FirebaseException(errorMessage));
                        return;
                    }

                    List<Test> tests = new ArrayList<>();
                    for (DataSnapshot testSnapshot: dataSnapshot.getChildren()) {
                        Test test = testSnapshot.getValue(Test.class);
                        test.key = testSnapshot.getKey();
                        tests.add(test);
                    }
                    store.dispatch(TestActions.listTestRequestSucceeded(tests));
                    subscriber.onNext(tests);
                    subscriber.onCompleted();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    store.dispatch(TestActions
                            .listTestRequestFailed(databaseError.getMessage()));
                    subscriber.onError(new FirebaseException(databaseError.getMessage()));
                }
            });
        });
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
