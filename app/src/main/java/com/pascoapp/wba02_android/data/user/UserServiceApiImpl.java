package com.pascoapp.wba02_android.data.user;

import android.content.Context;

import com.pascoapp.wba02_android.APIUtils;

import java.util.List;
import java.util.Map;

import retrofit2.http.Body;
import retrofit2.http.Path;
import rx.Observable;

public class UserServiceApiImpl implements UserServiceApi {

    private final Context mContext;

    public UserServiceApiImpl(Context context) {
        mContext = context;
    }

    @Override
    public Observable<User> getUser(@Path("id") String email) {
        return APIUtils.getPascoService(mContext, UserServiceApi.class).getUser(email);
    }

    @Override
    public Observable<User> setUser(@Body Map<String, User> userMap) {
        return APIUtils.getPascoService(mContext, UserServiceApi.class).setUser(userMap);
    }

    @Override
    public Observable<User> buyTests(@Body List<Integer> testIds) {
        return APIUtils.getPascoService(mContext, UserServiceApi.class).buyTests(testIds);
    }
}
