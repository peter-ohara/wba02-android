package com.pascoapp.wba02_android.services.messages;

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

public class Messages {

    public static final String MESSAGES_KEY = "messages";
    public static final DatabaseReference MESSAGES_REF
            = Helpers.getDatabaseInstance().getReference().child(MESSAGES_KEY);


    public static Observable<Message> fetchMessage(String key) {
        return Observable.create(subscriber -> {
            MESSAGES_REF.child(key)
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() == null) {
                                String errorMessage = key + ": doesn't exist in the database";
                                subscriber.onError(new FirebaseException(errorMessage));
                                return;
                            }

                            Message message = dataSnapshot.getValue(Message.class);
                            message.setKey(dataSnapshot.getKey());
                            subscriber.onNext(message);
                            subscriber.onCompleted();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            subscriber.onError(new FirebaseException(databaseError.getMessage()));

                        }
                    });
        });
    }

    public static Observable<List<Message>> fetchListOfMessages(Query query) {
        return Observable.create(subscriber -> {
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getValue() == null) {
                        String errorMessage = "Query " + query + " returned nothing from the the database";
                        subscriber.onError(new FirebaseException(errorMessage));
                        return;
                    }

                    List<Message> messages = new ArrayList<>();
                    for (DataSnapshot messageSnapshot : dataSnapshot.getChildren()) {
                        Message message = messageSnapshot.getValue(Message.class);
                        message.setKey(messageSnapshot.getKey());
                        messages.add(message);
                    }
                    subscriber.onNext(messages);
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
