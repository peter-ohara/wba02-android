package com.pascoapp.wba02_android.views.store;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.services.courses.Course;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pascoapp.wba02_android.views.main.CourseActivity.EXTRA_COURSE_KEY;

/**
 * Created by peter on 9/25/16.
 */

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder> {

    private Context mContext;
    private List<Course> mItems;

    public CourseListAdapter(Context mContext, List<Course> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_course_item, parent, false);
        CourseViewHolder vh = new CourseViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CourseViewHolder holder, int position) {
        Course course = mItems.get(position);

        holder.course = course;
        holder.courseIcon.setImageDrawable(
                Helpers.getIcon(
                        course.getId().toString(),
                        course.getCode(),
                        10 // fontSize in sp
                )
        );
        holder.courseName.setText(course.getName());

        holder.itemView.setOnClickListener(v -> {
            ChooseCoursesActivity chooseCoursesActivity = (ChooseCoursesActivity) mContext;
            Intent intent = new Intent(chooseCoursesActivity, CourseDetailsActivity.class);
            intent.putExtra(EXTRA_COURSE_KEY, course.getId());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class CourseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.courseIcon) ImageView courseIcon;
        @BindView(R.id.courseName) TextView courseName;

        public Course course;

        public CourseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
