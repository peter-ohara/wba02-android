package com.pascoapp.wba02_android.dataFetching;

import com.google.firebase.database.Query;

import java.util.List;

import trikita.jedux.Action;

/**
 * Created by peter on 8/4/16.
 */

public class QuestionDatabaseActions {

    private enum ActionType {
        REQUEST_QUESTION,
        RECEIVE_QUESTION_WITH_SUCCEESS,
        RECEIVE_QUESTION_WITH_FAILURE,

        REQUEST_LIST_OF_QUESTIONS,
        RECEIVE_LIST_OF_QUESTIONS_WITH_SUCCEESS,
        RECEIVE_LIST_OF_QUESTIONS_WITH_FAILURE
    }

    public static Action requestQuestion(String key) {
        return new Action<>(ActionType.REQUEST_QUESTION,
                key);
    }

    public static Action receiveQuestionWithSuccess(Question question) {
        return new Action<>(ActionType.RECEIVE_QUESTION_WITH_SUCCEESS,
                question);
    }

    public static Action receiveQuestionWithFailure(String databaseErrorMessage) {
        return new Action<>(ActionType.RECEIVE_QUESTION_WITH_FAILURE,
                databaseErrorMessage);
    }


    public static Action requestListOfQuestions(Query key) {
        return new Action<>(ActionType.REQUEST_LIST_OF_QUESTIONS,
                key);
    }

    public static Action receiveListOfQuestionsWithSuccess(List<Question> questions) {
        return new Action<>(ActionType.RECEIVE_LIST_OF_QUESTIONS_WITH_SUCCEESS,
                questions);
    }

    public static Action receiveListOfQuestionsWithFailure(String databaseErrorMessage) {
        return new Action<>(ActionType.RECEIVE_LIST_OF_QUESTIONS_WITH_FAILURE,
                databaseErrorMessage);
    }
}
