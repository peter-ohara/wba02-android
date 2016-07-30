package com.pascoapp.wba02_android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pascoapp.wba02_android.takeTest.TestViewModel;

import java.util.List;

/**
 * Created by peter on 7/27/16.
 */


public class CoursesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = CoursesAdapter.class.getSimpleName();
    public static final int TYPE_TEST = 1;
    public static final int TYPE_COURSE = 2;
    private List<MainListItem> mainListItems;

    private Context context;

    public CoursesAdapter(Context context, List<MainListItem> mainListItems) {
        this.context = context;
        this.mainListItems = mainListItems;
    }

    @Override
    public int getItemViewType(int position) {
        MainListItem item = mainListItems.get(position);
        if (item.getTestViewModel() != null) {
            return TYPE_TEST;
        } else {
            return TYPE_COURSE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_TEST:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.test_item, parent, false);
                holder = new TestViewHolder(v);
                break;
            case TYPE_COURSE:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.course_item, parent, false);
                holder = new CourseViewHolder(v);
                break;
            default:
                Log.wtf(TAG, "onCreateViewHolder: unidentified type");
                Toast.makeText(context, "Something wicked this way comes", Toast.LENGTH_SHORT).show();
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_TEST:
                final TestViewModel test = mainListItems.get(position).getTestViewModel();
                ((TestViewHolder) holder).setTestViewModel(test);
                break;
            case TYPE_COURSE:
                final CourseViewModel course = mainListItems.get(position).getCourseViewModel();
                ((CourseViewHolder) holder).setCourseViewModel(course);
                break;
            default:
                Log.wtf(TAG, "onCreateViewHolder: unidentified type");
                Toast.makeText(context, "Something wicked this way comes", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return mainListItems.size();
    }
}

