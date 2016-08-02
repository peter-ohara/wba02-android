package com.pascoapp.wba02_android.main;

import com.pascoapp.wba02_android.takeTest.TestViewModel;

/**
 * Created by peter on 7/28/16.
 */

public class MainListItem {

    private CourseViewModel courseViewModel;
    private TestViewModel testViewModel;

    public MainListItem(CourseViewModel courseViewModel) {
        this.courseViewModel = courseViewModel;
    }

    public MainListItem(TestViewModel testViewModel) {
        this.testViewModel = testViewModel;
    }

    public CourseViewModel getCourseViewModel() {
        return courseViewModel;
    }

    public TestViewModel getTestViewModel() {
        return testViewModel;
    }

    @Override
    public String toString() {
        return "MainListItem{" +
                "courseViewModel=" + courseViewModel +
                ", testViewModel=" + testViewModel +
                '}';
    }
}
