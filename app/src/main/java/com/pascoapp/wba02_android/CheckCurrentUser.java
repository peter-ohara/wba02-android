package com.pascoapp.wba02_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.firebase.ui.auth.ui.AcquireEmailHelper.RC_SIGN_IN;

/**
 * Checks if there's a current user
 * If there is start AuthenticateActivity
 * If not start RegistrationActivity
 */
public class CheckCurrentUser extends Activity {

    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authenticate_user);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in
            startAuthenticationActivity();
        } else {
            // not signed in
            startActivityForResult(
                    // Get an instance of AuthUI based on the default app
                    AuthUI.getInstance().createSignInIntentBuilder().build(),
                    RC_SIGN_IN);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // user is signed in!
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                writeNewUser(user.getUid(), user.getDisplayName(), user.getEmail());

                startAuthenticationActivity();
            } else {
                // user is not signed in. Maybe just wait for the user to press
                // "sign in" again, or show a message
            }
        }
    }

    private void writeNewUser(String userId, String name, String email) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child("users").child(userId).child("email").setValue(email);
        database.child("users").child(userId).child("username").setValue(name);
    }

    private void startAuthenticationActivity() {
        startActivity(new Intent(this, AuthenticateUserActivity.class));
        finish();
    }

}
