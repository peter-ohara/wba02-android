package com.pascoapp.wba02_android.testDetail.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pascoapp.wba02_android.R;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class InstructionAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<String> mInstructions;

    public InstructionAdapter(List<String> mInstructions) {
        setList(mInstructions);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.instruction_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.instructionView.setText(mInstructions.get(position));
    }

    @Override
    public int getItemCount() {
        return mInstructions.size();
    }

    public void replaceData(List<String> instructions) {
        setList(instructions);
        notifyDataSetChanged();
    }

    private void setList(List<String> instructions) {
        mInstructions = checkNotNull(instructions);
    }

}
