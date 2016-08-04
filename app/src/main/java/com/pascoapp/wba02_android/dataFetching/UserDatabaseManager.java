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

public class UserDatabaseManager {

    public static final String USERS_KEY = "users";
    public static final DatabaseReference USERS_REF
            = FirebaseDatabase.getInstance().getReference().child(USERS_KEY);

    public static void fetchUser(Store<Action, State> store, String key) {
        store.dispatch(UserDatabaseActions.requestUser(key));

        USERS_REF.child(key)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        user.key = dataSnapshot.getKey();
                        store.dispatch(UserDatabaseActions.receiveUserWithSuccess(user));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        store.dispatch(UserDatabaseActions
                                .receiveUserWithFailure(databaseError.getMessage()));
                    }
                });
    }

    public static void fetchListOfUsers(Store<Action, State> store, Query query) {
        store.dispatch(UserDatabaseActions.requestListOfUsers(query));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<>();
                for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    user.key = userSnapshot.getKey();
                    users.add(user);
                }
                store.dispatch(UserDatabaseActions.receiveListOfUsersWithSuccess(users));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                store.dispatch(UserDatabaseActions
                        .receiveListOfUsersWithFailure(databaseError.getMessage()));
            }
        });
    }

}
