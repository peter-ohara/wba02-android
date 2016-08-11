package com.pascoapp.wba02_android;

import com.pascoapp.wba02_android.views.main.MainActivity;
import com.pascoapp.wba02_android.views.main.MainView;
import com.pascoapp.wba02_android.views.main.MainViewListAdapter;
import com.pascoapp.wba02_android.views.takeTest.TestOverview.TestOverviewView;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = { StoreModule.class })
public interface StoreComponent {
    void inject(MainActivity mainActivity);

    void inject(MainView mainView);
    void inject(TestOverviewView testOverviewView);

    void inject(MainViewListAdapter mainViewListAdapter);
}
