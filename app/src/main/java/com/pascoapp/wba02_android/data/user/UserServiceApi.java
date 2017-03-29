package com.pascoapp.wba02_android.data.user;


import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

interface UserServiceApi {

    @GET("users/{id}")
    Observable<User> getUser(@Path("id") String email);

    @POST("users")
    Observable<User> setUser(@Body Map<String, User> userMap);

    @POST("users")
    Observable<User> buyTests(@Body List<Integer> testIds);
}
