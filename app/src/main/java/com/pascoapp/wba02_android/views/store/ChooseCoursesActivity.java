package com.pascoapp.wba02_android.views.store;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.services.APIUtils;
import com.pascoapp.wba02_android.services.courses.Course;
import com.pascoapp.wba02_android.views.WebviewActivity;
import com.pascoapp.wba02_android.views.signIn.CheckCurrentUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChooseCoursesActivity extends AppCompatActivity {

    public static final String TAG = ChooseCoursesActivity.class.getSimpleName();
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

    private CourseListAdapter courseListAdapter;
    private List<Course> courses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_courses);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        coursesRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(linearLayoutManager);

        courseListAdapter = new CourseListAdapter(this, courses);
        coursesRecyclerView.setAdapter(courseListAdapter);

        mySwipeRefreshLayout.setOnRefreshListener(() -> {
            refreshData();
        });

        refreshData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_store, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                refreshData();
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

    @OnClick(R.id.bottomBar)
    public void returnToMainScreen() {
        // Return to MainActivity
        setResult(RESULT_OK);
        finish();
    }

    private void logout() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        startActivity(new Intent(ChooseCoursesActivity.this, CheckCurrentUser.class));
                        finish();
                    }
                });
    }

    private void openFeedbackForm() {
        Intent browserIntent = new Intent(ChooseCoursesActivity.this, WebviewActivity.class);
        String url = "https://docs.google.com/forms/d/e/" +
                "1FAIpQLSckq4J5Jf32rK_LSKI4Gq1k0gWthyEj82B_UlySMJxr9ib22A/viewform";
        browserIntent.putExtra(WebviewActivity.EXTRA_URL, url);
        browserIntent.putExtra(WebviewActivity.EXTRA_TITLE, getString(R.string.action_feedback));
        startActivity(browserIntent);
    }

    private void openHelp() {
        Intent browserIntent = new Intent(ChooseCoursesActivity.this, WebviewActivity.class);
        String url = "http://www.pascoapp.com/help";
        browserIntent.putExtra(WebviewActivity.EXTRA_URL, url);
        browserIntent.putExtra(WebviewActivity.EXTRA_TITLE, getString(R.string.action_help));
        startActivity(browserIntent);
    }


    private void refreshData() {
        mySwipeRefreshLayout.setRefreshing(true);

        APIUtils.getCourseService().getCourses()
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(fetchedCourses -> {
                    mySwipeRefreshLayout.setRefreshing(false);

                    courses.clear();
                    courses.addAll(fetchedCourses);
                    courseListAdapter.notifyDataSetChanged();
                }, throwable -> {
                    mySwipeRefreshLayout.setRefreshing(false);
                    Log.d(TAG, "fetchData: " + throwable.getMessage());
                    Snackbar.make(coordinatorLayout, throwable.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Retry", view -> refreshData())
                            .show();
                });
    }

}
