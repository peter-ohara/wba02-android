package com.pascoapp.wba02_android.views.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.StoreActivity;
import com.pascoapp.wba02_android.services.courses.Courses;
import com.pascoapp.wba02_android.services.tests.Tests;
import com.pascoapp.wba02_android.services.users.Users;
import com.pascoapp.wba02_android.views.signIn.CheckCurrentUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.snackbarPosition)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mySwipeRefreshLayout;
    @BindView(R.id.coursesList)
    RecyclerView coursesRecyclerView;
    @BindView(R.id.bottomBar)
    View bottomBar;

    public static final int BUY_COURSES_REQUEST = 1;

    private MainViewListAdapter mainViewListAdapter;
    private List<MainViewListItem> mItems = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_action_logo);
        setTitle("");

        coursesRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(linearLayoutManager);

        mainViewListAdapter = new MainViewListAdapter(this, mItems);
        coursesRecyclerView.setAdapter(mainViewListAdapter);

        mySwipeRefreshLayout.setOnRefreshListener(() -> {
            refreshData();
        });

        refreshData();
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
            case R.id.action_refresh:
                refreshData();
                return true;
            case R.id.action_logout:
                logout();
                return true;
            case R.id.action_feedback:
                openMailClientForFeedback();
                return true;
            case R.id.action_help:
                openHelp();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.bottomBar)
    public void openStore() {
        Intent intent = new Intent(MainActivity.this, StoreActivity.class);
        startActivityForResult(intent, BUY_COURSES_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BUY_COURSES_REQUEST) {
            if (resultCode == RESULT_OK) {
                // User may have bought new courses refresh course list
                refreshData();
            }
        }
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

    private void openMailClientForFeedback() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "feedback@pascoapp.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback on Pasco Android App");
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    private void openHelp() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.pascoapp.com/help"));
        startActivity(browserIntent);
    }


    private void refreshData() {
        mySwipeRefreshLayout.setRefreshing(true);
        mItems.clear();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Users.fetchUser(user.getUid())
                .concatMap(fetchedUser -> {
                    if (fetchedUser.getCourseKeys() == null) {
                        // End Stream
                        openStore();
                    }

                    Object[] courseKeys = fetchedUser.getCourseKeys().keySet().toArray();

                    return Observable.from(courseKeys)
                            .concatMap(courseKey -> Courses.fetchCourse((String) courseKey))
                            .toList();
                })
                .concatMap(courses -> Observable.from(courses))
                .concatMap(course -> {
                    MainViewListItem mainViewListItem = new MainViewListItem(course);
                    mItems.add(mainViewListItem);

                    Query testsQuery = Tests.TESTS_REF.orderByChild("courseKey").equalTo(course.getKey());
                    return Tests.fetchListOfTests(testsQuery);
                })
                .concatMap(tests -> Observable.from(tests))
                .subscribe(test -> {
                    mySwipeRefreshLayout.setRefreshing(false);

                    MainViewListItem mainViewListItem = new MainViewListItem(test);
                    mItems.add(mainViewListItem);
                    mainViewListAdapter.notifyDataSetChanged();

                }, firebaseException -> {
                    mySwipeRefreshLayout.setRefreshing(false);
                    Snackbar.make(coordinatorLayout, firebaseException.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Retry", view -> refreshData())
                            .show();
                });
    }

}
