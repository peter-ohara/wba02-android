package com.pascoapp.wba02_android.views.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pascoapp.wba02_android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by peter on 8/12/16.
 */
public class TestViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.testIcon)
    ImageView testIcon;
    @BindView(R.id.testName)
    TextView testName;
    @BindView(R.id.lecturerName)
    TextView lecturerName;
    @BindView(R.id.questionCount)
    TextView questionCount;
    public View itemView;

    public TestViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
    }
}
