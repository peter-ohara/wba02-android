package com.pascoapp.wba02_android.storeScreen;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by peter on 3/18/17.
 */

public interface StoreScreenService {

    @GET("store_screen")
    Observable<List<Course>> getData();
}
