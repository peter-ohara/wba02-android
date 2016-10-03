package com.pascoapp.wba02_android;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.Query;
import com.pascoapp.wba02_android.services.courses.Course;
import com.pascoapp.wba02_android.services.courses.Courses;
import com.pascoapp.wba02_android.services.users.Users;
import com.pascoapp.wba02_android.views.CourseListAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StoreActivity extends AppCompatActivity {

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

    private CourseListAdapter courseListAdapter;
    private List<Course> courses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        coursesRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(linearLayoutManager);

        courseListAdapter = new CourseListAdapter(this, courses);
        coursesRecyclerView.setAdapter(courseListAdapter);

        refreshData();
    }

    @OnClick(R.id.bottomBar)
    public void returnToMainScreen() {
        // Return to MainActivity
        setResult(RESULT_OK);
        finish();
    }

    private void refreshData() {
        loadingIndicator.setVisibility(View.VISIBLE);

        Query coursesQuery = Courses.COURSES_REF;
        Courses.fetchListOfCourses(coursesQuery)
                .subscribe(fetchedCourses -> {
                    loadingIndicator.setVisibility(View.GONE);

                    courses.clear();
                    courses.addAll(fetchedCourses);
                    courseListAdapter.notifyDataSetChanged();
                }, firebaseException -> {
                    loadingIndicator.setVisibility(View.GONE);
                    Snackbar.make(coordinatorLayout, firebaseException.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Retry", view -> refreshData())
                            .show();
                });
    }

}
