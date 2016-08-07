package com.pascoapp.wba02_android;

import com.pascoapp.wba02_android.main.CourseViewHolder;
import com.pascoapp.wba02_android.main.MainComponent;
import com.pascoapp.wba02_android.main.TestViewHolder;
import com.pascoapp.wba02_android.takeTest.TestOverview.TestOverviewComponent;

import javax.inject.Singleton;
import dagger.Component;


@Singleton
@Component(modules = { StoreModule.class })
public interface StoreComponent {
    void inject(MainActivity mainActivity);

    void inject(MainComponent mainComponent);
    void inject(TestOverviewComponent testOverviewComponent);

    void inject(CourseViewHolder courseViewHolder);
    void inject(TestViewHolder testViewHolder);
}
