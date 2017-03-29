package com.pascoapp.wba02_android.data.course;


import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

interface CourseServiceApi {

    @GET("main_screen")
    Observable<List<Course>> getCourses();

    @GET("buy_tests_screen/{id}")
    Observable<Course> getCourse(@Path("id") Integer courseId);
}
