package com.pascoapp.wba02_android.dataFetching;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.State;

import java.util.ArrayList;
import java.util.List;

import trikita.jedux.Action;
import trikita.jedux.Store;

/**
 * Created by peter on 8/4/16.
 */

public class SchoolDatabaseManager {

    public static final String SCHOOLS_KEY = "schools";
    public static final DatabaseReference SCHOOLS_REF
            = FirebaseDatabase.getInstance().getReference().child(SCHOOLS_KEY);

    public static void fetchSchool(Store<Action, State> store, String key) {
        store.dispatch(SchoolDatabaseActions.requestSchool(key));

        SCHOOLS_REF.child(key)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        School school = dataSnapshot.getValue(School.class);
                        school.key = dataSnapshot.getKey();
                        store.dispatch(SchoolDatabaseActions.receiveSchoolWithSuccess(school));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        store.dispatch(SchoolDatabaseActions
                                .receiveSchoolWithFailure(databaseError.getMessage()));
                    }
                });
    }

    public static void fetchListOfSchools(Store<Action, State> store, Query query) {
        store.dispatch(SchoolDatabaseActions.requestListOfSchools(query));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<School> schools = new ArrayList<>();
                for (DataSnapshot schoolSnapshot: dataSnapshot.getChildren()) {
                    School school = schoolSnapshot.getValue(School.class);
                    school.key = schoolSnapshot.getKey();
                    schools.add(school);
                }
                store.dispatch(SchoolDatabaseActions.receiveListOfSchoolsWithSuccess(schools));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                store.dispatch(SchoolDatabaseActions
                        .receiveListOfSchoolsWithFailure(databaseError.getMessage()));
            }
        });
    }

}
