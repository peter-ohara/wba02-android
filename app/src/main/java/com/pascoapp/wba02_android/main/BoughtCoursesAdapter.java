package com.pascoapp.wba02_android.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.State;
import com.pascoapp.wba02_android.takeTest.TestViewModel;

import java.util.ArrayList;
import java.util.List;

import trikita.jedux.Action;
import trikita.jedux.Store;

/**
 * Created by peter on 7/27/16.
 */


public class BoughtCoursesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = BoughtCoursesAdapter.class.getSimpleName();
    public static final int TYPE_TEST = 1;
    public static final int TYPE_COURSE = 2;
    private List<MainListItem> mainListItems = new ArrayList<>();

    private Context context;
    private Store<Action, State> store;

    public BoughtCoursesAdapter(Context context, Store<Action, State> store) {
        this.context = context;
        this.store = store;
    }

    public void clear() {
        mainListItems.clear();
    }

    public void addAll(List<MainListItem> items) {
        mainListItems.addAll(items);
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
        switch (viewType) {
            case TYPE_TEST:
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.test_item, parent, false);
                return new TestViewHolder(v, store);
            case TYPE_COURSE:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.course_item, parent, false);
                return new CourseViewHolder(v);
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

