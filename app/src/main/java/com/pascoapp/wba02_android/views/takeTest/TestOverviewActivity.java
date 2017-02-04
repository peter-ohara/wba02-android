package com.pascoapp.wba02_android.views.takeTest;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.views.ToolbarBaseActivity;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestOverviewActivity extends ToolbarBaseActivity {

    public static final String EXTRA_TEST_ID = "com.pascoapp.wba02_android.testId";
    public static final String TAG = TestOverviewActivity.class.getSimpleName();
    public static final String TEST_OVERVIEW_SCREEN = "test_overview_screen";

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

    @Override
    protected String getExtraKeyForDataId() {
        return EXTRA_TEST_ID;
    }

    @Override
    protected void setUpViewItems() {
        setUpInstructionsRecyclerView();
    }

    @Override
    protected String getApiEndPoint() {
        return TEST_OVERVIEW_SCREEN;
    }

    @Override
    protected void beforeFetchingData() {
        loadingIndicator.show();
        lowerContent.setVisibility(View.GONE);
    }

    @Override
    protected void afterFetchingData(JsonObject data) {
        loadingIndicator.hide();
        lowerContent.setVisibility(View.VISIBLE);

        courseCode.setText(data.get("course_code").getAsString());
        courseName.setText(data.get("course_name").getAsString());
        testName.setText(data.get("test_name").getAsString());
        testDuration.setText(data.get("test_duration").getAsString());
        for (JsonElement instruction: data.get("instructions").getAsJsonArray()) {
            instructions.add(instruction.getAsString());
        }
        instructionsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onErrorFetchingData(Throwable e) {
        loadingIndicator.hide();
        Snackbar.make(coordinatorLayout, e.getMessage(), Snackbar.LENGTH_LONG)
                .setAction("Retry", view -> fetchData())
                .show();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_test_overview;
    }

    private void setUpInstructionsRecyclerView() {
        instructionsRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        instructionsRecyclerView.setLayoutManager(layoutManager2);

        instructionsAdapter = new InstructionAdapter(instructions);
        instructionsRecyclerView.setAdapter(instructionsAdapter);
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

    @OnClick(R.id.bottomBar)
    public void startTakeTestActivity(View view) {
        Intent intent = new Intent(this, TakeTestActivity.class);
        intent.putExtra(TakeTestActivity.EXTRA_TEST_ID, getDataId());
        startActivity(intent);
    }


}
