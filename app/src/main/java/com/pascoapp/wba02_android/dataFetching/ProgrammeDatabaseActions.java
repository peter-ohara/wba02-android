package com.pascoapp.wba02_android.dataFetching;

import com.google.firebase.database.Query;

import java.util.List;

import trikita.jedux.Action;

/**
 * Created by peter on 8/4/16.
 */

public class ProgrammeDatabaseActions {

    private enum ActionType {
        REQUEST_PROGRAMME,
        RECEIVE_PROGRAMME_WITH_SUCCEESS,
        RECEIVE_PROGRAMME_WITH_FAILURE,

        REQUEST_LIST_OF_PROGRAMMES,
        RECEIVE_LIST_OF_PROGRAMMES_WITH_SUCCEESS,
        RECEIVE_LIST_OF_PROGRAMMES_WITH_FAILURE
    }

    public static Action requestProgramme(String key) {
        return new Action<>(ActionType.REQUEST_PROGRAMME,
                key);
    }

    public static Action receiveProgrammeWithSuccess(Programme programme) {
        return new Action<>(ActionType.RECEIVE_PROGRAMME_WITH_SUCCEESS,
                programme);
    }

    public static Action receiveProgrammeWithFailure(String databaseErrorMessage) {
        return new Action<>(ActionType.RECEIVE_PROGRAMME_WITH_FAILURE,
                databaseErrorMessage);
    }


    public static Action requestListOfProgrammes(Query key) {
        return new Action<>(ActionType.REQUEST_LIST_OF_PROGRAMMES,
                key);
    }

    public static Action receiveListOfProgrammesWithSuccess(List<Programme> programmes) {
        return new Action<>(ActionType.RECEIVE_LIST_OF_PROGRAMMES_WITH_SUCCEESS,
                programmes);
    }

    public static Action receiveListOfProgrammesWithFailure(String databaseErrorMessage) {
        return new Action<>(ActionType.RECEIVE_LIST_OF_PROGRAMMES_WITH_FAILURE,
                databaseErrorMessage);
    }
}
