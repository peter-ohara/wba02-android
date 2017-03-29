package com.pascoapp.wba02_android.testDetail.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pascoapp.wba02_android.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.instruction)
    TextView instructionView;

    public ViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
