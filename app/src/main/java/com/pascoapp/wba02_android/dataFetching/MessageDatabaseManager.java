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

public class MessageDatabaseManager {

    public static final String MESSAGES_KEY = "messages";
    public static final DatabaseReference MESSAGES_REF
            = FirebaseDatabase.getInstance().getReference().child(MESSAGES_KEY);

    public static void fetchMessage(Store<Action, State> store, String key) {
        store.dispatch(MessageDatabaseActions.requestMessage(key));

        MESSAGES_REF.child(key)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Message message = dataSnapshot.getValue(Message.class);
                        message.key = dataSnapshot.getKey();
                        store.dispatch(MessageDatabaseActions.receiveMessageWithSuccess(message));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        store.dispatch(MessageDatabaseActions
                                .receiveMessageWithFailure(databaseError.getMessage()));
                    }
                });
    }

    public static void fetchListOfMessages(Store<Action, State> store, Query query) {
        store.dispatch(MessageDatabaseActions.requestListOfMessages(query));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Message> messages = new ArrayList<>();
                for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                    Message message = messageSnapshot.getValue(Message.class);
                    message.key = messageSnapshot.getKey();
                    messages.add(message);
                }
                store.dispatch(MessageDatabaseActions.receiveListOfMessagesWithSuccess(messages));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                store.dispatch(MessageDatabaseActions
                        .receiveListOfMessagesWithFailure(databaseError.getMessage()));
            }
        });
    }

}
