package com.pascoapp.wba02_android.data.test;

import android.support.annotation.NonNull;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class TestRepositories {

    public TestRepositories() {
        // no instance
    }

    private static TestRepository sRepository = null;

    public synchronized static TestRepository getInMemoryRepoInstance(
            @NonNull TestServiceApi testServiceApi) {

        checkNotNull(testServiceApi);
        if (null == sRepository) {
            sRepository = new InMemoryTestRepository(testServiceApi);
        }
        return sRepository;
    }
}
