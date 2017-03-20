package com.pascoapp.wba02_android.signInScreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.pascoapp.wba02_android.APIUtils;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.mainScreen.MainActivity;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Checks if there's a current user
 * If there is start AuthenticateActivity
 * If not start RegistrationActivity
 */
public class CheckCurrentUser extends Activity {

    private static final int RC_SIGN_IN = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branded_launch_screen);
    }

    @Override
    protected void onResume() {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() == null ||  getAuthToken(this) == null) {

            if (auth.getCurrentUser() == null) Timber.d("Not signed in to Firebase");
            if (getAuthToken(this) == null) Timber.d("Not signed in to Rails server");
            Timber.d("Signing them in...");

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
        } else {
            // already signed in
            Timber.d("Already signed in.");
            Timber.d("Starting MainActivity...");
            startMainActivity();
        }

        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK) {
                // user is signed in!
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                createOrUpdateUserInRailsThenLaunchMainActivity(user);
            } else {
                // user is not signed in. Maybe just wait for the user to press
                // "sign in" again, or show a message
                Toast.makeText(this, "Error signing in. Please try again.", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    public void createOrUpdateUserInRailsThenLaunchMainActivity(FirebaseUser firebaseUser) {
        Map<String, User> body = new HashMap<>();
        body.put("user", getRailsUser(firebaseUser));

        // Make a retrofit call that
        // if the user exists, update his info
        // if the user doesn't exist create him
        APIUtils.getPascoService(this, signInService.class).createOrUpdate(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(returnedUser -> {
                    saveAuthToken(this, returnedUser);
                    startMainActivity();
                });
    }

    private User getRailsUser(FirebaseUser firebaseUser) {
        // User is signed in
        String displayName = firebaseUser.getDisplayName();
        String email = firebaseUser.getEmail();
        Uri photoUrl = firebaseUser.getPhotoUrl();

        // If the above were null, iterate the provider data
        // and set with the first non null data
        for (UserInfo userInfo : firebaseUser.getProviderData()) {
            if (displayName == null && userInfo.getDisplayName() != null) {
                displayName = userInfo.getDisplayName();
            }
            if (photoUrl == null && userInfo.getPhotoUrl() != null) {
                photoUrl = userInfo.getPhotoUrl();
            }
        }

        User user = new User();
        user.displayName = displayName;
        user.email = email;
        if (photoUrl != null) {
            user.profileUrl = photoUrl.toString();
        }

        return user;
    }

    private void startMainActivity() {
        Timber.d("Starting MainActivity");
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public static String getAuthToken(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_auth_token),
                MODE_PRIVATE);

        return sharedPref.getString(context.getString(R.string.auth_token), null);
    }

    private static void saveAuthToken(Context context, User user) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_auth_token),
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.auth_token), user.authToken);
        editor.commit();
    }

    private static void clearAllPreferences(Activity activity) {
        activity.getSharedPreferences(
                activity.getString(R.string.preference_file_auth_token),
                MODE_PRIVATE).edit().clear().commit();
    }

    public static void logout(Activity activity) {
        AuthUI.getInstance()
                .signOut(activity)
                .addOnCompleteListener(task -> {
                    // user is now signed out
                    // Clear all preferences
                    clearAllPreferences(activity);
                    activity.startActivity(new Intent(activity, CheckCurrentUser.class));
                    activity.finish();
                });
    }
}
