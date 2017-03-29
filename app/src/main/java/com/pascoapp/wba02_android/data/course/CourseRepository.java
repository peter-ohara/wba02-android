package com.pascoapp.wba02_android.data.course;

import java.util.List;

import rx.Observable;

public interface CourseRepository {

    Observable<Course> getCourse(Integer courseId);

    Observable<List<Course>> getCourses();

    void refreshData();
}
