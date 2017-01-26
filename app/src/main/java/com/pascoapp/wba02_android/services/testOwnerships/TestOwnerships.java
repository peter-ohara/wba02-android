package com.pascoapp.wba02_android.services.testOwnerships;

import com.google.firebase.FirebaseException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.services.testOwnerships.TestOwnership;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by peter on 8/4/16.
 */

public class TestOwnerships {

    public static final String TestOwnershipS_KEY = "testOwnerships";
    public static final DatabaseReference TestOwnershipS_REF
            = Helpers.getDatabaseInstance().getReference().child(TestOwnershipS_KEY);


    public static Observable<TestOwnership> fetchTestOwnership(String key) {
        return Observable.create(subscriber -> {
            TestOwnershipS_REF.child(key)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() == null) {
                                String errorMessage = key + ": doesn't exist in the database";
                                subscriber.onError(new FirebaseException(errorMessage));
                                return;
                            }

                            TestOwnership testOwnership = dataSnapshot.getValue(TestOwnership.class);
                            testOwnership.setKey(dataSnapshot.getKey());
                            subscriber.onNext(testOwnership);
                            subscriber.onCompleted();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            subscriber.onError(new FirebaseException(databaseError.getMessage()));

                        }
                    });
        });
    }

    public static Observable<List<TestOwnership>> fetchListOfTestOwnerships(Query query) {
        return Observable.create(subscriber -> {
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null) {
                        String errorMessage = "Query " + query + " returned nothing from the the database";
                        subscriber.onError(new FirebaseException(errorMessage));
                        return;
                    }

                    List<TestOwnership> testOwnerships = new ArrayList<>();
                    for (DataSnapshot testOwnershipSnapshot : dataSnapshot.getChildren()) {
                        TestOwnership testOwnership = testOwnershipSnapshot.getValue(TestOwnership.class);
                        testOwnership.setKey(testOwnershipSnapshot.getKey());
                        testOwnerships.add(testOwnership);
                    }
                    subscriber.onNext(testOwnerships);
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
