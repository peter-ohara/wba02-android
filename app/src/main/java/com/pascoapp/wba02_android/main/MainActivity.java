package com.pascoapp.wba02_android.main;

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

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.pascoapp.wba02_android.Injection;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.webview.WebviewActivity;
import com.pascoapp.wba02_android.data.course.Course;
import com.pascoapp.wba02_android.data.test.Test;
import com.pascoapp.wba02_android.main.adapter.MainScreenItem;
import com.pascoapp.wba02_android.main.adapter.MainViewListAdapter;
import com.pascoapp.wba02_android.signIn.SignInActivity;
import com.pascoapp.wba02_android.store.StoreActivity;
import com.pascoapp.wba02_android.testDetail.TestDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private static final int REQUEST_INVITE = 100 ;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.snackbarPosition) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.coursesList) RecyclerView coursesRecyclerView;
    @BindView(R.id.bottomBar) View bottomBar;

    public static final int STORE_REQUEST = 1;

    MainContract.UserActionsListener mUserActionsListener;

    private MainViewListAdapter mainViewListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserActionsListener = new MainPresenter(Injection.provideCourseRepository(this), this);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        handleAnyNotificationData();
        setupToolbar();
        setupRecyclerView();

        mSwipeRefreshLayout.setOnRefreshListener(() -> mUserActionsListener.loadTests(true));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUserActionsListener.loadTests(false);
    }

    private void setupRecyclerView() {
        coursesRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(linearLayoutManager);

        mainViewListAdapter = new MainViewListAdapter(new ArrayList<>(), mTestItemListener);
        coursesRecyclerView.setAdapter(mainViewListAdapter);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_action_logo);
        setTitle("");
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
                mUserActionsListener.openStore();
                return true;
            case R.id.action_invite:
                mUserActionsListener.openInvite();
                return true;
            case R.id.action_refresh:
                mUserActionsListener.loadTests(true);
                return true;
            case R.id.action_upload_pasco:
                mUserActionsListener.openPascoUpload();
                return true;
            case R.id.action_logout:
                mUserActionsListener.logout(this);
                return true;
            case R.id.action_feedback:
                mUserActionsListener.openFeedbackForm();
                return true;
            case R.id.action_help:
                mUserActionsListener.openHelp();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.bottomBar)
    public void openStore() {
        mUserActionsListener.openStore();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Timber.d("onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == STORE_REQUEST) {
            processStoreResult(resultCode);
        }

        if (requestCode == REQUEST_INVITE) {
            processInviteResult(resultCode, data);
        }
    }

    private void processStoreResult(int resultCode) {
        if (resultCode == RESULT_OK) {
            // User may have bought new courses refresh course list
            mUserActionsListener.loadTests(true);
        }
    }

    private void processInviteResult(int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Toast.makeText(this, "Your invitations have been sent", Toast.LENGTH_SHORT).show();
            String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
            for (String id : ids) {
                Timber.d("onActivityResult: sent invitation " + id);
            }
        } else {
            Toast.makeText(this, "Error sending invitations. Please check your credit or data", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * Listener for clicks on tests in the RecyclerView.
     */
    TestItemListener mTestItemListener = new TestItemListener() {
        @Override
        public void onTestClick(Test clickedTest) {
            mUserActionsListener.openTestDetails(clickedTest);
        }

        @Override
        public void onMoreButtonClicked(Course clickedCourse) {
            mUserActionsListener.openCourseDetails(clickedCourse);
        }
    };

    @Override
    public void setProgressIndicator(boolean active) {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(active));
    }

    @Override
    public void showMainItems(List<MainScreenItem> mainScreenItems) {
        mainViewListAdapter.replaceData(mainScreenItems);
    }

    @Override
    public void showStore() {
        Intent intent = new Intent(MainActivity.this, StoreActivity.class);
        startActivityForResult(intent, STORE_REQUEST);
    }

    @Override
    public void showInvite() {
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                //.setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
                .setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))
                .setCallToActionText(getString(R.string.invitation_cta))
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }

    @Override
    public void showPascoUpload() {
        Intent browserIntent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://www.pascoapp.com/give-us-pasco"));
        startActivity(browserIntent);
    }

    @Override
    public void showFeedbackForm() {
        Intent browserIntent = new Intent(MainActivity.this, WebviewActivity.class);
        String url = "https://docs.google.com/forms/d/e/" +
                "1FAIpQLSckq4J5Jf32rK_LSKI4Gq1k0gWthyEj82B_UlySMJxr9ib22A/viewform";
        browserIntent.putExtra(WebviewActivity.EXTRA_URL, url);
        browserIntent.putExtra(WebviewActivity.EXTRA_TITLE, getString(R.string.action_feedback));
        startActivity(browserIntent);
    }

    @Override
    public void showHelp() {
        Intent browserIntent = new Intent(MainActivity.this, WebviewActivity.class);
        String url = "http://www.pascoapp.com/help";
        browserIntent.putExtra(WebviewActivity.EXTRA_URL, url);
        browserIntent.putExtra(WebviewActivity.EXTRA_TITLE, getString(R.string.action_help));
        startActivity(browserIntent);
    }

    @Override
    public void showSignIn() {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showTestDetailUi(Test test) {
        Intent intent = new Intent(this, TestDetailActivity.class);
        intent.putExtra(TestDetailActivity.EXTRA_TEST_ID, test);
        startActivity(intent);
    }

    @Override
    public void showCourseDetailUi(Course course) {
        Toast.makeText(this, "More quizzes coming soon!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG)
                .setAction("Retry", view -> mUserActionsListener.loadTests(true))
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
                Timber.d("Key: " + key + " Value: " + value);
            }
        }
        // [END handle_data_extras]
    }

    public interface TestItemListener {

        void onTestClick(Test clickedTest);
        void onMoreButtonClicked(Course clickedCourse);
    }
}
