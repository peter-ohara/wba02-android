package com.pascoapp.wba02_android.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.pascoapp.wba02_android.parseSubClasses.Programme;
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
    private ArrayList<Course> mCourses;
    private SelectCourseSpinnerAdapter mSpinnerAdapter;
    private Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.mipmap.ic_action_logo);

        coordinatorLayoutView = findViewById(R.id.snackbarPosition);

        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);
        emptyView = (View) findViewById(R.id.empty_view);

        setUpCourseToolbar();

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

        View spinnerContainer = LayoutInflater.from(MainActivity.this).inflate(R.layout.toolbar_spinner,
                selectCourseToolbar, false);
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        selectCourseToolbar.addView(spinnerContainer, lp);


        mSpinner = (Spinner) spinnerContainer.findViewById(R.id.toolbar_spinner);
        mSpinnerAdapter = new SelectCourseSpinnerAdapter();

        mSpinner.setAdapter(mSpinnerAdapter); // set the adapter to provide layout of rows and content
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Course course = (Course) mSpinnerAdapter.getItem(position);

                if (course.getObjectId().equalsIgnoreCase("all")) {
                    getTestsFromAllCourses();
                } else {
                    getTestsFromCourse(course.getObjectId(), course.getCode(), course.getName());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fetchCourses();
    }

    public void fetchCourses() {
        ParseQuery<Course> query = Course.getQuery();

        // TODO:
        // Filter query by courses that student has selected for current duration duration

        query.selectKeys(Arrays.asList("code", "name"));
        query.orderByAscending("code");

        query.findInBackground(new FindCallback<Course>() {
            @Override
            public void done(List<Course> courses, ParseException e) {
                if (e == null) {
                    mSpinnerAdapter.clear();
                    mSpinnerAdapter.addCourses((ArrayList<Course>) courses);

                    // Append "All" to the beginning of the list
                    Course allCourse = ParseObject.createWithoutData(Course.class, "all");
                    allCourse.setObjectId("all");
                    allCourse.setCode("ALL");
                    allCourse.setName("");
                    mSpinnerAdapter.addCourse(0, allCourse);
                    mSpinnerAdapter.notifyDataSetChanged();

                    mSpinner.setSelection(25);
                } else if (e.getCode() == 120) {
                    // Result not cached Error. Ignore it
                } else {
                    System.out.println("Courses" + e.getCode() + " : " + e.getMessage());
                    // TODO: Change to Snackbar
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
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
//            case R.id.action_clear_cache:
//                ParseQuery.clearAllCachedResults();
//                refreshTests();
//                break;
//            case R.id.action_logout:
//                logout();
//                break;
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
