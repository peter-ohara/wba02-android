package com.pascoapp.wba02_android.services.programmes;

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

public class Programmes {

    public static final String PROGRAMMES_KEY = "programmes";
    public static final DatabaseReference PROGRAMMES_REF
            = FirebaseDatabase.getInstance().getReference().child(PROGRAMMES_KEY);

    public static Promise fetchProgramme(Store<Action, State> store, String key) {
        store.dispatch(ProgrammeActions.programmeRequestInitiated(key));
        Deferred deferred = new DeferredObject();
        PROGRAMMES_REF.child(key)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue() == null) {
                            String errorMessage = "Item doesn't exist in the database";
                            store.dispatch(ProgrammeActions
                                    .programmeRequestFailed(errorMessage));
                            deferred.reject(errorMessage);
                            return;
                        }

                        Programme programme = dataSnapshot.getValue(Programme.class);
                        programme.key = dataSnapshot.getKey();
                        store.dispatch(ProgrammeActions.programmeRequestSucceeded(programme));
                        deferred.resolve(programme);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        store.dispatch(ProgrammeActions
                                .programmeRequestFailed(databaseError.getMessage()));
                        deferred.reject(databaseError.getMessage());
                    }
                });
        return deferred.promise();
    }

    public static Promise fetchListOfProgrammes(Store<Action, State> store, Query query) {
        store.dispatch(ProgrammeActions.listProgrammeRequestInitiated(query));
        Deferred deferred = new DeferredObject();
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    String errorMessage = "Item doesn't exist in the database";
                    store.dispatch(ProgrammeActions
                            .programmeRequestFailed(errorMessage));
                    deferred.reject(errorMessage);
                    return;
                }

                List<Programme> programmes = new ArrayList<>();
                for (DataSnapshot programmeSnapshot: dataSnapshot.getChildren()) {
                    Programme programme = programmeSnapshot.getValue(Programme.class);
                    programme.key = programmeSnapshot.getKey();
                    programmes.add(programme);
                }
                store.dispatch(ProgrammeActions.listProgrammeRequestSucceeded(programmes));
                deferred.resolve(programmes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                store.dispatch(ProgrammeActions
                        .programmeRequestFailed(databaseError.getMessage()));
                deferred.reject(databaseError.getMessage());
            }
        });
        return deferred.promise();
    }






    public static State programmeRequestInitiatedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case PROGRAMME_REQUEST_INITIATED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(true)
                        .build();
            default:
                return oldState;
        }
    }

    public static State programmeRequestSuccessfulReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case PROGRAMME_REQUEST_SUCCESSFUL:
                Programme programme = (Programme) action.value;
                return ImmutableState.builder()
                        .from(oldState)
                        // Merge new Programme Data
                        .putProgrammes(programme.getKey(), programme)
                        // Set isFetching Flag to false
                        .isFetching(false)
                        .build();
            default:
                return oldState;
        }
    }

    public static State programmeRequestFailedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case PROGRAMME_REQUEST_FAILED:
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


    public static State programmeListRequestInitiatedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LIST_PROGRAMME_REQUEST_INITIATED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(true)
                        .build();
            default:
                return oldState;
        }
    }

    public static State programmeListRequestSuccessfulReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LIST_PROGRAMME_REQUEST_SUCCESSFUL:
                List<Programme> programmes = (List<Programme>) action.value;
                List<String> programmeKeys = Stream.of(programmes)
                        .map(programme -> programme.getKey())
                        .collect(Collectors.toList());
                return ImmutableState.builder()
                        .from(oldState)
                        // Merge new Programme Data
                        .putAllProgrammes(Helpers.convertToMap(programmes))
                        // Set isFetching Flag to false
                        .isFetching(false)
                        .build();
            default:
                return oldState;
        }
    }

    public static State programmeListRequestFailedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LIST_PROGRAMME_REQUEST_FAILED:
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
