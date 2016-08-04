package com.pascoapp.wba02_android;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.dataFetching.Course;
import com.pascoapp.wba02_android.dataFetching.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import trikita.jedux.Action;
import trikita.jedux.Store;

/**
 * Created by peter on 7/31/16.
 */

public class Actions {

    public enum ActionType {
        // Main screen actions
        SHOW_SCREEN,
        SELECT_COURSE,

        REQUEST_INITIATED,
        REQUEST_SUCCESSFUL,
        REQUEST_FAILED,

        LIST_REQUEST_INITIATED,
        LIST_REQUEST_SUCCESSFUL,
        LIST_REQUEST_FAILED
    }

    public static Action showScreen(Screens screen) {
        return new Action<>(ActionType.SHOW_SCREEN,
                screen);
    }

    public static Action selectCourse(String testKey) {
        return new Action<>(ActionType.SELECT_COURSE,
                testKey);
    }
}
