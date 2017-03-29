package com.pascoapp.wba02_android.store.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.buyTests.BuyTestsActivity;
import com.pascoapp.wba02_android.data.course.Course;

import java.util.List;


public class CourseListAdapter extends RecyclerView.Adapter<CourseViewHolder> {

    public static final String EXTRA_COURSE_ID
            = CourseListAdapter.class.getCanonicalName() + "extraCourseId";

    private final Context context;
    private final List<Course> courses;

    public CourseListAdapter(Context context, List<Course> courses) {
        this.context = context;
        this.courses = courses;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_item, parent, false);
        return new CourseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {

        Course course = courses.get(position);

        holder.courseCode.setText(course.code);
        holder.courseName.setText(course.name);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, BuyTestsActivity.class);
            intent.putExtra(EXTRA_COURSE_ID, course.id);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }


}
