package com.pascoapp.wba02_android.signInScreen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.mainScreen.MainActivity;


/**
 * Checks if there's a current user
 * If there is start AuthenticateActivity
 * If not start RegistrationActivity
 */
public class CheckCurrentUser extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branded_launch_screen);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startMainActivity();
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
