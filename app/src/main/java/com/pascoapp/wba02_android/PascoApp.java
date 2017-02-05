package com.pascoapp.wba02_android;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crash.FirebaseCrash;

import timber.log.Timber;


public class PascoApp extends Application {

    private static PascoApp instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        Timber.plant(BuildConfig.DEBUG
                ? new Timber.DebugTree()
                : new CrashReportingTree());

        FirebaseAnalytics.getInstance(this);

        Timber.i("Creating our Application");
    }

    public static PascoApp getInstance () {
        return instance;
    }

    public static boolean hasNetwork () {
        return instance.checkIfHasNetwork();
    }

    public boolean checkIfHasNetwork() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService( Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private class CrashReportingTree extends Timber.Tree {

        @Override
        protected void log(int priority, String tag, String message, Throwable throwable) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }

            Throwable t = throwable != null
                    ? throwable
                    : new Exception(message);


            // Firebase Crash Reporting
            FirebaseCrash.logcat(priority, tag, message);
            FirebaseCrash.report(t);
        }
    }

}