package com.pascoapp.wba02_android.main;

import com.pascoapp.wba02_android.services.courses.Course;
import com.pascoapp.wba02_android.services.tests.Test;

/**
 * Created by peter on 7/28/16.
 */

public class MainListItem {

    private Course course;
    private Test test;

    public MainListItem(Course course) {
        this.course = course;
    }

    public MainListItem(Test test) {
        this.test = test;
    }

    public Course getCourse() {
        return course;
    }

    public Test getTest() {
        return test;
    }


    @Override
    public String toString() {
        return "MainListItem{" +
                "course=" + course +
                ", test=" + test +
                '}';
    }
}
