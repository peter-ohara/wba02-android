package com.pascoapp.wba02_android.setup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.pascoapp.wba02_android.main.ChooseTestActivity;
import com.pascoapp.wba02_android.R;

public class AuthenticateUserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        boolean userAuthenticated = false;
        // TODO: Authentication Logic.

        if (userAuthenticated) {
            // TODO: Get Programme from Authentication logic
            String programmeId = "3xC8GeiRik";  // Computer Science for now;
            Intent intent = new Intent(AuthenticateUserActivity.this, ChooseTestActivity.class);
            intent.putExtra(ChooseTestActivity.EXTRA_PROGRAMME_ID, programmeId);
            startActivity(intent);
        } else {
            Intent intent = new Intent(AuthenticateUserActivity.this, WelcomeActivity.class);
            startActivity(intent);
        }


    }

}
