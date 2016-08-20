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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.Query;
import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.services.courses.Course;
import com.pascoapp.wba02_android.services.courses.Courses;
import com.pascoapp.wba02_android.services.lecturers.Lecturer;
import com.pascoapp.wba02_android.services.lecturers.Lecturers;
import com.pascoapp.wba02_android.services.programmes.Programme;
import com.pascoapp.wba02_android.services.programmes.Programmes;
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
    @BindView(R.id.lecturerName)
    TextView lecturerName;
    @BindView(R.id.lecturerIcon)
    ImageView lecturerIcon;
    @BindView(R.id.programmesList)
    RecyclerView programmesRecyclerView;
    @BindView(R.id.instructionsList)
    RecyclerView instructionsRecyclerView;
    @BindView(R.id.bottomBar)
    View bottomBar;

    private String testKey;
    private Test test;
    private Lecturer lecturer;
    private Course course;
    private List<Programme> programmes = new ArrayList<>();
    private List<String> instructions = new ArrayList<>();
    private ProgrammeAdapter programmesAdapter;
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
        setupProgrammesRecyclerView();
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

    private void setupProgrammesRecyclerView() {
        programmesRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        programmesRecyclerView.setLayoutManager(layoutManager);

        programmesAdapter = new ProgrammeAdapter(programmes);
        programmesRecyclerView.setAdapter(programmesAdapter);
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
                .flatMap(course -> {
                    this.course = course;
                    Object[] lecturerKeys = test.getLecturerKeys().keySet().toArray();
                    return Lecturers.fetchLecturer((String) lecturerKeys[0]);
                })
                .flatMap(lecturer -> {
                    this.lecturer = lecturer;
                    Query query = Programmes.PROGRAMMES_REF.limitToFirst(5);
                    return Programmes.fetchListOfProgrammes(query);
                })
                .subscribe(newProgrammes -> {
                    loadingIndicator.setVisibility(View.GONE);
                    lowerContent.setVisibility(View.VISIBLE);

                    testName.setText(Helpers.getTestName(test));
                    testDuration.setText(test.getDuration() + "hrs");

                    courseCode.setText(course.getCode());
                    courseName.setText(course.getName());

                    lecturerName.setText(lecturer.getFirstName() + " " + lecturer.getLastName());
                    lecturerIcon.setImageDrawable(
                            Helpers.getIcon(
                                    lecturer.getKey(),
                                    lecturer.getFirstName().substring(0, 1), 24
                            )
                    );

                    programmes.clear();
                    programmes.addAll(newProgrammes);
                    programmesAdapter.notifyDataSetChanged();

                    instructions.clear();
                    instructions.addAll(test.getInstructions());
                    instructionsAdapter.notifyDataSetChanged();

                }, firebaseException -> {
                    loadingIndicator.setVisibility(View.GONE);
                    System.out.println("TestOverviewActivity: " + firebaseException.getMessage());
                    Snackbar.make(coordinatorLayout, firebaseException.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Retry", view -> refreshData(testKey))
                            .show();
                });
    }

    public class ProgrammeAdapter extends RecyclerView.Adapter<ProgrammeAdapter.ViewHolder> {

        private List<Programme> programmes;

        public ProgrammeAdapter(List<Programme> programmes) {
            this.programmes = programmes;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.programme_item, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Programme programme = programmes.get(position);
            holder.iconView.setImageDrawable(
                    Helpers.getIcon(
                            programme.getKey(),
                            programme.getName().substring(0, 1),
                            24 // fontSize in sp
                    )
            );
            holder.programmeName.setText(programme.getName());
        }

        @Override
        public int getItemCount() {
            return programmes.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.programmeIcon)
            ImageView iconView;
            @BindView(R.id.programmeName)
            TextView programmeName;

            public ViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
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
