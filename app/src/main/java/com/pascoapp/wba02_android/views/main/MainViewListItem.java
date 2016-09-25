package com.pascoapp.wba02_android.views.main;

import com.pascoapp.wba02_android.services.courses.Course;
import com.pascoapp.wba02_android.services.tests.Test;

/**
 * Created by peter on 7/28/16.
 */

public class MainViewListItem {

    private Course course;
    private Test test;

    public MainViewListItem(Course course) {
        this.course = course;
    }

    public MainViewListItem(Test test) {
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
        return "MainViewListItem{" +
                "course=" + course +
                ", test=" + test +
                '}';
    }
}
