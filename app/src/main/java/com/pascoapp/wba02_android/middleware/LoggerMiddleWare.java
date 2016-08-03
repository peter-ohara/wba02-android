package com.pascoapp.wba02_android.middleware;

import android.util.Log;

import trikita.jedux.Store;

/**
 * Created by peter on 7/30/16.
 */

public class LoggerMiddleWare<A, S> implements Store.Middleware<A, S> {
    @Override
    public void dispatch(Store<A, S> store, A action, Store.NextDispatcher<A> next) {
        Log.d("Redux", "--> " + action.toString());
        next.dispatch(action);
        Log.d("Redux", "<-- " + store.getState().toString());
    }
}
