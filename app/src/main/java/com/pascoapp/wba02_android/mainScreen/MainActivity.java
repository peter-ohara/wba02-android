package com.pascoapp.wba02_android.mainScreen;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.pascoapp.wba02_android.APIUtils;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.storeScreen.StoreActivity;
import com.pascoapp.wba02_android.WebviewActivity;
import com.pascoapp.wba02_android.mainScreen.adapter.MainScreenHeader;
import com.pascoapp.wba02_android.mainScreen.adapter.MainScreenItem;
import com.pascoapp.wba02_android.mainScreen.adapter.MainScreenTest;
import com.pascoapp.wba02_android.mainScreen.adapter.MainViewListAdapter;
import com.pascoapp.wba02_android.signInScreen.CheckCurrentUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by peter on 2/5/17.
 */

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_INVITE = 100 ;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.snackbarPosition) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout mySwipeRefreshLayout;
    @BindView(R.id.coursesList) RecyclerView coursesRecyclerView;
    @BindView(R.id.bottomBar) View bottomBar;

    public static final int STORE_REQUEST = 1;

    private MainViewListAdapter mainViewListAdapter;
    private List<MainScreenItem> mItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        handleAnyNotificationData();
        setupToolbar();
        setupRecyclerView();

        mySwipeRefreshLayout.setOnRefreshListener(() -> {
            fetchData();
        });

        fetchData();
    }

    private void setupRecyclerView() {
        coursesRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(linearLayoutManager);

        mainViewListAdapter = new MainViewListAdapter(this, mItems);
        coursesRecyclerView.setAdapter(mainViewListAdapter);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_action_logo);
        setTitle("");
    }

    protected void fetchData() {
        mySwipeRefreshLayout.setRefreshing(true);
        mItems.clear();

        APIUtils.getPascoService(MainScreenService.class).getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringListMap -> {
                    mySwipeRefreshLayout.setRefreshing(false);
                    for (Map.Entry<String, List<TestItem>> item : stringListMap.entrySet()) {
                        // Insert header
                        MainScreenHeader header = new MainScreenHeader(item.getKey());
                        mItems.add(header);
                        for (TestItem test : item.getValue()) {
                            MainScreenTest testItem = new MainScreenTest(test);
                            mItems.add(testItem);
                        }
                    }
                    mainViewListAdapter.notifyDataSetChanged();

                    fetchAllAppDataForStoragePurposes();
                }, e -> {
                    mySwipeRefreshLayout.setRefreshing(false);
                    Snackbar.make(coordinatorLayout, e.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Retry", view -> fetchData())
                            .show();
                    e.printStackTrace();
                });
    }

    private void fetchAllAppDataForStoragePurposes() {
        // TODO: Fetch all the sub-screens for each test and  cache it so the app can be used offline
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
                Timber.d("Key: " + key + " Value: " + value);
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
        Intent intent = new Intent(MainActivity.this, StoreActivity.class);
        startActivityForResult(intent, STORE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        Timber.d("onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == STORE_REQUEST) {
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
                    Timber.d("onActivityResult: sent invitation " + id);
                }
            } else {
                Toast.makeText(this, "Error sending invitations. Please check your credit or data", Toast.LENGTH_SHORT).show();
                // ...
            }

        }


    }

    private void openPascoUploadScreen() {
        Intent browserIntent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://www.pascoapp.com/give-us-pasco"));
        startActivity(browserIntent);
    }

    private void logout() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(task -> {
                    // user is now signed out
                    startActivity(new Intent(MainActivity.this, CheckCurrentUser.class));
                    finish();
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
