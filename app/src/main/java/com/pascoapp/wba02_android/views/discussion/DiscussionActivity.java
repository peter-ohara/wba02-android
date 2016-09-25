package com.pascoapp.wba02_android.views.discussion;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.pascoapp.wba02_android.R;

public class DiscussionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        setupToolbar();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        setTitle("Discussion");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // When the action bar's Up/Home button
            // Which in our case is the close button
            // Do the same thing as when the back button is pressed
            // In most cases this preserves the state of TakeTestActivity
            // from which this DiscussionActivity was launched.
            // TODO: Find more robust solution to the above problem.
            // Consult here https://developer.android.com/training/implementing-navigation/ancestral.html
            // It may give you some ideas
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
