package com.pascoapp.wba02_android.data.test;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.Path;
import rx.Observable;

public class TestServiceAPIApiEndpoint implements TestServiceApi {
    @Override
    public Observable<Test> getTest(@Path("id") Integer testId) {
        return null;
    }

    @Override
    public Observable<List<Test>> getTests() {
        return null;
    }

    @Override
    public Observable<Test> save(@Body Map<String, Test> testMap) {
        return null;
    }
}
