package com.pascoapp.wba02_android.services;

import com.pascoapp.wba02_android.services.courses.CourseService;
import com.pascoapp.wba02_android.services.tests.TestService;

/**
 * Created by peter on 1/31/17.
 */
public class APIUtils {
    public static final String BASE_URL = "https://nameless-forest-97776.herokuapp.com/android_v1/";

    public static DataService getDataService() {
        return RetrofitClient.getClient(BASE_URL).create(DataService.class);
    }

    public static TakeTestService getTakeTestService() {
        return RetrofitClient.getClient(BASE_URL).create(TakeTestService.class);
    }

    public static CourseService getCourseService() {
        return RetrofitClient.getClient(BASE_URL).create(CourseService.class);
    }

    public static TestService getTestService() {
        return RetrofitClient.getClient(BASE_URL).create(TestService.class);
    }
}
