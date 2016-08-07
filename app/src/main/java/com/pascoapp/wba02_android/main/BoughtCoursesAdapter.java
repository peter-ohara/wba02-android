package com.pascoapp.wba02_android.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.services.courses.Course;
import com.pascoapp.wba02_android.services.tests.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 7/27/16.
 */


public class BoughtCoursesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = BoughtCoursesAdapter.class.getSimpleName();
    public static final int TYPE_TEST = 1;
    public static final int TYPE_COURSE = 2;
    private List<MainListItem> mainListItems = new ArrayList<>();

    private Context context;

    public BoughtCoursesAdapter(Context context) {
        this.context = context;
    }

    public void add(int position, MainListItem item) {
        mainListItems.add(position, item);
    }

    public void addAll(List<MainListItem> items) {
        mainListItems.addAll(items);
    }

    public void addAll(int position, List<MainListItem> items) {
        mainListItems.addAll(items);
    }

    public void clear() {
        mainListItems.clear();
    }

    public int indexOf(MainListItem mainListItem) {
        return mainListItems.indexOf(mainListItem);
    }

    @Override
    public int getItemViewType(int position) {
        MainListItem item = mainListItems.get(position);
        if (item.getTest() != null) {
            return TYPE_TEST;
        } else {
            return TYPE_COURSE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TEST:
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.test_item, parent, false);
                return new TestViewHolder(v);
            case TYPE_COURSE:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.course_item, parent, false);
                return new CourseViewHolder(v, this);
            default:
                Log.wtf(TAG, "onCreateViewHolder: unidentified type");
                Toast.makeText(context, "Something wicked this way comes", Toast.LENGTH_SHORT).show();
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_TEST:
                final Test test = mainListItems.get(position).getTest();
                ((TestViewHolder) holder).setTest(test);
                break;
            case TYPE_COURSE:
                final Course course = mainListItems.get(position).getCourse();
                ((CourseViewHolder) holder).setCourse(course, position);
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

