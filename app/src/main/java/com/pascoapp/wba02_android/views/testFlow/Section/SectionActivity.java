package com.pascoapp.wba02_android.views.testFlow.Section;

import android.os.Bundle;
import android.app.Activity;

import com.pascoapp.wba02_android.R;

public class SectionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
