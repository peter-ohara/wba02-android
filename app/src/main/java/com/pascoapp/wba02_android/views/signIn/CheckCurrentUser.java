package com.pascoapp.wba02_android.views.signIn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.services.users.Users;
import com.pascoapp.wba02_android.views.main.MainActivity;

import java.util.HashMap;
import java.util.Map;

import static com.firebase.ui.auth.ui.AcquireEmailHelper.RC_SIGN_IN;

/**
 * Checks if there's a current userKey
 * If there is start AuthenticateActivity
 * If not start RegistrationActivity
 */
public class CheckCurrentUser extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branded_launch_screen);
    }

    @Override
    protected void onResume() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in
            startMainActivity();
        } else {
            // not signed in
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setProviders(
                                    AuthUI.EMAIL_PROVIDER,
                                    AuthUI.GOOGLE_PROVIDER)
                            .setTheme(R.style.AppTheme)
                            .build(),
                    RC_SIGN_IN);
        }

        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // userKey is signed in!
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                Map newUser = new HashMap<String, String>();
                newUser.put("email", user.getEmail());
                newUser.put("username", user.getDisplayName());

                Users.put(user.getUid(), newUser);

                startMainActivity();
            } else {
                // userKey is not signed in. Maybe just wait for the userKey to press
                // "sign in" again, or show a message
                Toast.makeText(this, "Error signing in. Please try again.", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
