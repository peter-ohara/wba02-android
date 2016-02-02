package com.pascoapp.wba02_android.main;

import android.content.Context;
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
import com.parse.ParseUser;
import com.pascoapp.wba02_android.App;
import com.pascoapp.wba02_android.parseSubClasses.Course;
import com.pascoapp.wba02_android.parseSubClasses.Programme;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.Student;

import java.util.ArrayList;
import java.util.List;

public class ChooseCourseActivity extends AppCompatActivity {

    public static final String SELECTED_COURSE_ID =
            "com.pascoapp.wba02_android.ChooseCourseActivity.ChooseCourseActivity.courseId";

    public static final String SELECTED_COURSE_CODE =
            "com.pascoapp.wba02_android.ChooseCourseActivity.ChooseCourseActivity.courseCode";

    public static final String SELECTED_COURSE_NAME =
            "com.pascoapp.wba02_android.ChooseCourseActivity.ChooseCourseActivity.courseName";

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
            public void onItemClick(View view, Course course) {
                persistSelectedDepartment(course);
                setResultAndCloseActivity(course);
            }
        });

        // Set the adapter object to the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        Student student = (Student) ParseUser.getCurrentUser();
        fetchCourses(student.getProgramme(), student.getLevel(), student.getSemester());
    }

    public void fetchCourses(Programme programme, Integer level, Integer semester) {
        loadingIndicator.setVisibility(View.VISIBLE);

        ParseQuery<Course> query = Course.getQuery();

        if (programme != null) {
            query.whereEqualTo("programme", programme);
        }

        if (level != null) {
            query.whereEqualTo("level", level);
        }

        if (semester != null) {
            query.whereEqualTo("semester", semester);
        }


        query.findInBackground(new FindCallback<Course>() {
            @Override
            public void done(List<Course> courses, ParseException e) {
                loadingIndicator.setVisibility(View.GONE);
                if (e == null) {
                    mCourses.clear();
                    mCourses.addAll(courses);
                    Course allCourse = ParseObject.createWithoutData(Course.class, "all");

                    // Append "All" to the beginning of the list
                    allCourse.setObjectId("all");
                    allCourse.setCode("ALL");
                    allCourse.setName("");
                    mCourses.add(0, allCourse);

                    mAdapter.notifyDataSetChanged();
                } else if (e.getCode() == 102) {
                    // Ignore this error
                } else {
                    System.out.println("Courses" + e.getCode() + " : " + e.getMessage());
                    // TODO: Change to Snackbar
                    Toast.makeText(ChooseCourseActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void persistSelectedDepartment(Course course) {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_current_course), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(App.CURRENT_COURSE_ID, course.getObjectId());
        editor.putString(App.CURRENT_COURSE_CODE, course.getCode());
        editor.putString(App.CURRENT_COURSE_NAME, course.getName());
        editor.commit();
    }

    private void setResultAndCloseActivity(Course course) {
        Intent result = new Intent();
        result.putExtra(SELECTED_COURSE_ID, course.getObjectId());
        result.putExtra(SELECTED_COURSE_CODE, course.getCode());
        result.putExtra(SELECTED_COURSE_NAME, course.getName());
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
        switch(id) {
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
