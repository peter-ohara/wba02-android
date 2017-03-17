package com.pascoapp.wba02_android.testOverviewScreen;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by peter on 2/5/17.
 */

public interface TestOverviewService {

    @GET("quiz_overview_screen/{id}")
    Observable<TestOverviewItem> getData(@Path("id") Integer dataId);

}
