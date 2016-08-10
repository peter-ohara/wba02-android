package com.pascoapp.wba02_android.views.testFlow.discussion;

import android.os.Bundle;
import android.app.Activity;

import com.pascoapp.wba02_android.R;

public class DiscussionActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
