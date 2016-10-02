package com.pascoapp.wba02_android.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.services.courses.Course;
import com.pascoapp.wba02_android.services.users.User;
import com.pascoapp.wba02_android.services.users.Users;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by peter on 9/25/16.
 */

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder> {

    private Context mContext;
    private List<Course> mItems;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private User fetchedUser;

    public CourseListAdapter(Context mContext, List<Course> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;

        fetchUser();
    }

    private void fetchUser() {
        Users.fetchUser(user.getUid())
                .subscribe(fetchedUser -> {
                    this.fetchedUser = fetchedUser;
                    // If user has no courses initial coursekeys with an empty hashmap
                    if (fetchedUser.getCourseKeys() == null) {
                        fetchedUser.setCourseKeys(new HashMap<>());
                    }
                }, throwable -> {
                    Toast.makeText(mContext, "Error fetching user's course list",
                            Toast.LENGTH_SHORT).show();
                });
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
                        course.getKey(),
                        course.getCode(),
                        10 // fontSize in sp
                )
        );
        holder.courseName.setText(course.getName());

        if (fetchedUser.getCourseKeys().containsKey(course.getKey())) {
            // course is already added
            setCourseAsAdded(holder);
        } else {
            // course is not added
            setCourseAsNotAdded(holder);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private void setCourseAsAdded(CourseViewHolder holder) {
        holder.addedStateIcon.setImageResource(R.drawable.ic_checkbox_marked_outline_grey600_24dp);
        holder.itemView.setOnClickListener(view -> {
            // ask user if they want to remove it
            removeTheCourse(holder);
        });
    }

    private void setCourseAsNotAdded(CourseViewHolder holder) {
        holder.addedStateIcon.setImageResource(R.drawable.ic_checkbox_blank_outline_grey600_24dp);
        holder.itemView.setOnClickListener(view -> {
            // ask user if they want to add it
            addTheCourse(holder);
        });
    }

    private void removeTheCourse(CourseViewHolder holder) {
        fetchedUser.getCourseKeys().remove(holder.course.getKey());
        setCourseAsNotAdded(holder);

        // Update the user
        Users.put(fetchedUser);
    }

    private void addTheCourse(CourseViewHolder holder) {
        fetchedUser.getCourseKeys().put(holder.course.getKey(), true);
        setCourseAsAdded(holder);

        // Update the user
        Users.put(fetchedUser);
    }



    public class CourseViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.courseIcon) ImageView courseIcon;
        @BindView(R.id.courseName) TextView courseName;
        @BindView(R.id.addedStateIcon) ImageView addedStateIcon;

        public Course course;

        public CourseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
