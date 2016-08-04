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

public class TestDatabaseManager {

    public static final String TESTS_KEY = "tests";
    public static final DatabaseReference TESTS_REF
            = FirebaseDatabase.getInstance().getReference().child(TESTS_KEY);

    public static void fetchTest(Store<Action, State> store, String key) {
        store.dispatch(TestDatabaseActions.requestTest(key));

        TESTS_REF.child(key)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Test test = dataSnapshot.getValue(Test.class);
                        test.key = dataSnapshot.getKey();
                        store.dispatch(TestDatabaseActions.receiveTestWithSuccess(test));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        store.dispatch(TestDatabaseActions
                                .receiveTestWithFailure(databaseError.getMessage()));
                    }
                });
    }

    public static void fetchListOfTests(Store<Action, State> store, Query query) {
        store.dispatch(TestDatabaseActions.requestListOfTests(query));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Test> tests = new ArrayList<>();
                for (DataSnapshot testSnapshot: dataSnapshot.getChildren()) {
                    Test test = testSnapshot.getValue(Test.class);
                    test.key = testSnapshot.getKey();
                    tests.add(test);
                }
                store.dispatch(TestDatabaseActions.receiveListOfTestsWithSuccess(tests));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                store.dispatch(TestDatabaseActions
                        .receiveListOfTestsWithFailure(databaseError.getMessage()));
            }
        });
    }

}
