package com.pascoapp.wba02_android.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pascoapp.wba02_android.App;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.Course;

public class ChooseCourseActivity extends AppCompatActivity {

    public static final String SELECTED_COURSE_ID =
            "com.pascoapp.wba02_android.ChooseCourseActivity.ChooseCourseActivity.courseId";

    public static final String SELECTED_COURSE_CODE =
            "com.pascoapp.wba02_android.ChooseCourseActivity.ChooseCourseActivity.courseCode";

    public static final String SELECTED_COURSE_NAME =
            "com.pascoapp.wba02_android.ChooseCourseActivity.ChooseCourseActivity.courseName";

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private DatabaseReference mRef;
    private FirebaseRecyclerAdapter<Course, CourseHolder> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_course);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        mRef = FirebaseDatabase.getInstance().getReference().child("courses");
        // TODO: Filter query by courses/programme that student has selected for current duration duration


        mRecyclerView = (RecyclerView) findViewById(R.id.courses_list);
        mRecyclerView.setHasFixedSize(true);

        // Use a linear layout manager
        mLayoutManager = new LinearLayoutManager(ChooseCourseActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Create an object for the adapter
        mAdapter = new FirebaseRecyclerAdapter<Course, CourseHolder>(Course.class, R.layout.course_list_item_template, CourseHolder.class, mRef) {
            @Override
            public void populateViewHolder(CourseHolder courseViewHolder, final Course course, int position) {
                final String courseKey = getRef(position).getKey();
                courseViewHolder.setTitle(course.getName());
                courseViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        persistSelectedDepartment(courseKey, course.getCode(), course.getName());
                        setResultAndCloseActivity(courseKey, course.getCode(), course.getName());
                    }
                });
            }
        };

        // Set the adapter object to the RecyclerView
        mRecyclerView.setAdapter(mAdapter);
    }


    public void persistSelectedDepartment(String courseKey, String courseCode, String courseName) {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_current_course), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(App.CURRENT_COURSE_ID, courseKey);
        editor.putString(App.CURRENT_COURSE_CODE, courseCode);
        editor.putString(App.CURRENT_COURSE_NAME, courseName);
        editor.commit();
    }

    private void setResultAndCloseActivity(String courseKey, String courseCode, String courseName) {
        Intent result = new Intent();
        result.putExtra(SELECTED_COURSE_ID, courseKey);
        result.putExtra(SELECTED_COURSE_CODE, courseCode);
        result.putExtra(SELECTED_COURSE_NAME, courseName);
        setResult(RESULT_OK, result);

        finish();
        overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_up);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_up);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case android.R.id.home:
                super.onOptionsItemSelected(item);
                this.finish();
                overridePendingTransition(android.R.anim.fade_in, R.anim.slide_out_up);
                break;
            default:
                break;
        }

        return true;
    }

    public static class CourseHolder extends RecyclerView.ViewHolder{

        public View mView;

        public CourseHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String name) {
            TextView field = (TextView) mView.findViewById(R.id.course_item_title);
            field.setText(name);
        }
    }

}
