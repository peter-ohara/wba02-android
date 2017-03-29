package com.pascoapp.wba02_android.data.test;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

interface TestServiceApi {

    @GET("quiz_overview_screen/{id}")
    Observable<Test> getTest(@Path("id") Integer testId);

    @GET("quiz_overview_screen")
    Observable<List<Test>> getTests();

    @POST("quiz_overview_screen")
    Observable<Test> save(@Body Map<String, Test> testMap);

}
