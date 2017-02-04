package com.pascoapp.wba02_android.services;

import com.google.gson.JsonObject;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by peter on 1/31/17.
 */

public interface DataService {

    @GET("{api_end_point}")
    Observable<JsonObject> getData(@Path("api_end_point") String apiEndPoint);

    @GET("{api_end_point}/{id}")
    Observable<JsonObject> getData(@Path("api_end_point") String apiEndPoint,
                          @Path("id") Integer dataId);

    @GET("{api_end_point}/{id}")
    Observable getData2(@Path("api_end_point") String apiEndPoint,
                                   @Path("id") Integer dataId);
}
