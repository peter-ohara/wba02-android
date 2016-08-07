package com.pascoapp.wba02_android.services.schools;

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

public class Schools {

    public static final String SCHOOLS_KEY = "schools";
    public static final DatabaseReference SCHOOLS_REF
            = FirebaseDatabase.getInstance().getReference().child(SCHOOLS_KEY);

    public static void fetchSchool(Store<Action, State> store, String key) {
        store.dispatch(SchoolActions.schoolRequestInitiated(key));

        SCHOOLS_REF.child(key)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        School school = dataSnapshot.getValue(School.class);
                        school.key = dataSnapshot.getKey();
                        store.dispatch(SchoolActions.schoolRequestSucceeded(school));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        store.dispatch(SchoolActions
                                .schoolRequestFailed(databaseError.getMessage()));
                    }
                });
    }

    public static void fetchListOfSchools(Store<Action, State> store, Query query) {
        store.dispatch(SchoolActions.listSchoolRequestInitiated(query));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<School> schools = new ArrayList<>();
                for (DataSnapshot schoolSnapshot: dataSnapshot.getChildren()) {
                    School school = schoolSnapshot.getValue(School.class);
                    school.key = schoolSnapshot.getKey();
                    schools.add(school);
                }
                store.dispatch(SchoolActions.listSchoolRequestSucceeded(schools));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                store.dispatch(SchoolActions
                        .listSchoolRequestFailed(databaseError.getMessage()));
            }
        });
    }






    public static State schoolRequestInitiatedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case SCHOOL_REQUEST_INITIATED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(true)
                        .build();
            default:
                return oldState;
        }
    }

    public static State schoolRequestSuccessfulReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case SCHOOL_REQUEST_SUCCESSFUL:
                School school = (School) action.value;
                return ImmutableState.builder()
                        .from(oldState)
                        // Merge new School Data
                        .putSchools(school.getKey(), school)
                        // Set isFetching Flag to false
                        .isFetching(false)
                        .build();
            default:
                return oldState;
        }
    }

    public static State schoolRequestFailedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case SCHOOL_REQUEST_FAILED:
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


    public static State schoolListRequestInitiatedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LIST_SCHOOL_REQUEST_INITIATED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(true)
                        .build();
            default:
                return oldState;
        }
    }

    public static State schoolListRequestSuccessfulReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LIST_SCHOOL_REQUEST_SUCCESSFUL:
                List<School> schools = (List<School>) action.value;
                List<String> schoolKeys = Stream.of(schools)
                        .map(school -> school.getKey())
                        .collect(Collectors.toList());
                return ImmutableState.builder()
                        .from(oldState)
                        // Merge new School Data
                        .putAllSchools(Helpers.convertToMap(schools))
                        // Set isFetching Flag to false
                        .isFetching(false)
                        .build();
            default:
                return oldState;
        }
    }

    public static State schoolListRequestFailedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LIST_SCHOOL_REQUEST_FAILED:
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
