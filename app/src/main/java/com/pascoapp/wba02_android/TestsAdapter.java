package com.pascoapp.wba02_android;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pascoapp.wba02_android.takeTest.TestViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 7/27/16.
 */

public class TestsAdapter extends RecyclerView.Adapter<TestViewHolder> {

    private List<TestViewModel> mTests = new ArrayList<>();

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.test_item, parent, false);
        TestViewHolder holder = new TestViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(TestViewHolder holder, int position) {
        final TestViewModel test = mTests.get(position);
    }

    @Override
    public int getItemCount() {
        return mTests.size();
    }

    public void addTest(int position, TestViewModel test) {
        mTests.add(position, test);
    }

    public void clear() {
        mTests.clear();
    }

}
