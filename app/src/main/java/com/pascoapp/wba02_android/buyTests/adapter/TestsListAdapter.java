package com.pascoapp.wba02_android.buyTests.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.buyTests.BuyTestsActivity;
import com.pascoapp.wba02_android.data.test.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class TestsListAdapter extends RecyclerView.Adapter<TestViewHolder> {

    private List<Test> mTests;
    private final BuyTestsActivity.TestItemListener mTestItemListener;

    private List<Test> mSelectedTests = new ArrayList<>();

    public TestsListAdapter(List<Test> mTests, BuyTestsActivity.TestItemListener testItemListener) {
        this.mTests = mTests;
        this.mTestItemListener = testItemListener;
    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_buy_test_item, parent, false);
        return new TestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(TestViewHolder holder, int position) {
        Test test = mTests.get(position);
        holder.testName.setText(test.name);

        holder.price.setText(test.pascoCredits + " PC");

        holder.checkBox.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if(isChecked){
                checkTest(test);
            } else{
                unCheckTest(test);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTests.size();
    }

    public void replaceData(List<Test> tests) {
        setList(tests);
        notifyDataSetChanged();
    }

    private void setList(List<Test> tests) {
        checkNotNull(tests);
        mSelectedTests.clear();
        mTests = tests;
    }

    private void checkTest(Test testData){
        mSelectedTests.add(testData);
        mTestItemListener.onTestSelected();
    }

    private void unCheckTest(Test testData){
        mSelectedTests.remove(testData);
        mTestItemListener.onTestSelected();
    }

    public List<Test> getSelectedTests() {
        return mSelectedTests;
    }

}
