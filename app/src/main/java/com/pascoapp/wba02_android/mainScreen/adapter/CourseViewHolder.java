package com.pascoapp.wba02_android.mainScreen.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pascoapp.wba02_android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by peter on 8/12/16.
 */
public class CourseViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.courseCode)
    TextView courseCode;
    @BindView(R.id.moreButton)
    Button moreButton;

    public CourseViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
