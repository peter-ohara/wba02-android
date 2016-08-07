package com.pascoapp.wba02_android.views.signIn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pascoapp.wba02_android.MainActivity;
import com.pascoapp.wba02_android.R;

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

        // Online offline access of data stored in Firebase
        // Must be done in the entry point of the app.
        // Which happens to be here
        // FirebaseDatabase.getInstance().setPersistenceEnabled(true);

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
                                    AuthUI.FACEBOOK_PROVIDER,
                                    AuthUI.GOOGLE_PROVIDER)
                            .setTheme(R.style.AppTheme)
                            .build(),
                    RC_SIGN_IN);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // userKey is signed in!
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                writeNewUser(user.getUid(), user.getDisplayName(), user.getEmail());

                startMainActivity();
            } else {
                // userKey is not signed in. Maybe just wait for the userKey to press
                // "sign in" again, or show a message
            }
        }
    }

    private void writeNewUser(String userId, String name, String email) {
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        database.child("users").child(userId).child("email").setValue(email);
        database.child("users").child(userId).child("username").setValue(name);
    }

    private void startMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
