package com.pascoapp.wba02_android.takeTest.TestOverview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pascoapp.wba02_android.BR;
import com.pascoapp.wba02_android.BindingHolder;
import com.pascoapp.wba02_android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 7/24/16.
 */

public class InstructionsAdapter extends RecyclerView.Adapter<BindingHolder> {

    private List<String> mInstructions = new ArrayList<>();

    public void setInstructions(List<String> instructions) {
        mInstructions.clear();
        mInstructions.addAll(instructions);
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.instruction_item, parent, false);
        BindingHolder holder = new BindingHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        final String instruction = mInstructions.get(position);
        holder.getBinding().setVariable(BR.instruction, instruction);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mInstructions.size();
    }
}
