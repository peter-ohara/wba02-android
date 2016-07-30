package com.pascoapp.wba02_android;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by peter on 7/25/16.
 */
public class CourseViewHolder extends RecyclerView.ViewHolder {

    private final TextView courseNameView;
    private View itemView;

    private CourseViewModel courseViewModel;

    public CourseViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;

        courseNameView = (TextView) itemView.findViewById(R.id.courseCode);
    }

    public void setCourseViewModel(CourseViewModel courseViewModel) {
        this.courseViewModel = courseViewModel;
        courseNameView.setText(courseViewModel.getCode());
    }

}
