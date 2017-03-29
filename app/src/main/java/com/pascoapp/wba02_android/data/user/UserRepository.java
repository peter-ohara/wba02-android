package com.pascoapp.wba02_android.data.user;

import java.util.List;
import java.util.Map;

import rx.Observable;

public interface UserRepository {

    Observable<User> getUser(String email);

    Observable<User> setUser(Map<String, User> userMap);

    Observable<User> buyTests(List<Integer> testIds);

    void refreshData();
}
