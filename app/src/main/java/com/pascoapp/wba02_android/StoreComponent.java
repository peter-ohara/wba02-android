package com.pascoapp.wba02_android;

import com.pascoapp.wba02_android.main.MainScreenView;

import javax.inject.Singleton;
import dagger.Component;


@Singleton
@Component(modules = { StoreModule.class })
public interface StoreComponent {
    void inject(MainActivity mainActivity);
    void inject(MainScreenView mainScreenView);
}
