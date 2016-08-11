package com.pascoapp.wba02_android;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by peter on 8/2/16.
 */

public class App extends Application {

    private static StoreComponent storeComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("INITIALIZING APP FROM PID: " + android.os.Process.myPid());

        storeComponent = DaggerStoreComponent.builder()
                .storeModule(new StoreModule())
                .build();
    }

    public static StoreComponent getStoreComponent() {
        return storeComponent;
    }
}
