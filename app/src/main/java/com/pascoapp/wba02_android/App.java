package com.pascoapp.wba02_android;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.parse.Parse;
import com.parse.ParseObject;
import com.pascoapp.wba02_android.parseSubClasses.Course;
import com.pascoapp.wba02_android.parseSubClasses.Lecturer;
import com.pascoapp.wba02_android.parseSubClasses.Message;
import com.pascoapp.wba02_android.parseSubClasses.Programme;
import com.pascoapp.wba02_android.parseSubClasses.Question;
import com.pascoapp.wba02_android.parseSubClasses.School;
import com.pascoapp.wba02_android.parseSubClasses.Student;
import com.pascoapp.wba02_android.parseSubClasses.Test;
import io.fabric.sdk.android.Fabric;

/**
 * Application class for the Pasco App
 */
public class App extends Application {

    public static final String CURRENT_COURSE_ID = "com.pascoapp.wba02_android.App.courseId";

    @Override
    public void onCreate() {

        // Register sub-classes
        ParseObject.registerSubclass(Lecturer.class);
        ParseObject.registerSubclass(Student.class);
        ParseObject.registerSubclass(School.class);
        ParseObject.registerSubclass(Programme.class);
        ParseObject.registerSubclass(Course.class);
        ParseObject.registerSubclass(Test.class);
        ParseObject.registerSubclass(Question.class);
        ParseObject.registerSubclass(Message.class);

        // Initialize parse
        Parse.initialize(this);

        super.onCreate();
        Fabric.with(this, new Crashlytics());
    }
}
