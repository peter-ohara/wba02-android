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

public class LecturerDatabaseManager {

    public static final String LECTURERS_KEY = "lecturers";
    public static final DatabaseReference LECTURERS_REF
            = FirebaseDatabase.getInstance().getReference().child(LECTURERS_KEY);

    public static void fetchLecturer(Store<Action, State> store, String key) {
        store.dispatch(LecturerDatabaseActions.requestLecturer(key));

        LECTURERS_REF.child(key)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Lecturer lecturer = dataSnapshot.getValue(Lecturer.class);
                        lecturer.key = dataSnapshot.getKey();
                        store.dispatch(LecturerDatabaseActions.receiveLecturerWithSuccess(lecturer));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        store.dispatch(LecturerDatabaseActions
                                .receiveLecturerWithFailure(databaseError.getMessage()));
                    }
                });
    }

    public static void fetchListOfLecturers(Store<Action, State> store, Query query) {
        store.dispatch(LecturerDatabaseActions.requestListOfLecturers(query));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Lecturer> lecturers = new ArrayList<>();
                for (DataSnapshot lecturerSnapshot: dataSnapshot.getChildren()) {
                    Lecturer lecturer = lecturerSnapshot.getValue(Lecturer.class);
                    lecturer.key = lecturerSnapshot.getKey();
                    lecturers.add(lecturer);
                }
                store.dispatch(LecturerDatabaseActions.receiveListOfLecturersWithSuccess(lecturers));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                store.dispatch(LecturerDatabaseActions
                        .receiveListOfLecturersWithFailure(databaseError.getMessage()));
            }
        });
    }

}
