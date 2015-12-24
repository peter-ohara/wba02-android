package com.pascoapp.wba02_android.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.pascoapp.wba02_android.App;
import com.pascoapp.wba02_android.parseSubClasses.Course;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.registration.RegistrationActivity;
import com.pascoapp.wba02_android.settings.SettingsActivity;
import com.pascoapp.wba02_android.takeTest.TakeTestActivity;
import com.pascoapp.wba02_android.parseSubClasses.Test;

import java.util.ArrayList;
import java.util.List;

public class ChooseTestActivity extends AppCompatActivity {

    public static final String EXTRA_PROGRAMME_ID =
            "com.pascoapp.wba02_android.ChooseTestActivity.ChooseTestActivity.courseId";
    private static final int SELECT_COURSE_REQUEST = 43;

    private ProgressBar loadingIndicator;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<Test> mTests;
    private TestListAdapter mAdapter;
    private View coordinatorLayoutView;
    private Button selectCourseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        coordinatorLayoutView = findViewById(R.id.snackbarPosition);
        selectCourseButton = (Button) findViewById(R.id.select_department_button);
        selectCourseButton.setText(getCurrentCourseId());

        selectCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseTestActivity.this, ChooseCourseActivity.class);
                intent.putExtra(ChooseCourseActivity.EXTRA_PROGRAMME_ID, getProgrammeId());
                startActivityForResult(intent, SELECT_COURSE_REQUEST);
                overridePendingTransition(R.anim.pull_up_from_bottom, android.R.anim.fade_out);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);

        mRecyclerView = (RecyclerView) findViewById(R.id.tests_list);
        mRecyclerView.setHasFixedSize(true);

        // Use a linear layout manager
        mLayoutManager = new LinearLayoutManager(ChooseTestActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Create an object for the adapter
        mTests = new ArrayList<>();
        mAdapter = new TestListAdapter(mTests);
        mAdapter.setOnItemClickListener(new TestListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String testId) {
                Intent intent = new Intent(ChooseTestActivity.this, TakeTestActivity.class);
                intent.putExtra(TakeTestActivity.EXTRA_TEST_ID, testId);
                startActivity(intent);
            }
        });

        // Set the adapter object to the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        String courseId = getCurrentCourseId();
        refreshList(courseId);
    }

    public String getProgrammeId() {
        Intent intent = getIntent();
        return intent.getStringExtra(EXTRA_PROGRAMME_ID);
    }

    private String getCurrentCourseId() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(ChooseTestActivity.this);
        return settings.getString(App.CURRENT_COURSE_ID, null);
    }

    public void refreshList(final String courseId) {
        loadingIndicator.setVisibility(View.VISIBLE);

        ParseQuery<Test> query = Test.getQuery();

        //query.whereEqualTo()

        if (courseId != null) {
            query.whereEqualTo("course",
                    ParseObject.createWithoutData(Course.class, courseId));
        } else {
            selectCourseButton.setText("All");
        }

        query.findInBackground(new FindCallback<Test>() {
            @Override
            public void done(List<Test> tests, ParseException e) {
                loadingIndicator.setVisibility(View.GONE);
                if (e == null) {
                    mTests.clear();
                    mTests.addAll(tests);
                    mAdapter.notifyDataSetChanged();
                } else {
                    Snackbar.make(coordinatorLayoutView, e.getCode() + " : " + e.getMessage(),
                            Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    refreshList(courseId);
                                }
                            }).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_choose_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
//            case R.id.action_inbox:
//                // Check if users department has been set, if not show UserDepartmentActivity
//                ParseObject department = mCurrentUser.getParseObject("department");
//
//                if (department == null) {
//                    startActivity(new Intent(ChooseTestActivity.this, UserDepartmentActivity.class));
//                } else {
//                    startActivity(new Intent(ChooseTestActivity.this, InboxActivity.class));
//                }
//                break;
            case R.id.action_clear_cache:
                ParseQuery.clearAllCachedResults();
                break;
            case R.id.action_logout:
                logout();
                break;
            case R.id.action_settings:
                startActivity(new Intent(ChooseTestActivity.this, SettingsActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        ParseUser.logOut();
        showRegistrationPage();
    }

    private void showRegistrationPage() {
        Intent intent = new Intent(ChooseTestActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == SELECT_COURSE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                String courseId = data.getStringExtra(ChooseCourseActivity.SELECTED_COURSE_ID);

                fetchCourse(courseId);
            }
        }
    }

    private void fetchCourse(final String courseId) {
        loadingIndicator.setVisibility(View.VISIBLE);

        ParseQuery<Course> query = Course.getQuery();
        query.getInBackground(courseId, new GetCallback<Course>() {
            @Override
            public void done(Course course, ParseException e) {
                loadingIndicator.setVisibility(View.GONE);

                if (e == null) {
                    selectCourseButton.setText(course.getCode() + " " + course.getName());
                    refreshList(course.getObjectId());
                } else {
                    Snackbar.make(coordinatorLayoutView, e.getCode() + " : " + e.getMessage(),
                            Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    fetchCourse(courseId);
                                }
                            }).show();
                }
            }
        });
    }
}
