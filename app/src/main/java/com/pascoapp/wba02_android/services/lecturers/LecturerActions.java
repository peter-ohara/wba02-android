package com.pascoapp.wba02_android.services.lecturers;

import com.google.firebase.database.Query;
import com.pascoapp.wba02_android.Actions.ActionType;

import java.util.List;

import trikita.jedux.Action;

/**
 * Created by peter on 8/4/16.
 */

public class LecturerActions {

    public static Action lecturerRequestInitiated(String key) {
        return new Action<>(ActionType.LECTURER_REQUEST_INITIATED,
                key);
    }

    public static Action lecturerRequestSucceeded(Lecturer lecturer) {
        return new Action<>(ActionType.LECTURER_REQUEST_SUCCESSFUL,
                lecturer);
    }

    public static Action lecturerRequestFailed(String databaseErrorMessage) {
        return new Action<>(ActionType.LECTURER_REQUEST_FAILED,
                databaseErrorMessage);
    }


    public static Action listLecturerRequestInitiated(Query key) {
        return new Action<>(ActionType.LIST_LECTURER_REQUEST_INITIATED,
                key);
    }

    public static Action listLecturerRequestSucceeded(List<Lecturer> lecturers) {
        return new Action<>(ActionType.LIST_LECTURER_REQUEST_SUCCESSFUL,
                lecturers);
    }

    public static Action listLecturerRequestFailed(String databaseErrorMessage) {
        return new Action<>(ActionType.LIST_LECTURER_REQUEST_FAILED,
                databaseErrorMessage);
    }
}
