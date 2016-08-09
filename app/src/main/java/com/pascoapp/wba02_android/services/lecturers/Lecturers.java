package com.pascoapp.wba02_android.services.lecturers;

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

public class Lecturers {

    public static final String LECTURERS_KEY = "lecturers";
    public static final DatabaseReference LECTURERS_REF
            = FirebaseDatabase.getInstance().getReference().child(LECTURERS_KEY);


    public static Observable<Lecturer> fetchLecturer(Store<Action, State> store, String key) {
        return Observable.create(subscriber -> {
            store.dispatch(LecturerActions.lecturerRequestInitiated(key));
            LECTURERS_REF.child(key)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() == null) {
                                String errorMessage = "Item doesn't exist in the database";
                                store.dispatch(LecturerActions
                                        .lecturerRequestFailed(errorMessage));
                                subscriber.onError(new FirebaseException(errorMessage));
                                return;
                            }

                            Lecturer lecturer = dataSnapshot.getValue(Lecturer.class);
                            lecturer.key = dataSnapshot.getKey();
                            store.dispatch(LecturerActions.lecturerRequestSucceeded(lecturer));
                            subscriber.onNext(lecturer);
                            subscriber.onCompleted();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            store.dispatch(LecturerActions
                                    .lecturerRequestFailed(databaseError.getMessage()));
                            subscriber.onError(new FirebaseException(databaseError.getMessage()));

                        }
                    });
        });
    }

    public static Observable<List<Lecturer>> fetchListOfLecturers(Store<Action, State> store, Query query) {
        return Observable.create(subscriber -> {
            store.dispatch(LecturerActions.listLecturerRequestInitiated(query));
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null) {
                        String errorMessage = "Item doesn't exist in the database";
                        store.dispatch(LecturerActions
                                .lecturerRequestFailed(errorMessage));
                        subscriber.onError(new FirebaseException(errorMessage));
                        return;
                    }

                    List<Lecturer> lecturers = new ArrayList<>();
                    for (DataSnapshot lecturerSnapshot: dataSnapshot.getChildren()) {
                        Lecturer lecturer = lecturerSnapshot.getValue(Lecturer.class);
                        lecturer.key = lecturerSnapshot.getKey();
                        lecturers.add(lecturer);
                    }
                    store.dispatch(LecturerActions.listLecturerRequestSucceeded(lecturers));
                    subscriber.onNext(lecturers);
                    subscriber.onCompleted();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    store.dispatch(LecturerActions
                            .listLecturerRequestFailed(databaseError.getMessage()));
                    subscriber.onError(new FirebaseException(databaseError.getMessage()));
                }
            });
        });
    }

    public static State lecturerRequestInitiatedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LECTURER_REQUEST_INITIATED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(true)
                        .build();
            default:
                return oldState;
        }
    }

    public static State lecturerRequestSuccessfulReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LECTURER_REQUEST_SUCCESSFUL:
                Lecturer lecturer = (Lecturer) action.value;
                return ImmutableState.builder()
                        .from(oldState)
                        // Merge new Lecturer Data
                        .putLecturers(lecturer.getKey(), lecturer)
                        // Set isFetching Flag to false
                        .isFetching(false)
                        .build();
            default:
                return oldState;
        }
    }

    public static State lecturerRequestFailedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LECTURER_REQUEST_FAILED:
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


    public static State lecturerListRequestInitiatedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LIST_LECTURER_REQUEST_INITIATED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(true)
                        .build();
            default:
                return oldState;
        }
    }

    public static State lecturerListRequestSuccessfulReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LIST_LECTURER_REQUEST_SUCCESSFUL:
                List<Lecturer> lecturers = (List<Lecturer>) action.value;
                List<String> lecturerKeys = Stream.of(lecturers)
                        .map(lecturer -> lecturer.getKey())
                        .collect(Collectors.toList());
                return ImmutableState.builder()
                        .from(oldState)
                        // Merge new Lecturer Data
                        .putAllLecturers(Helpers.convertToMap(lecturers))
                        // Set isFetching Flag to false
                        .isFetching(false)
                        .build();
            default:
                return oldState;
        }
    }

    public static State lecturerListRequestFailedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LIST_LECTURER_REQUEST_FAILED:
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
