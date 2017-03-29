package com.pascoapp.wba02_android.data.test;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;

public interface TestRepository {

    Observable<List<Test>> getTests();

    Observable<Test> getTest(Integer testId);

    void saveTest(@NonNull Test test);

    void refreshData();

}
