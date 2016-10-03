package com.pascoapp.wba02_android.views.takeTest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.services.courses.Course;
import com.pascoapp.wba02_android.services.courses.Courses;
import com.pascoapp.wba02_android.services.tests.Test;
import com.pascoapp.wba02_android.services.tests.Tests;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestOverviewActivity extends AppCompatActivity {

    public static final String EXTRA_TEST_KEY =
            "com.pascoapp.wba02_android.testKey";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.lowerContent) View lowerContent;
    @BindView(R.id.snackbarPosition)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.loading_indicator)
    ProgressBar loadingIndicator;
    @BindView(R.id.courseCode)
    TextView courseCode;
    @BindView(R.id.courseName)
    TextView courseName;
    @BindView(R.id.testName)
    TextView testName;
    @BindView(R.id.testDuration)
    TextView testDuration;
    @BindView(R.id.instructionsList)
    RecyclerView instructionsRecyclerView;
    @BindView(R.id.bottomBar)
    View bottomBar;

    private String testKey;
    private Test test;
    private Course course;
    private List<String> instructions = new ArrayList<>();
    private InstructionAdapter instructionsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            testKey = savedInstanceState.getString(EXTRA_TEST_KEY);
        } else {
            setTestKeyFromIntentExtras();
        }

        setContentView(R.layout.activity_test_overview);
        ButterKnife.bind(this);

        setupToolbar();
        setUpInstructionsRecyclerView();

        refreshData(testKey);
    }

    private void setUpInstructionsRecyclerView() {
        instructionsRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        instructionsRecyclerView.setLayoutManager(layoutManager2);

        instructionsAdapter = new InstructionAdapter(instructions);
        instructionsRecyclerView.setAdapter(instructionsAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(EXTRA_TEST_KEY, testKey);
    }

    public void setTestKeyFromIntentExtras() {
        Intent intent = getIntent();
        testKey = intent.getStringExtra(EXTRA_TEST_KEY);
    }

    @OnClick(R.id.bottomBar)
    public void startTakeTestActivity(View view) {
        Intent intent = new Intent(this, TakeTestActivity.class);
        intent.putExtra(TakeTestActivity.EXTRA_TEST_KEY, testKey);
        startActivity(intent);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void refreshData(String testKey) {
        loadingIndicator.setVisibility(View.VISIBLE);
        lowerContent.setVisibility(View.GONE);
        Tests.fetchTest(testKey)
                .flatMap(test -> {
                    this.test = test;
                    return Courses.fetchCourse(test.getCourseKey());
                })
                .subscribe(course -> {
                    this.course = course;

                    loadingIndicator.setVisibility(View.GONE);
                    lowerContent.setVisibility(View.VISIBLE);

                    testName.setText(Helpers.getTestName(test));
                    testDuration.setText(test.getDuration() + "hrs");

                    courseCode.setText(course.getCode());
                    courseName.setText(course.getName());

                    instructions.clear();
                    instructions.addAll(test.getInstructions());
                    instructionsAdapter.notifyDataSetChanged();

                }, firebaseException -> {
                    loadingIndicator.setVisibility(View.GONE);
                    Snackbar.make(coordinatorLayout, firebaseException.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Retry", view -> refreshData(testKey))
                            .show();
                });
    }


    public class InstructionAdapter extends RecyclerView.Adapter<InstructionAdapter.ViewHolder> {

        private List<String> instructions;

        public InstructionAdapter(List<String> instructions) {
            this.instructions = instructions;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.instruction_item, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.instructionView.setText(instructions.get(position));
        }

        @Override
        public int getItemCount() {
            return instructions.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.instruction)
            TextView instructionView;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }


}
