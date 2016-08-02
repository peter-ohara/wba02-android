package com.pascoapp.wba02_android.takeTest.TestOverview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pascoapp.wba02_android.State;
import com.pascoapp.wba02_android.main.TestViewHolder;
import com.pascoapp.wba02_android.R;

import java.util.ArrayList;
import java.util.List;

import trikita.jedux.Action;
import trikita.jedux.Store;

/**
 * Created by peter on 7/24/16.
 */

public class InstructionsAdapter extends RecyclerView.Adapter<TestViewHolder> {

    private List<String> mInstructions = new ArrayList<>();

    private Store<Action, State> store;

    public InstructionsAdapter(Store<Action, State> store) {
        this.store = store;
    }

    public void setInstructions(List<String> instructions) {
        mInstructions.clear();
        mInstructions.addAll(instructions);
    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.instruction_item, parent, false);
        TestViewHolder holder = new TestViewHolder(v, store);
        return holder;
    }

    @Override
    public void onBindViewHolder(TestViewHolder holder, int position) {
        final String instruction = mInstructions.get(position);
    }

    @Override
    public int getItemCount() {
        return mInstructions.size();
    }
}
