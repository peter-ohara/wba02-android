package com.pascoapp.wba02_android;

import com.pascoapp.wba02_android.middleware.LoggerMiddleWare;
import com.pascoapp.wba02_android.router.Router;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import trikita.jedux.Action;
import trikita.jedux.Store;

/**
 * Provides the Store instance for the entire app
 * and also subscribes anvil to render whenever the store
 * state changes
 */
@Module
public class StoreModule {

    @Provides
    @Singleton
    Store<Action, State> provideStore(Router router) {
        State initialState = State.Default.build();
        return new Store<Action, State>(
                new RootReducer(),
                initialState,
                new LoggerMiddleWare(),
                router);
    }

    @Provides
    @Singleton
    Router provideRouter() {
        return new Router();
    }
}
