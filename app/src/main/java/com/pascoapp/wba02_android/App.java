package com.pascoapp.wba02_android;

import android.app.Application;

/**
 * Created by peter on 8/2/16.
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("INITIALIZING APP FROM PID: " + android.os.Process.myPid());
    }

}
