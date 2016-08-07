package com.pascoapp.wba02_android.services.questions;

import com.google.firebase.database.Query;
import com.pascoapp.wba02_android.Actions.ActionType;

import java.util.List;

import trikita.jedux.Action;

/**
 * Created by peter on 8/4/16.
 */

public class QuestionActions {

    public static Action questionRequestInitiated(String key) {
        return new Action<>(ActionType.QUESTION_REQUEST_INITIATED,
                key);
    }

    public static Action questionRequestSucceeded(Question question) {
        return new Action<>(ActionType.QUESTION_REQUEST_SUCCESSFUL,
                question);
    }

    public static Action questionRequestFailed(String databaseErrorMessage) {
        return new Action<>(ActionType.QUESTION_REQUEST_FAILED,
                databaseErrorMessage);
    }


    public static Action listQuestionRequestInitiated(Query key) {
        return new Action<>(ActionType.LIST_QUESTION_REQUEST_INITIATED,
                key);
    }

    public static Action listQuestionRequestSucceeded(List<Question> questions) {
        return new Action<>(ActionType.LIST_QUESTION_REQUEST_SUCCESSFUL,
                questions);
    }

    public static Action listQuestionRequestFailed(String databaseErrorMessage) {
        return new Action<>(ActionType.LIST_QUESTION_REQUEST_FAILED,
                databaseErrorMessage);
    }
}
