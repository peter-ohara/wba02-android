package com.pascoapp.wba02_android.dataFetching;

import com.google.firebase.database.Query;

import java.util.List;

import trikita.jedux.Action;

/**
 * Created by peter on 8/4/16.
 */

public class LecturerDatabaseActions {

    private enum ActionType {
        REQUEST_LECTURER,
        RECEIVE_LECTURER_WITH_SUCCEESS,
        RECEIVE_LECTURER_WITH_FAILURE,

        REQUEST_LIST_OF_LECTURERS,
        RECEIVE_LIST_OF_LECTURERS_WITH_SUCCEESS,
        RECEIVE_LIST_OF_LECTURERS_WITH_FAILURE
    }

    public static Action requestLecturer(String key) {
        return new Action<>(ActionType.REQUEST_LECTURER,
                key);
    }

    public static Action receiveLecturerWithSuccess(Lecturer lecturer) {
        return new Action<>(ActionType.RECEIVE_LECTURER_WITH_SUCCEESS,
                lecturer);
    }

    public static Action receiveLecturerWithFailure(String databaseErrorMessage) {
        return new Action<>(ActionType.RECEIVE_LECTURER_WITH_FAILURE,
                databaseErrorMessage);
    }


    public static Action requestListOfLecturers(Query key) {
        return new Action<>(ActionType.REQUEST_LIST_OF_LECTURERS,
                key);
    }

    public static Action receiveListOfLecturersWithSuccess(List<Lecturer> lecturers) {
        return new Action<>(ActionType.RECEIVE_LIST_OF_LECTURERS_WITH_SUCCEESS,
                lecturers);
    }

    public static Action receiveListOfLecturersWithFailure(String databaseErrorMessage) {
        return new Action<>(ActionType.RECEIVE_LIST_OF_LECTURERS_WITH_FAILURE,
                databaseErrorMessage);
    }
}
