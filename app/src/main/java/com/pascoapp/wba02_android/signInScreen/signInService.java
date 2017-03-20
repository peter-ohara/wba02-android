package com.pascoapp.wba02_android.signInScreen;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by peter on 3/19/17.
 */

public interface signInService {

    @GET("users")
    Observable<List<User>> getUsers();

    @POST("users")
    Observable<User> createOrUpdate(@Body Map<String, User> user);

    @PUT("users/{id}")
    Observable<User> updateUser(@Path("id") Integer userId, @Body Map<String, User> user);


}
