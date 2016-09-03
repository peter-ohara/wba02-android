package com.pascoapp.wba02_android.views.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pascoapp.wba02_android.R;

public class CourseActivity extends AppCompatActivity {

    public static final String EXTRA_COURSE_KEY = "com.pascoapp.wba02_android.courseKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);
    }
}
