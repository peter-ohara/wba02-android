package com.pascoapp.wba02_android;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import trikita.jedux.Action;
import trikita.jedux.Store;

/**
 * Created by peter on 7/30/16.
 */

public class PersistenceMiddleWare implements Store.Middleware<Action, State> {

    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference appStateRef = FirebaseDatabase.getInstance().getReference()
            .child("users").child(userId).child("appState");

    private final Gson mGson;


    public PersistenceMiddleWare() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapterFactory(new GsonAdaptersState());
        mGson = gsonBuilder.create();
    }

    @Override
    public void dispatch(Store<Action, State> store, Action action, Store.NextDispatcher<Action> next) {
        next.dispatch(action);
        String json = mGson.toJson(store.getState());
        // TODO: Store actual json not stringified version
        appStateRef.setValue(json);
    }
}
