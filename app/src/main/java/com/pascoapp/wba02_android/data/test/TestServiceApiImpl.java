package com.pascoapp.wba02_android.data.test;

import android.content.Context;

import com.pascoapp.wba02_android.APIUtils;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.Path;
import rx.Observable;

public class TestServiceApiImpl implements TestServiceApi {

    private final Context mContext;

    public TestServiceApiImpl(Context context) {
        mContext = context;
    }

    @Override
    public Observable<Test> getTest(@Path("id") Integer testId) {
        return APIUtils.getPascoService(mContext, TestServiceApi.class).getTest(testId);
    }

    @Override
    public Observable<List<Test>> getTests() {
        return APIUtils.getPascoService(mContext, TestServiceApi.class).getTests();
    }

    @Override
    public Observable<Test> save(@Body Map<String, Test> testMap) {
        return APIUtils.getPascoService(mContext, TestServiceApi.class).save(testMap);
    }
}
