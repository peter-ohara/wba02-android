package com.pascoapp.wba02_android.services.courses;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by peter on 1/31/17.
 */

public interface CourseService {

    @GET("/courses/{id}.json")
    Observable<Course> getCourse(@Path("id") Integer id);

    @GET("/courses.json")
    Observable<List<Course>> getCourses();
}
