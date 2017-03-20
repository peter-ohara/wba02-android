package com.pascoapp.wba02_android.testOverviewScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pascoapp.wba02_android.APIUtils;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.takeTestScreen.TakeTestActivity;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by peter on 2/5/17.
 */

public class TestOverviewActivity extends AppCompatActivity {

    public static final String EXTRA_TEST_ID = "com.pascoapp.wba02_android.quizId";

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.lowerContent) View lowerContent;
    @BindView(R.id.snackbarPosition) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.loading_indicator) AVLoadingIndicatorView loadingIndicator;
    @BindView(R.id.courseCode) TextView courseCode;
    @BindView(R.id.courseName) TextView courseName;
    @BindView(R.id.testName) TextView testName;
    @BindView(R.id.testDuration) TextView testDuration;
    @BindView(R.id.instructionsList) RecyclerView instructionsRecyclerView;
    @BindView(R.id.bottomBar) View bottomBar;

    private List<String> instructions = new ArrayList<>();
    private InstructionAdapter instructionsAdapter;

    protected Integer dataId;
    protected TestOverviewItem data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            dataId = savedInstanceState.getInt(getExtraKeyForDataId());
        } else {
            setDataIdFromIntentExtras();
        }

        setContentView(R.layout.activity_test_overview);
        ButterKnife.bind(this);
        setupToolbar();
        setUpInstructionsRecyclerView();

        fetchData();
    }

    protected String getExtraKeyForDataId() {
        return EXTRA_TEST_ID;
    }

    protected void setDataIdFromIntentExtras() {
        Intent intent = getIntent();
        dataId = intent.getIntExtra(getExtraKeyForDataId(), -1);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


    protected void fetchData() {
        loadingIndicator.show();
        lowerContent.setVisibility(View.GONE);

        APIUtils.getPascoService(this, TestOverviewService.class).getData(dataId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fetchedData -> {
                    this.data = fetchedData;
                    loadingIndicator.hide();
                    lowerContent.setVisibility(View.VISIBLE);

                    courseCode.setText(data.courseCode);
                    courseName.setText(data.courseName);
                    testName.setText(data.testName);
                    testDuration.setText(data.testDuration);
                    instructions.addAll(data.instructions);
                    instructionsAdapter.notifyDataSetChanged();
                }, e -> {
                    loadingIndicator.hide();
                    Snackbar.make(coordinatorLayout, e.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Retry", view -> fetchData())
                            .show();
                    e.printStackTrace();
                });
    }

    private void setUpInstructionsRecyclerView() {
        instructionsRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        instructionsRecyclerView.setLayoutManager(layoutManager2);

        instructionsAdapter = new InstructionAdapter(instructions);
        instructionsRecyclerView.setAdapter(instructionsAdapter);
    }

    public class InstructionAdapter extends RecyclerView.Adapter<TestOverviewActivity.InstructionAdapter.ViewHolder> {

        private List<String> instructions;

        public InstructionAdapter(List<String> instructions) {
            this.instructions = instructions;
        }

        @Override
        public TestOverviewActivity.InstructionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.instruction_item, parent, false);
            TestOverviewActivity.InstructionAdapter.ViewHolder vh = new TestOverviewActivity.InstructionAdapter.ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(TestOverviewActivity.InstructionAdapter.ViewHolder holder, int position) {
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

    @OnClick(R.id.bottomBar)
    public void startTakeTestActivity(View view) {
        Intent intent = new Intent(this, TakeTestActivity.class);
        intent.putExtra(TakeTestActivity.EXTRA_TEST_ID, dataId);
        startActivity(intent);
    }

}
