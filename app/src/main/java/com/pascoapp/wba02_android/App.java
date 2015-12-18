package com.pascoapp.wba02_android;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;
import com.pascoapp.wba02_android.Course.Course;
import com.pascoapp.wba02_android.Programme.Programme;
import com.pascoapp.wba02_android.Question.Question;
import com.pascoapp.wba02_android.School.School;
import com.pascoapp.wba02_android.Test.Test;

/**
 * Application class for the Pasco App
 */
public class App extends Application {
    @Override
    public void onCreate() {

        // [Optional] Power your app with Local Datastore. For more info, go to
        // https://parse.com/docs/android/guide#local-datastore
        Parse.enableLocalDatastore(this);

        // Register sub-classes
        ParseObject.registerSubclass(School.class);
        ParseObject.registerSubclass(Programme.class);
        ParseObject.registerSubclass(Course.class);
        ParseObject.registerSubclass(Test.class);
        ParseObject.registerSubclass(Question.class);

        // Initialize parse
        Parse.initialize(this);

        super.onCreate();
    }
}
