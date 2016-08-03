package com.pascoapp.wba02_android;

import android.app.Application;

/**
 * Created by peter on 8/2/16.
 */

public class App extends Application {

    private static StoreComponent storeComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        storeComponent = DaggerStoreComponent.builder()
                .storeModule(new StoreModule())
                .build();
    }

    public static StoreComponent getStoreComponent() {
        return storeComponent;
    }
}
