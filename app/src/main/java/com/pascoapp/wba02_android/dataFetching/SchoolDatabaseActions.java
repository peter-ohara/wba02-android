package com.pascoapp.wba02_android.dataFetching;

import com.google.firebase.database.Query;

import java.util.List;

import trikita.jedux.Action;

/**
 * Created by peter on 8/4/16.
 */

public class SchoolDatabaseActions {

    private enum ActionType {
        REQUEST_SCHOOL,
        RECEIVE_SCHOOL_WITH_SUCCEESS,
        RECEIVE_SCHOOL_WITH_FAILURE,

        REQUEST_LIST_OF_SCHOOLS,
        RECEIVE_LIST_OF_SCHOOLS_WITH_SUCCEESS,
        RECEIVE_LIST_OF_SCHOOLS_WITH_FAILURE
    }

    public static Action requestSchool(String key) {
        return new Action<>(ActionType.REQUEST_SCHOOL,
                key);
    }

    public static Action receiveSchoolWithSuccess(School school) {
        return new Action<>(ActionType.RECEIVE_SCHOOL_WITH_SUCCEESS,
                school);
    }

    public static Action receiveSchoolWithFailure(String databaseErrorMessage) {
        return new Action<>(ActionType.RECEIVE_SCHOOL_WITH_FAILURE,
                databaseErrorMessage);
    }


    public static Action requestListOfSchools(Query key) {
        return new Action<>(ActionType.REQUEST_LIST_OF_SCHOOLS,
                key);
    }

    public static Action receiveListOfSchoolsWithSuccess(List<School> schools) {
        return new Action<>(ActionType.RECEIVE_LIST_OF_SCHOOLS_WITH_SUCCEESS,
                schools);
    }

    public static Action receiveListOfSchoolsWithFailure(String databaseErrorMessage) {
        return new Action<>(ActionType.RECEIVE_LIST_OF_SCHOOLS_WITH_FAILURE,
                databaseErrorMessage);
    }
}
