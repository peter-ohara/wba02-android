package com.pascoapp.wba02_android;

import android.app.Application;

/**
 * Application class for the Pasco App
 */
public class App extends Application {

    public static final String CURRENT_COURSE_ID = "com.pascoapp.wba02_android.App.courseId";
    public static final String CURRENT_COURSE_CODE = "com.pascoapp.wba02_android.App.courseCode";
    public static final String CURRENT_COURSE_NAME = "com.pascoapp.wba02_android.App.courseName";

    @Override
    public void onCreate() {

        super.onCreate();
    }
}
