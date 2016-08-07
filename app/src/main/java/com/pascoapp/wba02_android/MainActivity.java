package com.pascoapp.wba02_android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.pascoapp.wba02_android.views.help.HelpActivity;
import com.pascoapp.wba02_android.views.Inbox.MessageListActivity;
import com.pascoapp.wba02_android.router.Route;
import com.pascoapp.wba02_android.router.RouteActions;
import com.pascoapp.wba02_android.router.Router;
import com.pascoapp.wba02_android.views.settings.SettingsActivity;
import com.pascoapp.wba02_android.views.signIn.CheckCurrentUser;

import javax.inject.Inject;

import trikita.anvil.Anvil;
import trikita.jedux.Action;
import trikita.jedux.Store;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Inject
    Store<Action, State> store;

    @Inject
    Router router;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branded_launch_screen);

        App.getStoreComponent().inject(this);

        // Re-render UI via anvil when the store state changes
        store.subscribe(Anvil::render);

        router.init(this);
        if (savedInstanceState != null) {
            router.load(savedInstanceState);
        } else {
            // TODO: Rehydrate state and use it to show screen
            // Check what the current screen is and show that one
            Route currentRoute = store.getState().currentRoute();
            store.dispatch(RouteActions.showScreen(currentRoute));
        }
    }

    @Override
    public void onBackPressed() {
        if (!router.back()) {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_inbox:
                startActivity(new Intent(MainActivity.this, MessageListActivity.class));
                break;
            case R.id.action_logout:
                logout();
                break;
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.action_feedback:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","pascoapp.wb@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback on Pasco Android App");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Write your feedback here");
                startActivity(Intent.createChooser(emailIntent, "Send email..."));
                break;
            case R.id.action_help:
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // userKey is now signed out
                        startActivity(new Intent(MainActivity.this, CheckCurrentUser.class));
                        finish();
                    }
                });
    }

}
