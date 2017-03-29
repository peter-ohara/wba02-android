package com.pascoapp.wba02_android.main.adapter;

import com.pascoapp.wba02_android.data.course.Course;

/**
 * Created by peter on 1/31/17.
 */

public class MainScreenHeader implements MainScreenItem {
    public Course course;

    public MainScreenHeader(Course course) {
        this.course = course;
    }
}
