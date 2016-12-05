package com.pascoapp.wba02_android.services.lecturers;

import com.google.firebase.FirebaseException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.Helpers;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by peter on 8/4/16.
 */

public class Lecturers {

    public static final String LECTURERS_KEY = "lecturers";
    public static final DatabaseReference LECTURERS_REF
            = Helpers.getDatabaseInstance().getReference().child(LECTURERS_KEY);


    public static Observable<Lecturer> fetchLecturer(String key) {
        return Observable.create(subscriber -> {
            LECTURERS_REF.child(key)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() == null) {
                                String errorMessage = key + ": doesn't exist in the database";
                                subscriber.onError(new FirebaseException(errorMessage));
                                return;
                            }

                            Lecturer lecturer = dataSnapshot.getValue(Lecturer.class);
                            lecturer.setKey(dataSnapshot.getKey());
                            subscriber.onNext(lecturer);
                            subscriber.onCompleted();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            subscriber.onError(new FirebaseException(databaseError.getMessage()));

                        }
                    });
        });
    }

    public static Observable<List<Lecturer>> fetchListOfLecturers(Query query) {
        return Observable.create(subscriber -> {
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null) {
                        String errorMessage = "Query " + query + " returned nothing from the the database";
                        subscriber.onError(new FirebaseException(errorMessage));
                        return;
                    }

                    List<Lecturer> lecturers = new ArrayList<>();
                    for (DataSnapshot lecturerSnapshot : dataSnapshot.getChildren()) {
                        Lecturer lecturer = lecturerSnapshot.getValue(Lecturer.class);
                        lecturer.setKey(lecturerSnapshot.getKey());
                        lecturers.add(lecturer);
                    }
                    subscriber.onNext(lecturers);
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
