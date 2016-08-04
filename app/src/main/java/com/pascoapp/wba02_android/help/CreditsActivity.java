package com.pascoapp.wba02_android.help;

import android.app.Activity;
import android.os.Bundle;

import com.pascoapp.wba02_android.R;

public class CreditsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
