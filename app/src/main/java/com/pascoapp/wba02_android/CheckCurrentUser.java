package com.pascoapp.wba02_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.parse.ParseUser;
import com.pascoapp.wba02_android.setup.AuthenticateUserActivity;
import com.pascoapp.wba02_android.registration.RegistrationActivity;

/**
 * Checks if there's a current user
 * If there is start AuthenticateActivity
 * If not start RegistrationActivity
 */
public class CheckCurrentUser extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //check if user is null; authenticate or registration
        if (ParseUser.getCurrentUser() == null) {
            startActivity(new Intent(this, RegistrationActivity.class));
        } else {
            startActivity(new Intent(this, AuthenticateUserActivity.class));
        }

        super.onCreate(savedInstanceState);
    }
}
