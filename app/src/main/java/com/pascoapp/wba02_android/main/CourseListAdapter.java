package com.pascoapp.wba02_android.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pascoapp.wba02_android.parseSubClasses.Course;
import com.pascoapp.wba02_android.R;

import java.util.List;

/**
 * Provides a binding from an Courses fetched from parse
 * to views that are displayed within a RecyclerView
 */
public class CourseListAdapter extends RecyclerView.Adapter {

    private List<Course> mCourses;
    private OnItemClickListener mItemClickListener;

    public CourseListAdapter(List<Course> mCourses) {
        this.mCourses = mCourses;
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {

        public TextView titleView;

        public String courseId;

        public CourseViewHolder(View itemView) {
            super(itemView);

            titleView = (TextView) itemView.findViewById(R.id.course_item_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(view, courseId);
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.course_list_item_template, parent, false);
        CourseViewHolder vh = new CourseViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Course course = mCourses.get(position);

        ((CourseViewHolder) holder).titleView.setText(course.getCode() + " " + course.getName());

        ((CourseViewHolder) holder).courseId = course.getObjectId();
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, String courseId);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}