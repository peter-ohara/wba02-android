package com.pascoapp.wba02_android.store;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.pascoapp.wba02_android.APIUtils;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.data.course.Course;
import com.pascoapp.wba02_android.store.adapter.CourseListAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class StoreActivity extends AppCompatActivity {

    @BindView(R.id.courseList) RecyclerView courseList;
    @BindView(R.id.loading_indicator) AVLoadingIndicatorView loadingIndicator;
    @BindView(R.id.snackbarPosition) CoordinatorLayout coordinatorLayout;

    private CourseListAdapter courseListAdapter;
    private List<Course> courses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courseList.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        courseList.setLayoutManager(gridLayoutManager);

        courseListAdapter = new CourseListAdapter(this, courses);
        courseList.setAdapter(courseListAdapter);

        fetchData();
    }

    private void fetchData() {
        loadingIndicator.show();

        APIUtils.getPascoService(this, StoreScreenService.class).getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fetchedCourses -> {
                    loadingIndicator.hide();

                    courses.clear();
                    courses.addAll(fetchedCourses);
                    Timber.d(fetchedCourses.toString());
                    Timber.d(courses.toString());
                    courseListAdapter.notifyDataSetChanged();
                }, e -> {
                    loadingIndicator.hide();
                    Snackbar.make(coordinatorLayout, e.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Retry", view -> fetchData())
                            .show();
                    e.printStackTrace();
                });
    }

}
