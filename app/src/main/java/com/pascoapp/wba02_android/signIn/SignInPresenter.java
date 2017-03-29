package com.pascoapp.wba02_android.signIn;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.data.user.User;
import com.pascoapp.wba02_android.data.user.UserRepository;
import com.pascoapp.wba02_android.main.MainActivity;

import java.util.HashMap;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static android.content.Context.MODE_PRIVATE;
import static com.google.common.base.Preconditions.checkNotNull;

public class SignInPresenter implements SignInContract.SignInActionsListener {

    private final UserRepository mUserRepository;
    private final SignInContract.View mSignInView;

    SignInPresenter(
            @NonNull UserRepository userRepository, @NonNull SignInContract.View signInView) {
        mUserRepository = checkNotNull(userRepository, "userRepository cannot be null!");
        mSignInView = checkNotNull(signInView, "signInView cannot be null!)");
    }

    @Override
    public void signIn(Activity activity) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String authToken = getAuthToken(activity);

        if (auth.getCurrentUser() == null ||  authToken == null) {
            // not signed in
            mSignInView.showFirebaseUiSignIn();
        } else {
            // already signed in
            mSignInView.showMain();
        }
    }

    @Override
    public void setUser(Context context) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Map<String, User> body = new HashMap<>();
        body.put("user", getRailsUser(firebaseUser));

        mSignInView.setProgressIndicator(true);
        mUserRepository.setUser(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> {
                    saveAuthToken(context, user.authToken);
                    mSignInView.setProgressIndicator(false);
                    mSignInView.showMain();
                }, e -> {
                    mSignInView.setProgressIndicator(false);
                    mSignInView.showError(e.getMessage());
                    e.printStackTrace();
                });
    }

    public static String getAuthToken(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_auth_token),
                MODE_PRIVATE);

        return sharedPref.getString(context.getString(R.string.auth_token), null);
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

    private static void saveAuthToken(Context context, String authToken) {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_auth_token),
                MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(context.getString(R.string.auth_token), authToken);
        editor.commit();
    }

}
