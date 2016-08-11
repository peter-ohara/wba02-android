package com.pascoapp.wba02_android.views.takeTest.TestOverview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class TestOverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TestOverviewView(this));
    }
}
