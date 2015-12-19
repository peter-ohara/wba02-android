package com.pascoapp.wba02_android.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.pascoapp.wba02_android.App;
import com.pascoapp.wba02_android.parseSubClasses.Course;
import com.pascoapp.wba02_android.parseSubClasses.Programme;
import com.pascoapp.wba02_android.R;

import java.util.ArrayList;
import java.util.List;

public class ChooseCourseActivity extends AppCompatActivity {

    public static final String EXTRA_PROGRAMME_ID =
            "com.pascoapp.wba02_android.ChooseCourseActivity.ChooseCourseActivity.programmeId";

    public static final String SELECTED_COURSE_ID =
            "com.pascoapp.wba02_android.ChooseCourseActivity.ChooseCourseActivity.department_id";

    private ProgressBar loadingIndicator;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<Course> mCourses;
    private CourseListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);

        mRecyclerView = (RecyclerView) findViewById(R.id.courses_list);
        mRecyclerView.setHasFixedSize(true);

        // Use a linear layout manager
        mLayoutManager = new LinearLayoutManager(ChooseCourseActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Create an object for the adapter
        mCourses = new ArrayList<>();
        mAdapter = new CourseListAdapter(mCourses);
        mAdapter.setOnItemClickListener(new CourseListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String courseId) {
                persistSelectedDepartment(courseId);
                setResultAndCloseParentActivity(courseId);
            }
        });

        // Set the adapter object to the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        String programmeId = getProgrammeId();
        refreshList(programmeId);
    }

    public String getProgrammeId() {
        Intent intent = getIntent();
        return intent.getStringExtra(EXTRA_PROGRAMME_ID);
    }

    public void refreshList(String programmeId) {
        loadingIndicator.setVisibility(View.VISIBLE);

        ParseQuery<Course> query = Course.getQuery();

        query.whereEqualTo("programme",
                ParseObject.createWithoutData(Programme.class, programmeId));

        query.findInBackground(new FindCallback<Course>() {
            @Override
            public void done(List<Course> courses, ParseException e) {
                loadingIndicator.setVisibility(View.GONE);
                if (e == null) {
                    mCourses.clear();
                    mCourses.addAll(courses);
                    mAdapter.notifyDataSetChanged();
                } else {
                    System.out.println("Courses" + e.getCode() + " : " + e.getMessage());
                    Toast.makeText(ChooseCourseActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void persistSelectedDepartment(String courseId) {
        // TODO: Fix this.
        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(App.CURRENT_COURSE_ID,
                (String) courseId);
    }

    private void setResultAndCloseParentActivity(String courseId) {
        Intent result = new Intent();
        result.putExtra(SELECTED_COURSE_ID, courseId);
        setResult(RESULT_OK, result);

        finish();
        overridePendingTransition(android.R.anim.fade_in, R.anim.pull_out_to_bottom);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, R.anim.pull_out_to_bottom);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case android.R.id.home:
                super.onOptionsItemSelected(item);
                this.finish();
                overridePendingTransition(android.R.anim.fade_in, R.anim.pull_out_to_bottom);
                break;
            default:
                break;
        }

        return true;
    }

}
