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

public class ProgrammeDatabaseManager {

    public static final String PROGRAMMES_KEY = "programmes";
    public static final DatabaseReference PROGRAMMES_REF
            = FirebaseDatabase.getInstance().getReference().child(PROGRAMMES_KEY);

    public static void fetchProgramme(Store<Action, State> store, String key) {
        store.dispatch(ProgrammeDatabaseActions.requestProgramme(key));

        PROGRAMMES_REF.child(key)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Programme programme = dataSnapshot.getValue(Programme.class);
                        programme.key = dataSnapshot.getKey();
                        store.dispatch(ProgrammeDatabaseActions.receiveProgrammeWithSuccess(programme));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        store.dispatch(ProgrammeDatabaseActions
                                .receiveProgrammeWithFailure(databaseError.getMessage()));
                    }
                });
    }

    public static void fetchListOfProgrammes(Store<Action, State> store, Query query) {
        store.dispatch(ProgrammeDatabaseActions.requestListOfProgrammes(query));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Programme> programmes = new ArrayList<>();
                for (DataSnapshot programmeSnapshot: dataSnapshot.getChildren()) {
                    Programme programme = programmeSnapshot.getValue(Programme.class);
                    programme.key = programmeSnapshot.getKey();
                    programmes.add(programme);
                }
                store.dispatch(ProgrammeDatabaseActions.receiveListOfProgrammesWithSuccess(programmes));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                store.dispatch(ProgrammeDatabaseActions
                        .receiveListOfProgrammesWithFailure(databaseError.getMessage()));
            }
        });
    }

}
