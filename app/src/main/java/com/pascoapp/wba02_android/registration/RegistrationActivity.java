package com.pascoapp.wba02_android.registration;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.pascoapp.wba02_android.R;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    Button signUpButton, logInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        signUpButton = (Button)findViewById(R.id.signup_button);
        signUpButton.setOnClickListener(this);
        logInButton = (Button)findViewById(R.id.login_button);
        logInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup_button:
                signUp();
                break;
            case R.id.login_button:
                logIn();
                break;
        }
    }

    private void logIn() {
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
    }

    private void signUp(){
        startActivity(new Intent(RegistrationActivity.this, SignUpActivity.class));
    }

}
