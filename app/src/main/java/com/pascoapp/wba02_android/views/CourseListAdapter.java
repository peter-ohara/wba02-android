package com.pascoapp.wba02_android.views;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
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
                        course.getKey(),
                        course.getCode(),
                        10 // fontSize in sp
                )
        );
        holder.courseName.setText(course.getName());

        Users.fetchUser(user.getUid())
                .subscribe(fetchedUser -> {
                    // If user has no courses initial coursekeys with an empty hashmap
                    if (fetchedUser.getCourseKeys() == null) {
                        fetchedUser.setCourseKeys(new HashMap<>());
                    }

                    if (fetchedUser.getCourseKeys().containsKey(course.getKey())) {
                        // course is already added
                        setCourseAsAdded(holder);
                    } else {
                        // course is not added
                        setCourseAsNotAdded(holder);
                    }
                }, throwable -> {
                    Toast.makeText(mContext, "Error fetching user's course list",
                            Toast.LENGTH_SHORT).show();
                });
    }

    private void setCourseAsAdded(CourseViewHolder holder) {
        holder.addedStateIcon.setImageResource(R.drawable.ic_checkbox_marked_outline_grey600_24dp);
        holder.itemView.setOnClickListener(view -> {
            // ask user if they want to remove it
            showRemoveCourseDialog(holder);
        });
    }

    private void setCourseAsNotAdded(CourseViewHolder holder) {
        holder.addedStateIcon.setImageResource(R.drawable.ic_checkbox_blank_outline_grey600_24dp);
        holder.itemView.setOnClickListener(view -> {
            // ask user if they want to add it
            showAddCourseDialog(holder);
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    private void showRemoveCourseDialog(CourseViewHolder holder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Do you want to remove this course?");
        builder.setPositiveButton("Yes", (dialog, id) -> {
            // User clicked OK button
            Users.fetchUser(user.getUid())
                    .subscribe(fetchedUser -> {
                        // If user has no courses initial coursekeys with an empty hashmap
                        if (fetchedUser.getCourseKeys() == null) {
                            fetchedUser.setCourseKeys(new HashMap<>());
                        }

                        fetchedUser.getCourseKeys().remove(holder.course.getKey());
                        setCourseAsNotAdded(holder);

                        // Update the user
                        Users.put(fetchedUser);
                    }, throwable -> {
                        Toast.makeText(mContext, "Error removing course from user's list",
                                Toast.LENGTH_SHORT).show();
                    });
        });
        builder.setNegativeButton("Cancel", (dialog, id) -> {
            // User cancelled the dialog
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showAddCourseDialog(CourseViewHolder holder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Do you want to add this course?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                Users.fetchUser(user.getUid())
                        .subscribe(fetchedUser -> {
                            // If user has no courses initial coursekeys with an empty hashmap
                            if (fetchedUser.getCourseKeys() == null) {
                                fetchedUser.setCourseKeys(new HashMap<>());
                            }

                            fetchedUser.getCourseKeys().put(holder.course.getKey(), true);
                            setCourseAsAdded(holder);

                            // Update the user
                            Users.put(fetchedUser);
                        }, throwable -> {
                            Toast.makeText(mContext, "Error adding course to user's list",
                                    Toast.LENGTH_SHORT).show();
                        });
            }
        });
        builder.setNegativeButton("Cancel", (dialog, id) -> {
            // User cancelled the dialog
        });
        AlertDialog dialog = builder.create();
        dialog.show();
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
