package com.pascoapp.wba02_android.signIn;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.pascoapp.wba02_android.BuildConfig;
import com.pascoapp.wba02_android.Injection;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.main.MainActivity;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity implements SignInContract.View {

    private static final int RC_SIGN_IN = 101;

    @BindView(R.id.snackbarPosition) ConstraintLayout coordinatorLayout;
    @BindView(R.id.loading_indicator) AVLoadingIndicatorView loadingIndicator;
    @BindView(R.id.sign_in_button) Button signInButton;

    SignInContract.SignInActionsListener mSignInActionsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        loadingIndicator.hide();

        mSignInActionsListener = new SignInPresenter(Injection.provideUserRepository(this), this);

        mSignInActionsListener.signIn(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == ResultCodes.OK) {
                mSignInActionsListener.setUser(this);
                return;
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                    showError(getString(R.string.sign_in_cancelled));
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showError(getString(R.string.no_internet_connection));
                    return;
                }

                if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
                    showError(getString(R.string.unknown_error));
                    return;
                }
            }

            showError(getString(R.string.unknown_sign_in_response));
        }
    }

    @Override
    public void setProgressIndicator(boolean active) {

        if (active) {
            loadingIndicator.post(() -> loadingIndicator.show());
            signInButton.setEnabled(false);
        } else {
            loadingIndicator.post(() -> loadingIndicator.hide());
            signInButton.setEnabled(true);
        }
    }

    @Override
    public void showFirebaseUiSignIn() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                        .setTheme(R.style.SignInTheme)
                        .setLogo(R.drawable.pasco_logo_transparent)
                        .setIsSmartLockEnabled(!BuildConfig.DEBUG)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    public void showMain() {
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showError(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG)
                .show();
    }

    @OnClick(R.id.sign_in_button)
    public void launchFirebaseSignIn() {
        mSignInActionsListener.signIn(this);
    }
}
