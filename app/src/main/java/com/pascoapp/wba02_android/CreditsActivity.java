package com.pascoapp.wba02_android;

import android.os.Bundle;
import android.app.Activity;

public class CreditsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
