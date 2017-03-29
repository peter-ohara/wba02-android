package com.pascoapp.wba02_android.data.user;

import android.support.annotation.NonNull;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class UserRepositories {

    public UserRepositories() {
        // no instance
    }

    private static UserRepository sRepository = null;

    public synchronized static UserRepository getInMemoryRepoInstance(
            @NonNull UserServiceApi userServiceApi) {

        checkNotNull(userServiceApi);
        if (null == sRepository) {
            sRepository = new InMemoryUserRepository(userServiceApi);
        }
        return sRepository;
    }
}
