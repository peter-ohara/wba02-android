package com.pascoapp.wba02_android.store;

import com.pascoapp.wba02_android.data.course.Course;

import java.util.List;

import retrofit2.http.GET;
import rx.Observable;

interface StoreScreenService {

    @GET("store_screen")
    Observable<List<Course>> getData();
}
