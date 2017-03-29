package com.pascoapp.wba02_android.data.user;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import java.util.List;
import java.util.Map;

import rx.Observable;

import static com.google.common.base.Preconditions.checkNotNull;

class InMemoryUserRepository implements UserRepository {

    private final UserServiceApi mUserServiceApi;

    /**
     * This method has reduced visibility for testing and is only visible to users in the same
     * package.
     */
    @VisibleForTesting
    private List<User> mCachedUsers;

    InMemoryUserRepository(@NonNull UserServiceApi userServiceApi) {
        mUserServiceApi = checkNotNull(userServiceApi);
    }

    @Override
    public Observable<User> getUser(@NonNull String email) {
        checkNotNull(email);
        return mUserServiceApi.getUser(email);
    }

    @Override
    public Observable<User> setUser(Map<String, User> userMap) {
        return mUserServiceApi.setUser(userMap);
    }

    @Override
    public Observable<User> buyTests(List<Integer> testIds) {
        return mUserServiceApi.buyTests(testIds);
    }

    @Override
    public void refreshData() {
        mCachedUsers = null;
    }
}
