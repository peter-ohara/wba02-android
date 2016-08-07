package com.pascoapp.wba02_android.main;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.pascoapp.wba02_android.App;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.State;
import com.pascoapp.wba02_android.services.courses.Course;

import javax.inject.Inject;

import trikita.jedux.Action;
import trikita.jedux.Store;

/**
 * Created by peter on 7/25/16.
 */
public class CourseViewHolder extends RecyclerView.ViewHolder {

    private BoughtCoursesAdapter adapter;

    private final TextView courseNameView;
    private final Button moreButton;
    private View itemView;

    private Course course;

    @Inject
    Store<Action, State> store;

    public CourseViewHolder(View itemView, BoughtCoursesAdapter adapter) {
        super(itemView);
        this.itemView = itemView;
        App.getStoreComponent().inject(this);
        this.adapter = adapter;

        courseNameView = (TextView) itemView.findViewById(R.id.courseCode);
        moreButton = (Button) itemView.findViewById(R.id.moreButton);
    }

    public void setCourse(Course course, int position) {
        this.course = course;
        courseNameView.setText(course.getCode());
    }
}
