package com.pascoapp.wba02_android.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.CheckCurrentUser;
import com.pascoapp.wba02_android.HelpActivity;
import com.pascoapp.wba02_android.Inbox.MessageListActivity;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.firebasePojos.Course;
import com.pascoapp.wba02_android.firebasePojos.Lecturer;
import com.pascoapp.wba02_android.firebasePojos.Test;
import com.pascoapp.wba02_android.settings.SettingsActivity;
import com.pascoapp.wba02_android.takeTest.TakeTestActivity;

public class MainActivity extends AppCompatActivity {

    public static final String CURRENT_COURSE_KEY = "com.pascoapp.wba02_android.MainActivity.courseId";
    public static final String CURRENT_COURSE_CODE = "com.pascoapp.wba02_android.MainActivity.courseCode";
    public static final String CURRENT_COURSE_NAME = "com.pascoapp.wba02_android.MainActivity.courseName";
    private static final String TAG = MainActivity.class.getSimpleName();

    private ProgressBar loadingIndicator;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private FirebaseRecyclerAdapter<Test, TestHolder> mAdapter;
    private View coordinatorLayoutView;
    private Toolbar selectCourseToolbar;
    private View emptyView;
    private SelectCourseSpinnerAdapter mSpinnerAdapter;
    private Spinner mSpinner;

    private DatabaseReference mCoursesRef;
    private DatabaseReference mTestsRef;

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

        createAdapter();

        // Set the adapter object to the RecyclerView
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }

    private void createAdapter() {
        mTestsRef = FirebaseDatabase.getInstance().getReference().child("tests");
        // TODO: Filter query by tests/programme that student has selected for current duration duration

        loadingIndicator.setVisibility(View.VISIBLE);
        mAdapter = new FirebaseRecyclerAdapter<Test, TestHolder>(Test.class, R.layout.test_list_item_template, TestHolder.class, mTestsRef) {
            @Override
            public void populateViewHolder(final TestHolder testViewHolder, final Test test, final int position) {

                DatabaseReference mLecturerRef = FirebaseDatabase.getInstance().getReference()
                        .child("lecturers").child(test.getLecturer());

                mLecturerRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Lecturer lecturer = dataSnapshot.getValue(Lecturer.class);

                        String lecturerName = lecturer.getFirstName() + " " + lecturer.getLastName();
                        final String testKey = getRef(position).getKey();


                        Long year = test.getYear();

                        String type;
                        if (test.getType().equalsIgnoreCase("endOfSem")) {
                            type = "End of Semester Exam";
                        } else if (test.getType().equalsIgnoreCase("midSem")) {
                            type = "Mid-Semester Exam";
                        } else if (test.getType().equalsIgnoreCase("classTest")) {
                            type = "Class Test";
                        } else if (test.getType().equalsIgnoreCase("assignment")) {
                            type = "Assignment";
                        } else {
                            type = "Unknown";
                        }
                        final String testTitle = year + " " + type;

                        testViewHolder.setTitle(testTitle);
                        testViewHolder.setLecturer(lecturerName);
                        testViewHolder.setDuration(test.getDuration() + "hrs");

                        testViewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this, TakeTestActivity.class);
                                intent.putExtra(TakeTestActivity.EXTRA_TEST_KEY, testKey);
                                intent.putExtra(TakeTestActivity.EXTRA_TEST_TITLE, testTitle);
                                intent.putExtra(TakeTestActivity.EXTRA_TEST_POJO, test);
                                startActivity(intent);
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        };

        mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                loadingIndicator.setVisibility(View.GONE);

                if (mAdapter.getItemCount() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onItemRangeRemoved(int positionStart, int itemCount) {
                super.onItemRangeRemoved(positionStart, itemCount);

                if (mAdapter.getItemCount() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                }
            }
        });
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
                String courseKey = mSpinnerAdapter.getItemKey(position);
                persistSelectedCourse(courseKey, course.getCode(), course.getName());

                selectCourseToolbar.setTitle(course.getCode() + " " + course.getCode());
                getTestsFromCourse(courseKey, course.getCode(), course.getName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(MainActivity.this, "Nothing Selected", Toast.LENGTH_SHORT).show();
            }
        });

        fetchBoughtCourses();
    }

    private String getCurrentCourseKey() {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_current_course), Context.MODE_PRIVATE);

        return sharedPref.getString(CURRENT_COURSE_KEY, null);
    }

    private String getCurrentCourseCode() {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_current_course), Context.MODE_PRIVATE);

        return sharedPref.getString(CURRENT_COURSE_CODE, null);
    }

    private String getCurrentCourseName() {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_current_course), Context.MODE_PRIVATE);

        return sharedPref.getString(CURRENT_COURSE_NAME, null);
    }

    public void persistSelectedCourse(String courseKey, String courseCode, String courseName) {
        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_current_course), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(CURRENT_COURSE_KEY, courseKey);
        editor.putString(CURRENT_COURSE_CODE, courseCode);
        editor.putString(CURRENT_COURSE_NAME, courseName);
        editor.commit();
    }

    public void fetchBoughtCourses() {
        // TODO: Filter query by courses that student has bought for current duration
        mCoursesRef = FirebaseDatabase.getInstance().getReference().child("courses");
        mCoursesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mSpinnerAdapter.clear();

                int i = 0;
                for (DataSnapshot courseSnapshot: dataSnapshot.getChildren()) {
                    Course course = courseSnapshot.getValue(Course.class);
                    mSpinnerAdapter.addCourse(i, course, courseSnapshot.getKey());
                    // Log.i("Course", course.getCode()+": "+course.getName());
                }

                mSpinnerAdapter.notifyDataSetChanged();

                String currentCoursekey = getCurrentCourseKey();
                String currentCourseCode = getCurrentCourseCode();
                String currentCourseName = getCurrentCourseName();

                Log.d(TAG,
                        currentCoursekey + " : " + currentCourseCode + " : " + currentCourseName);

                if (currentCoursekey != null) {
                    // Select that course from the spinner
                    mSpinner.setSelection(mSpinnerAdapter.getItemPosition(currentCoursekey));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Snackbar.make(coordinatorLayoutView,
                        databaseError.getCode() + " : " + databaseError.getMessage(),
                        Snackbar.LENGTH_SHORT).show();
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

    private void getTestsFromCourse(String coursekey, String courseCode, String courseName) {
        // TODO: Change the query that mAdapter is using
    }

    private void logout() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // user is now signed out
                        startActivity(new Intent(MainActivity.this, CheckCurrentUser.class));
                        finish();
                    }
                });
    }

    public static class TestHolder extends RecyclerView.ViewHolder{

        public View mView;

        public TestHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setTitle(String name) {
            TextView field = (TextView) mView.findViewById(R.id.test_item_title);
            field.setText(name);
        }

        public void setLecturer(String lecturer) {
            TextView field = (TextView) mView.findViewById(R.id.test_item_lecturer);
            field.setText(lecturer);
        }

        public void setDuration(String duration) {
            TextView field = (TextView) mView.findViewById(R.id.test_item_duration);
            field.setText(duration);
        }
    }

}
