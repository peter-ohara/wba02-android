package com.pascoapp.wba02_android.services.schools;

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

public class Schools {

    public static final String SCHOOLS_KEY = "schools";
    public static final DatabaseReference SCHOOLS_REF
            = FirebaseDatabase.getInstance().getReference().child(SCHOOLS_KEY);


    public static Observable<School> fetchSchool(String key) {
        return Observable.create(subscriber -> {
            SCHOOLS_REF.child(key)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() == null) {
                                String errorMessage = "Item doesn't exist in the database";
                                subscriber.onError(new FirebaseException(errorMessage));
                                return;
                            }

                            School school = dataSnapshot.getValue(School.class);
                            school.setKey(dataSnapshot.getKey());
                            subscriber.onNext(school);
                            subscriber.onCompleted();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            subscriber.onError(new FirebaseException(databaseError.getMessage()));

                        }
                    });
        });
    }

    public static Observable<List<School>> fetchListOfSchools(Query query) {
        return Observable.create(subscriber -> {
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null) {
                        String errorMessage = "Item doesn't exist in the database";
                        subscriber.onError(new FirebaseException(errorMessage));
                        return;
                    }

                    List<School> schools = new ArrayList<>();
                    for (DataSnapshot schoolSnapshot: dataSnapshot.getChildren()) {
                        School school = schoolSnapshot.getValue(School.class);
                        school.setKey(schoolSnapshot.getKey());
                        schools.add(school);
                    }
                    subscriber.onNext(schools);
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
