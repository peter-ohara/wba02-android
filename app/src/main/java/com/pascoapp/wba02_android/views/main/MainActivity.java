package com.pascoapp.wba02_android.views.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.Query;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.services.courses.Courses;
import com.pascoapp.wba02_android.services.tests.Tests;
import com.pascoapp.wba02_android.views.overflow.Inbox.MessageListActivity;
import com.pascoapp.wba02_android.views.overflow.help.HelpActivity;
import com.pascoapp.wba02_android.views.overflow.settings.SettingsActivity;
import com.pascoapp.wba02_android.views.signIn.CheckCurrentUser;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.snackbarPosition)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.loading_indicator)
    ProgressBar loadingIndicator;
    @BindView(R.id.coursesList)
    RecyclerView coursesRecyclerView;
    @BindView(R.id.bottomBar)
    View bottomBar;

    private MainViewListAdapter mainViewListAdapter;


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

        mainViewListAdapter = new MainViewListAdapter(this, new ArrayList<>());
        coursesRecyclerView.setAdapter(mainViewListAdapter);

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
            case R.id.action_logout:
                logout();
                break;
            case R.id.action_feedback:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", "pascoapp.wb@gmail.com", null));
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

    private void refreshData() {
        loadingIndicator.setVisibility(View.VISIBLE);

        Query coursesQuery = Courses.COURSES_REF.limitToFirst(3);
        Courses.fetchListOfCourses(coursesQuery)
                .concatMap(courses -> Observable.from(courses))
                .concatMap(course -> {
                    MainViewListItem mainViewListItem = new MainViewListItem(course);
                    mainViewListAdapter.add(mainViewListItem);

                    Query testsQuery = Tests.TESTS_REF.orderByChild("courseKey").equalTo(course.getKey());
                    return Tests.fetchListOfTests(testsQuery);
                })
                .concatMap(tests -> Observable.from(tests))
                .subscribe(test -> {
                    loadingIndicator.setVisibility(View.GONE);
                    MainViewListItem mainViewListItem = new MainViewListItem(test);
                    mainViewListAdapter.add(mainViewListItem);
                }, firebaseException -> {
                    loadingIndicator.setVisibility(View.GONE);
                    Snackbar.make(coordinatorLayout, firebaseException.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Retry", view -> refreshData())
                            .show();
                });
    }

}
