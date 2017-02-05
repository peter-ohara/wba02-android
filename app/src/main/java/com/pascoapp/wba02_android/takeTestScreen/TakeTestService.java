package com.pascoapp.wba02_android.takeTestScreen;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by peter on 2/5/17.
 */

public interface TakeTestService {

    @GET("take_test_screen/{id}")
    Observable<TakeTestItem> getData(@Path("id") Integer dataId);

}
