package com.pascoapp.wba02_android.mainScreen.adapter;

import com.pascoapp.wba02_android.mainScreen.adapter.MainScreenItem;

/**
 * Created by peter on 1/31/17.
 */

public class MainScreenHeader implements MainScreenItem {
    public String courseCode;

    public MainScreenHeader(String courseCode) {
        this.courseCode = courseCode;
    }
}
