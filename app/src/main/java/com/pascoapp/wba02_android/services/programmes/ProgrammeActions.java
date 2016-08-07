package com.pascoapp.wba02_android.services.programmes;

import com.google.firebase.database.Query;
import com.pascoapp.wba02_android.Actions.ActionType;

import java.util.List;

import trikita.jedux.Action;

/**
 * Created by peter on 8/4/16.
 */

public class ProgrammeActions {

    public static Action programmeRequestInitiated(String key) {
        return new Action<>(ActionType.PROGRAMME_REQUEST_INITIATED,
                key);
    }

    public static Action programmeRequestSucceeded(Programme programme) {
        return new Action<>(ActionType.PROGRAMME_REQUEST_SUCCESSFUL,
                programme);
    }

    public static Action programmeRequestFailed(String databaseErrorMessage) {
        return new Action<>(ActionType.PROGRAMME_REQUEST_FAILED,
                databaseErrorMessage);
    }


    public static Action listProgrammeRequestInitiated(Query key) {
        return new Action<>(ActionType.LIST_PROGRAMME_REQUEST_INITIATED,
                key);
    }

    public static Action listProgrammeRequestSucceeded(List<Programme> programmes) {
        return new Action<>(ActionType.LIST_PROGRAMME_REQUEST_SUCCESSFUL,
                programmes);
    }

    public static Action listProgrammeRequestFailed(String databaseErrorMessage) {
        return new Action<>(ActionType.LIST_PROGRAMME_REQUEST_FAILED,
                databaseErrorMessage);
    }
}
