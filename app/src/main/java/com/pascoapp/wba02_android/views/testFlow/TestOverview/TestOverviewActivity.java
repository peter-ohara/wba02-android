package com.pascoapp.wba02_android.views.testFlow.TestOverview;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.pascoapp.wba02_android.R;

public class TestOverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TestOverviewView(this));
    }
}
