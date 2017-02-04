package com.pascoapp.wba02_android.views.main;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pascoapp.wba02_android.views.BaseActivity;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.views.WebviewActivity;
import com.pascoapp.wba02_android.views.store.ChooseCoursesActivity;
import com.pascoapp.wba02_android.views.signIn.CheckCurrentUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int REQUEST_INVITE = 100 ;
    public static final String MAIN_SCREEN = "main_screen";
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.snackbarPosition) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout mySwipeRefreshLayout;
    @BindView(R.id.coursesList) RecyclerView coursesRecyclerView;
    @BindView(R.id.bottomBar) View bottomBar;

    public static final int BUY_COURSES_REQUEST = 1;

    private MainViewListAdapter mainViewListAdapter;
    private List<JsonObject> mItems = new ArrayList<>();


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected String getApiEndPoint() {
        return MAIN_SCREEN;
    }

    @Override
    protected String getExtraKeyForDataId() {
        return null;
    }

    @Override
    protected void setUpViewItems() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_action_logo);
        setTitle("");

        handleAnyNotificationData();

        coursesRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(linearLayoutManager);

        mainViewListAdapter = new MainViewListAdapter(this, mItems);
        coursesRecyclerView.setAdapter(mainViewListAdapter);

        mySwipeRefreshLayout.setOnRefreshListener(() -> {
            fetchData();
        });
    }

    @Override
    protected void beforeFetchingData() {
        mySwipeRefreshLayout.setRefreshing(true);
        mItems.clear();
    }

    @Override
    protected void afterFetchingData(JsonObject data) {
        mySwipeRefreshLayout.setRefreshing(false);
        for (Map.Entry<String, JsonElement> item : data.entrySet()) {
            // Insert header
            JsonObject header = new JsonObject();
            header.addProperty("type", "header");
            header.addProperty("title", item.getKey());
            mItems.add(header);

            // insert tests
            for (JsonElement test: item.getValue().getAsJsonArray()) {
                mItems.add(test.getAsJsonObject());
            }
        }
        mainViewListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onErrorFetchingData(Throwable e) {
        mySwipeRefreshLayout.setRefreshing(false);
        Snackbar.make(coordinatorLayout, e.getMessage(), Snackbar.LENGTH_LONG)
                .setAction("Retry", view -> fetchData())
                .show();
    }

    private void handleAnyNotificationData() {
        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Toast.makeText(this, "Key: " + key + " Value: " + value, Toast.LENGTH_LONG).show();
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }
        // [END handle_data_extras]
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_store:
                openStore();
                return true;
            case R.id.action_invite:
                openInvite();
                return true;
            case R.id.action_refresh:
                fetchData();
                return true;
            case R.id.action_upload_pasco:
                openPascoUploadScreen();
                return true;
            case R.id.action_logout:
                logout();
                return true;
            case R.id.action_feedback:
                openFeedbackForm();
                return true;
            case R.id.action_help:
                openHelp();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openInvite() {
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                //.setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
                .setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }

    @OnClick(R.id.bottomBar)
    public void openStore() {
        Intent intent = new Intent(MainActivity.this, ChooseCoursesActivity.class);
        startActivityForResult(intent, BUY_COURSES_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == BUY_COURSES_REQUEST) {
            if (resultCode == RESULT_OK) {
                // User may have bought new courses refresh course list
                fetchData();
            }
        }

        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Your invitations have been sent", Toast.LENGTH_SHORT).show();
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                for (String id : ids) {
                    Log.d(TAG, "onActivityResult: sent invitation " + id);
                }
            } else {
                Toast.makeText(this, "Error sending invitations. Please check your credit or data", Toast.LENGTH_SHORT).show();
                // ...
            }

        }


    }

    private void openPascoUploadScreen() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.pascoapp.com/give-us-pasco"));
        startActivity(browserIntent);
    }

    private void logout() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        startActivity(new Intent(MainActivity.this, CheckCurrentUser.class));
                        finish();
                    }
                });
    }

    private void openFeedbackForm() {
        Intent browserIntent = new Intent(MainActivity.this, WebviewActivity.class);
        String url = "https://docs.google.com/forms/d/e/" +
                "1FAIpQLSckq4J5Jf32rK_LSKI4Gq1k0gWthyEj82B_UlySMJxr9ib22A/viewform";
        browserIntent.putExtra(WebviewActivity.EXTRA_URL, url);
        browserIntent.putExtra(WebviewActivity.EXTRA_TITLE, getString(R.string.action_feedback));
        startActivity(browserIntent);
    }

    private void openHelp() {
        Intent browserIntent = new Intent(MainActivity.this, WebviewActivity.class);
        String url = "http://www.pascoapp.com/help";
        browserIntent.putExtra(WebviewActivity.EXTRA_URL, url);
        browserIntent.putExtra(WebviewActivity.EXTRA_TITLE, getString(R.string.action_help));
        startActivity(browserIntent);
    }
}
