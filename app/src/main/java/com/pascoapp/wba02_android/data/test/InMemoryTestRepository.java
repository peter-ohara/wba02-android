package com.pascoapp.wba02_android.data.test;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.google.common.collect.ImmutableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.google.common.base.Preconditions.checkNotNull;

public class InMemoryTestRepository implements TestRepository {

    private final TestServiceApi mTestServiceApi;

    /**
     * This method has reduced visibility for testing and is only visible to tests in the same
     * package.
     */
    @VisibleForTesting
    private List<Test> mCachedTests;

    public InMemoryTestRepository(@NonNull TestServiceApi testServiceApi) {
        mTestServiceApi = checkNotNull(testServiceApi);
    }

    @Override
    public Observable<List<Test>> getTests() {

        if (mCachedTests == null) {
            return mTestServiceApi.getTests()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .concatMap(tests -> {
                        mCachedTests = ImmutableList.copyOf(tests);
                        return Observable.just(mCachedTests);
                    });
        } else {
            return Observable.just(mCachedTests);
        }
    }

    @Override
    public Observable<Test> getTest(@NonNull Integer testId) {
        checkNotNull(testId);

        return mTestServiceApi.getTest(testId);
    }

    @Override
    public void saveTest(@NonNull Test test) {
        checkNotNull(test);

        Map<String, Test> testMap = new HashMap<>();
        testMap.put("test", test);
        mTestServiceApi.save(testMap);
    }

    @Override
    public void refreshData() {
        mCachedTests = null;
    }
}
