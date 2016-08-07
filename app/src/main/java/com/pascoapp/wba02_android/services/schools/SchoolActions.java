package com.pascoapp.wba02_android.services.schools;

import com.google.firebase.database.Query;
import com.pascoapp.wba02_android.Actions.ActionType;

import java.util.List;

import trikita.jedux.Action;

/**
 * Created by peter on 8/4/16.
 */

public class SchoolActions {

    public static Action schoolRequestInitiated(String key) {
        return new Action<>(ActionType.SCHOOL_REQUEST_INITIATED,
                key);
    }

    public static Action schoolRequestSucceeded(School school) {
        return new Action<>(ActionType.SCHOOL_REQUEST_SUCCESSFUL,
                school);
    }

    public static Action schoolRequestFailed(String databaseErrorMessage) {
        return new Action<>(ActionType.SCHOOL_REQUEST_FAILED,
                databaseErrorMessage);
    }


    public static Action listSchoolRequestInitiated(Query key) {
        return new Action<>(ActionType.LIST_SCHOOL_REQUEST_INITIATED,
                key);
    }

    public static Action listSchoolRequestSucceeded(List<School> schools) {
        return new Action<>(ActionType.LIST_SCHOOL_REQUEST_SUCCESSFUL,
                schools);
    }

    public static Action listSchoolRequestFailed(String databaseErrorMessage) {
        return new Action<>(ActionType.LIST_SCHOOL_REQUEST_FAILED,
                databaseErrorMessage);
    }
}
