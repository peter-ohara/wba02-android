package com.pascoapp.wba02_android.services.users;

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

public class Users {

    public static final String USERS_KEY = "users";
    public static final DatabaseReference USERS_REF
            = Helpers.getDatabaseInstance().getReference().child(USERS_KEY);


    public static Observable<User> fetchUser(String key) {
        return Observable.create(subscriber -> {
            USERS_REF.child(key)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() == null) {
                                String errorMessage = key + ": doesn't exist in the database";
                                subscriber.onError(new FirebaseException(errorMessage));
                                return;
                            }

                            System.out.println("Hello: " + key);

                            User user = dataSnapshot.getValue(User.class);
                            user.setKey(dataSnapshot.getKey());
                            subscriber.onNext(user);
                            subscriber.onCompleted();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            subscriber.onError(new FirebaseException(databaseError.getMessage()));

                        }
                    });
        });
    }

    public static Observable<List<User>> fetchListOfUsers(Query query) {
        return Observable.create(subscriber -> {
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null) {
                        String errorMessage = "Query " + query + " returned nothing from the the database";
                        subscriber.onError(new FirebaseException(errorMessage));
                        return;
                    }

                    List<User> users = new ArrayList<>();
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        User user = userSnapshot.getValue(User.class);
                        user.setKey(userSnapshot.getKey());
                        users.add(user);
                    }
                    subscriber.onNext(users);
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
