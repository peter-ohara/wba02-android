package com.pascoapp.wba02_android.views.store;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.services.tests.Test;
import com.pascoapp.wba02_android.services.users.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by peter on 9/25/16.
 */

public class TestListAdapter extends RecyclerView.Adapter<TestListAdapter.TestViewHolder> {

    private Context mContext;
    private List<Test> mItems;

    private List<Test> selectedTests = new ArrayList<>();

    public TestListAdapter(Context mContext, List<Test> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }


    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_test_item, parent, false);
        TestViewHolder vh = new TestViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(TestViewHolder holder, int position) {
        Test test = mItems.get(position);
        holder.test = test;
        holder.testName.setText(Helpers.getTestName(test));

        //in some cases, it will prevent unwanted situations
        holder.checkbox.setOnCheckedChangeListener(null);

        holder.checkbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedTests.add(test);
            } else {
                selectedTests.remove(test);
            }
            Toast.makeText(mContext, selectedTests.toString(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    public class TestViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.testName) TextView testName;
        @BindView(R.id.checkbox) CheckBox checkbox;

        public Test test;

        public TestViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public List<Test> getSelectedTests() {
        return selectedTests;
    }
}
