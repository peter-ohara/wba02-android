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

public class QuestionDatabaseManager {

    public static final String QUESTIONS_KEY = "questions";
    public static final DatabaseReference QUESTIONS_REF
            = FirebaseDatabase.getInstance().getReference().child(QUESTIONS_KEY);

    public static void fetchQuestion(Store<Action, State> store, String key) {
        store.dispatch(QuestionDatabaseActions.requestQuestion(key));

        QUESTIONS_REF.child(key)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Question question = dataSnapshot.getValue(Question.class);
                        question.key = dataSnapshot.getKey();
                        store.dispatch(QuestionDatabaseActions.receiveQuestionWithSuccess(question));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        store.dispatch(QuestionDatabaseActions
                                .receiveQuestionWithFailure(databaseError.getMessage()));
                    }
                });
    }

    public static void fetchListOfQuestions(Store<Action, State> store, Query query) {
        store.dispatch(QuestionDatabaseActions.requestListOfQuestions(query));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Question> questions = new ArrayList<>();
                for (DataSnapshot questionSnapshot: dataSnapshot.getChildren()) {
                    Question question = questionSnapshot.getValue(Question.class);
                    question.key = questionSnapshot.getKey();
                    questions.add(question);
                }
                store.dispatch(QuestionDatabaseActions.receiveListOfQuestionsWithSuccess(questions));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                store.dispatch(QuestionDatabaseActions
                        .receiveListOfQuestionsWithFailure(databaseError.getMessage()));
            }
        });
    }

}
