package com.pascoapp.wba02_android.storeScreen;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by peter on 3/17/17.
 */

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder> {

    private final Context context;
    private final List<Course> courses;

    public CourseListAdapter(Context context, List<Course> courses) {
        this.context = context;
        this.courses = courses;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_course_item, parent, false);
        return new CourseViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {

        Course course = courses.get(position);

        holder.courseIcon.setImageDrawable(Helpers.getIcon(
                course.id.toString(),
                course.code,
                14 // font-size in sp
        ));

        String courseName = course.code + " " + course.name;

        holder.courseName.setText(courseName);

        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(context, courseName, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }


    public class CourseViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.courseIcon) ImageView courseIcon;
        @BindView(R.id.courseName) TextView courseName;

        public View itemView;

        public CourseViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            ButterKnife.bind(this, itemView);
        }
    }
}
