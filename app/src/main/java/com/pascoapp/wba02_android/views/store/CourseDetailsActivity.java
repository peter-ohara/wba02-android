package com.pascoapp.wba02_android.views.store;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.services.APIUtils;
import com.pascoapp.wba02_android.services.courses.Course;
import com.pascoapp.wba02_android.services.courses.CourseService;
import com.pascoapp.wba02_android.services.testOwnerships.TestOwnership;
import com.pascoapp.wba02_android.services.testOwnerships.TestOwnerships;
import com.pascoapp.wba02_android.services.tests.Test;
import com.pascoapp.wba02_android.services.tests.TestService;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.pascoapp.wba02_android.views.main.CourseActivity.EXTRA_COURSE_KEY;

public class CourseDetailsActivity extends AppCompatActivity {

    public static final String TAG = CourseDetailsActivity.class.getSimpleName();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.snackbarPosition)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.courseCode)
    TextView courseCode;

    @BindView(R.id.courseName)
    TextView courseName;

    @BindView(R.id.testList)
    RecyclerView testsRecyclerView;

    @BindView(R.id.loading_indicator)
    AVLoadingIndicatorView loadingIndicator;

    @BindView(R.id.doneButton)
    View doneButton;

    private Integer courseKey;

    private TestListAdapter testListAdapter;
    private List<Test> tests = new ArrayList<>();

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private Course course;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            courseKey = savedInstanceState.getInt(EXTRA_COURSE_KEY);
        } else {
            setCourseKeyFromIntentExtras();
        }

        setContentView(R.layout.activity_course_details);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        testsRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        testsRecyclerView.setLayoutManager(linearLayoutManager);

        testListAdapter = new TestListAdapter(this, tests);
        testsRecyclerView.setAdapter(testListAdapter);


        refreshData(courseKey);
    }

    private void setCourseKeyFromIntentExtras() {
        Intent intent = getIntent();
        courseKey = intent.getIntExtra(EXTRA_COURSE_KEY, 0);
    }

    private void refreshData(Integer courseKey) {
        loadingIndicator.show();

        CourseService courseService = APIUtils.getCourseService();
        TestService testService = APIUtils.getTestService();

        Log.d(TAG, "fetchData: courseKey = " + courseKey);
        courseService.getCourse(courseKey)
                .subscribeOn(Schedulers.io())
                .concatMap(fetchedCourse -> {
                    this.course = fetchedCourse;
                    return testService.getTests(course.getId());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fetchedTests -> {
                    loadingIndicator.hide();
                    Log.d(TAG, "fetchData: " + fetchedTests);
                    courseCode.setText(course.getCode());
                    courseName.setText(course.getName());

                    tests.clear();
                    tests.addAll(fetchedTests);
                    testListAdapter.notifyDataSetChanged();
                }, throwable -> {
                    loadingIndicator.hide();
                    Log.d(TAG, "errorOnRefresh: " + throwable.getMessage());
                    Snackbar.make(coordinatorLayout, throwable.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Retry", view -> refreshData(courseKey))
                            .show();
                });
    }


    @OnClick(R.id.doneButton)
    public void buyCourses() {
        Date today = new Date();

        Map<String, Object> testOwnershipMap = new HashMap<>();
        for (Test test : testListAdapter.getSelectedTests()) {
            String key = TestOwnerships.TestOwnershipS_REF.push().getKey();

            TestOwnership testOwnership = new TestOwnership();
            testOwnership.setUserId(user.getUid());
            testOwnership.setTestId(test.getId());
            testOwnership.setTransactionDate(today.getTime());
            testOwnership.setExpiryDate(today.getTime()); //TODO: Fix this
            testOwnershipMap.put(key, testOwnership);
        }

        Log.d(TAG, "buyCourses: " + testOwnershipMap);

        TestOwnerships.TestOwnershipS_REF.updateChildren(testOwnershipMap);
        finish();
    }
}
