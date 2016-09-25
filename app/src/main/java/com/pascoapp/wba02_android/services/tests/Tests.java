package com.pascoapp.wba02_android.services.tests;

import com.google.firebase.FirebaseException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.Helpers;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by peter on 8/4/16.
 */

public class Tests {

    public static final String TESTS_KEY = "tests";
    public static final DatabaseReference TESTS_REF
            = Helpers.getDatabaseInstance().getReference().child(TESTS_KEY);


    public static Observable<Test> fetchTest(String key) {
        return Observable.create(subscriber -> {
            TESTS_REF.child(key)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() == null) {
                                String errorMessage = key + ": doesn't exist in the database";
                                subscriber.onError(new FirebaseException(errorMessage));
                                return;
                            }

                            System.out.println("Hello: " + key);

                            Test test = dataSnapshot.getValue(Test.class);
                            test.setKey(dataSnapshot.getKey());
                            subscriber.onNext(test);
                            subscriber.onCompleted();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            subscriber.onError(new FirebaseException(databaseError.getMessage()));

                        }
                    });
        });
    }

    public static Observable<List<Test>> fetchListOfTests(Query query) {
        return Observable.create(subscriber -> {
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null) {
                        String errorMessage = "Query " + query + " returned nothing from the the database";
                        subscriber.onError(new FirebaseException(errorMessage));
                        return;
                    }

                    List<Test> tests = new ArrayList<>();
                    for (DataSnapshot testSnapshot : dataSnapshot.getChildren()) {
                        Test test = testSnapshot.getValue(Test.class);
                        test.setKey(testSnapshot.getKey());
                        tests.add(test);
                    }
                    subscriber.onNext(tests);
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
