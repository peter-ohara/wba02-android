package com.pascoapp.wba02_android.buyTests.adapter;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pascoapp.wba02_android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TestViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.test_name)
    public TextView testName;

    @BindView(R.id.price)
    public TextView price;

    @BindView(R.id.checkbox)
    public AppCompatCheckBox checkBox;

    public View itemView;

    public TestViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView = itemView;
    }
}
