package com.pascoapp.wba02_android.services.tests;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by peter on 1/31/17.
 */

public interface TestService {

    @GET("tests/{id}.json")
    Observable<Test> getTest(@Path("id") Integer id);

    @GET("tests.json")
    Observable<List<Test>> getTests();

    @GET("tests.json")
    Observable<List<Test>> getTests(@Query("course_id") Integer course_id);
}
