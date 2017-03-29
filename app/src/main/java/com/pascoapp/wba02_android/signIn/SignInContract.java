package com.pascoapp.wba02_android.signIn;

import android.app.Activity;
import android.content.Context;

interface SignInContract {

    interface View {

        void setProgressIndicator(boolean active);

        void showFirebaseUiSignIn();

        void showMain();

        void showError(String message);
    }

    interface SignInActionsListener {

        void signIn(Activity activity);

        void setUser(Context context);
    }
}
