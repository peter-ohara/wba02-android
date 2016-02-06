package com.pascoapp.wba02_android.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.pascoapp.wba02_android.App;
import com.pascoapp.wba02_android.HelpActivity;
import com.pascoapp.wba02_android.Inbox.MessageListActivity;
import com.pascoapp.wba02_android.parseSubClasses.Course;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.Student;
import com.pascoapp.wba02_android.registration.RegistrationActivity;
import com.pascoapp.wba02_android.settings.SettingsActivity;
import com.pascoapp.wba02_android.takeTest.TakeTestActivity;
import com.pascoapp.wba02_android.parseSubClasses.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int SELECT_COURSE_REQUEST = 43;

    private ProgressBar loadingIndicator;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private ArrayList<Test> mTests;
    private TestListAdapter mAdapter;
    private View coordinatorLayoutView;
    private Toolbar selectCourseToolbar;
    private View emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_action_logo);

        coordinatorLayoutView = findViewById(R.id.snackbarPosition);

        setUpCourseToolbar();

        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);
        emptyView = (View) findViewById(R.id.empty_view);
        mRecyclerView = (RecyclerView) findViewById(R.id.tests_list);
        mRecyclerView.setHasFixedSize(true);

        // Use a linear layout manager
        mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Create an object for the adapter
        mTests = new ArrayList<>();
        mAdapter = new TestListAdapter(mTests);
        mAdapter.setOnItemClickListener(new TestListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, String testId, String testTitle) {
                Intent intent = new Intent(MainActivity.this, TakeTestActivity.class);
                intent.putExtra(TakeTestActivity.EXTRA_TEST_ID, testId);
                intent.putExtra(TakeTestActivity.EXTRA_TEST_TITLE, testTitle);
                startActivity(intent);
            }
        });

        // Set the adapter object to the RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        refreshTests();
    }

    private void setUpCourseToolbar() {
        selectCourseToolbar = (Toolbar) findViewById(R.id.select_department_button);
        selectCourseToolbar.setTitle(getCurrentCourseId());
        selectCourseToolbar.inflateMenu(R.menu.menu_course_toolbar);
        selectCourseToolbar.hideOverflowMenu();

        selectCourseToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.action_switch_course:
                        showChooseCourseActivity();
                        return true;
                }
                return false;
            }
        });
    }

    private void refreshTests() {
        String courseId = getCurrentCourseId();

        if (courseId == null) {
            showChooseCourseActivity();
        } else if (courseId.equalsIgnoreCase("all")) {
            getTestsFromAllCourses();
        } else {
            getTestsFromCourse(getCurrentCourseId(), getCurrentCourseCode(), getCurrentCourseName());
        }
    }

    private String getCurrentCourseId() {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_current_course), Context.MODE_PRIVATE);

        return sharedPref.getString(App.CURRENT_COURSE_ID, null);
    }

    private String getCurrentCourseCode() {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_current_course), Context.MODE_PRIVATE);

        return sharedPref.getString(App.CURRENT_COURSE_CODE, null);
    }

    private String getCurrentCourseName() {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_current_course), Context.MODE_PRIVATE);

        return sharedPref.getString(App.CURRENT_COURSE_NAME, null);
    }

    @NonNull
    private ParseQuery<Course> getCourseQuery() {
        Student student = (Student) ParseUser.getCurrentUser();

        ParseQuery<Course> query = Course.getQuery();

        // TODO:
        // Filter query by courses that student has selected for current duration duration

        return query;
    }

    private void showChooseCourseActivity() {
        Intent intent = new Intent(MainActivity.this, ChooseCourseActivity.class);
        startActivityForResult(intent, SELECT_COURSE_REQUEST);
        overridePendingTransition(R.anim.slide_in_down, android.R.anim.fade_out);
    }

    public void refreshTestList(final Course course) {
        assert course != null;

        loadingIndicator.setVisibility(View.VISIBLE);

        ParseQuery<Test> query = Test.getQuery();
        query.whereEqualTo("course", course);
        query.selectKeys(Arrays.asList("lecturer", "year", "type", "duration"));

        query.findInBackground(new FindCallback<Test>() {
            @Override
            public void done(List<Test> tests, ParseException e) {
                if (e == null) {
                    if (tests.size() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mTests.clear();
                        mTests.addAll(tests);
                        mAdapter.notifyDataSetChanged();
                    }
                    loadingIndicator.setVisibility(View.GONE);
                } else if (e.getCode() == 120) {
                    // Result not cached Error. Ignore it
                } else {
                    Snackbar.make(coordinatorLayoutView, e.getCode() + " : " + e.getMessage(),
                            Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    refreshTestList(course);
                                }
                            }).show();
                    loadingIndicator.setVisibility(View.GONE);
                }
            }
        });
    }

    public void refreshTestList(final ParseQuery<Course> courseQuery) {
        assert courseQuery != null;

        loadingIndicator.setVisibility(View.VISIBLE);

        ParseQuery<Test> query = Test.getQuery();
        query.whereMatchesQuery("course", courseQuery);
        query.selectKeys(Arrays.asList("lecturer", "year", "type", "duration"));

        query.findInBackground(new FindCallback<Test>() {
            @Override
            public void done(List<Test> tests, ParseException e) {
                if (e == null) {
                    if (tests.size() == 0) {
                        emptyView.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    } else {
                        emptyView.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mTests.clear();
                        mTests.addAll(tests);
                        mAdapter.notifyDataSetChanged();
                    }
                    loadingIndicator.setVisibility(View.GONE);
                } else if (e.getCode() == 120) {
                    // Result not cached Error. Ignore it
                } else {
                    Snackbar.make(coordinatorLayoutView, e.getCode() + " : " + e.getMessage(),
                            Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    refreshTestList(courseQuery);
                                }
                            }).show();
                    loadingIndicator.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_inbox:
                startActivity(new Intent(MainActivity.this, MessageListActivity.class));
                break;
            case R.id.action_clear_cache:
                ParseQuery.clearAllCachedResults();
                refreshTests();
                break;
            case R.id.action_logout:
                logout();
                break;
            case R.id.action_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;
            case R.id.action_feedback:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto","pascoapp.wb@gmail.com", null));
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
        ParseUser.logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
                showRegistrationPage();
            }
        });
    }

    private void showRegistrationPage() {
        Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == SELECT_COURSE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                String courseId = data.getStringExtra(ChooseCourseActivity.SELECTED_COURSE_ID);
                String courseCode = data.getStringExtra(ChooseCourseActivity.SELECTED_COURSE_CODE);
                String courseName = data.getStringExtra(ChooseCourseActivity.SELECTED_COURSE_NAME);

                if (courseId.equalsIgnoreCase("all")) {
                    getTestsFromAllCourses();
                } else {
                    getTestsFromCourse(courseId, courseCode, courseName);
                }
            }
        }
    }

    private void getTestsFromCourse(String courseId, String courseCode, String courseName) {
        selectCourseToolbar.setTitle(courseCode + " " + courseName);
        Course course = ParseObject.createWithoutData(Course.class, courseId);
        refreshTestList(course);
    }

    private void getTestsFromAllCourses() {
        selectCourseToolbar.setTitle("ALL");
        ParseQuery<Course> courseQuery = getCourseQuery();
        refreshTestList(courseQuery);
    }
}
