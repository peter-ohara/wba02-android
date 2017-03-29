package com.pascoapp.wba02_android.store.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pascoapp.wba02_android.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CourseViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.courseCode)
    TextView courseCode;
    @BindView(R.id.courseName)
    TextView courseName;

    public View itemView;

    CourseViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
    }
}
