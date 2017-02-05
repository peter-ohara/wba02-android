package com.pascoapp.wba02_android.mainScreen;

import java.util.List;
import java.util.Map;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by peter on 2/5/17.
 */

public interface MainScreenService {

    @GET("main_screen")
    Observable<Map<String, List<TestItem>>> getData();
}
