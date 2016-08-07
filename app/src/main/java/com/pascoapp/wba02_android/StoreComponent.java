package com.pascoapp.wba02_android;

import com.pascoapp.wba02_android.views.main.MainView;
import com.pascoapp.wba02_android.views.main.MainViewListAdapter;
import com.pascoapp.wba02_android.views.takeTest.TestOverview.TestOverviewComponent;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = { StoreModule.class })
public interface StoreComponent {
    void inject(MainActivity mainActivity);

    void inject(MainView mainView);
    void inject(TestOverviewComponent testOverviewComponent);

    void inject(MainViewListAdapter mainViewListAdapter);
}
