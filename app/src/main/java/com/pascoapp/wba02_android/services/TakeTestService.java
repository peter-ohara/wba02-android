package com.pascoapp.wba02_android.services;

import java.util.Map;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by peter on 1/31/17.
 */

public interface TakeTestService {

    @GET("take_test_screen/{id}")
    Observable<Map<String, Object>> getData(@Path("id") Integer testId);

}
