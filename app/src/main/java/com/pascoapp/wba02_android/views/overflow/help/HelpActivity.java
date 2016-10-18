package com.pascoapp.wba02_android.views.overflow.help;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.pascoapp.wba02_android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpActivity extends AppCompatActivity {

    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: Add progress Indicator for when webpage is loading

        setContentView(R.layout.activity_help);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        webview.setBackgroundColor(Color.TRANSPARENT);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setWebViewClient(new WebViewClient());

//        webview.loadUrl("https://www.pascoapp.com/help");
        webview.loadUrl("http://mspoweruser.com/universal-office-apps-shares-96-of-its-code-with-windows-version/");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_help, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_view_in_google_play_store:
                // getPackageName() from Context or Activity object
                final String appPackageName = getPackageName();
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                break;
            case R.id.action_credits:
                break;
            case R.id.action_terms_of_service:
                break;
            case R.id.action_privacy_policy:
                break;
            case R.id.action_version_info:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
