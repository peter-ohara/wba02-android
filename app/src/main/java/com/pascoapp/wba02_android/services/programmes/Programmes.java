package com.pascoapp.wba02_android.services.programmes;

import com.google.firebase.FirebaseException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by peter on 8/4/16.
 */

public class Programmes {

    public static final String PROGRAMMES_KEY = "programmes";
    public static final DatabaseReference PROGRAMMES_REF
            = FirebaseDatabase.getInstance().getReference().child(PROGRAMMES_KEY);


    public static Observable<Programme> fetchProgramme(String key) {
        return Observable.create(subscriber -> {
            PROGRAMMES_REF.child(key)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() == null) {
                                String errorMessage = key + ": doesn't exist in the database";
                                subscriber.onError(new FirebaseException(errorMessage));
                                return;
                            }

                            System.out.println("Hello: " + key);

                            Programme programme = dataSnapshot.getValue(Programme.class);
                            programme.setKey(dataSnapshot.getKey());
                            subscriber.onNext(programme);
                            subscriber.onCompleted();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            subscriber.onError(new FirebaseException(databaseError.getMessage()));

                        }
                    });
        });
    }

    public static Observable<List<Programme>> fetchListOfProgrammes(Query query) {
        return Observable.create(subscriber -> {
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null) {
                        String errorMessage = "Query " + query + " returned nothing from the the database";
                        subscriber.onError(new FirebaseException(errorMessage));
                        return;
                    }

                    List<Programme> programmes = new ArrayList<>();
                    for (DataSnapshot programmeSnapshot : dataSnapshot.getChildren()) {
                        Programme programme = programmeSnapshot.getValue(Programme.class);
                        programme.setKey(programmeSnapshot.getKey());
                        programmes.add(programme);
                    }
                    subscriber.onNext(programmes);
                    subscriber.onCompleted();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    subscriber.onError(new FirebaseException(databaseError.getMessage()));
                }
            });
        });
    }

}
