package com.pascoapp.wba02_android.services.questions;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.Actions;
import com.pascoapp.wba02_android.ImmutableState;
import com.pascoapp.wba02_android.State;
import com.pascoapp.wba02_android.Helpers;

import java.util.ArrayList;
import java.util.List;

import trikita.jedux.Action;
import trikita.jedux.Store;

/**
 * Created by peter on 8/4/16.
 */

public class Questions {

    public static final String QUESTIONS_KEY = "questions";
    public static final DatabaseReference QUESTIONS_REF
            = FirebaseDatabase.getInstance().getReference().child(QUESTIONS_KEY);

    public static void fetchQuestion(Store<Action, State> store, String key) {
        store.dispatch(QuestionActions.questionRequestInitiated(key));

        QUESTIONS_REF.child(key)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Question question = dataSnapshot.getValue(Question.class);
                        question.key = dataSnapshot.getKey();
                        store.dispatch(QuestionActions.questionRequestSucceeded(question));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        store.dispatch(QuestionActions
                                .questionRequestFailed(databaseError.getMessage()));
                    }
                });
    }

    public static void fetchListOfQuestions(Store<Action, State> store, Query query) {
        store.dispatch(QuestionActions.listQuestionRequestInitiated(query));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Question> questions = new ArrayList<>();
                for (DataSnapshot questionSnapshot: dataSnapshot.getChildren()) {
                    Question question = questionSnapshot.getValue(Question.class);
                    question.key = questionSnapshot.getKey();
                    questions.add(question);
                }
                store.dispatch(QuestionActions.listQuestionRequestSucceeded(questions));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                store.dispatch(QuestionActions
                        .listQuestionRequestFailed(databaseError.getMessage()));
            }
        });
    }






    public static State questionRequestInitiatedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case QUESTION_REQUEST_INITIATED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(true)
                        .build();
            default:
                return oldState;
        }
    }

    public static State questionRequestSuccessfulReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case QUESTION_REQUEST_SUCCESSFUL:
                Question question = (Question) action.value;
                return ImmutableState.builder()
                        .from(oldState)
                        // Merge new Question Data
                        .putQuestions(question.getKey(), question)
                        // Set isFetching Flag to false
                        .isFetching(false)
                        .build();
            default:
                return oldState;
        }
    }

    public static State questionRequestFailedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case QUESTION_REQUEST_FAILED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(false)
                        .displayErrorMessage((String) action.value)
                        .build();
            default:
                return oldState;
        }
    }


    public static State questionListRequestInitiatedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LIST_QUESTION_REQUEST_INITIATED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(true)
                        .build();
            default:
                return oldState;
        }
    }

    public static State questionListRequestSuccessfulReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LIST_QUESTION_REQUEST_SUCCESSFUL:
                List<Question> questions = (List<Question>) action.value;
                List<String> questionKeys = Stream.of(questions)
                        .map(question -> question.getKey())
                        .collect(Collectors.toList());
                return ImmutableState.builder()
                        .from(oldState)
                        // Merge new Question Data
                        .putAllQuestions(Helpers.convertToMap(questions))
                        // Set isFetching Flag to false
                        .isFetching(false)
                        .build();
            default:
                return oldState;
        }
    }

    public static State questionListRequestFailedReducer(Action action, State oldState) {
        Actions.ActionType type = (Actions.ActionType) action.type;
        switch(type) {
            case LIST_QUESTION_REQUEST_FAILED:
                return ImmutableState.builder()
                        .from(oldState)
                        // Set isFetching Flag to true
                        .isFetching(false)
                        .displayErrorMessage((String) action.value)
                        .build();
            default:
                return oldState;
        }
    }

}
