package com.pascoapp.wba02_android.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.data.test.Test;
import com.pascoapp.wba02_android.main.MainActivity;

import java.util.List;

import timber.log.Timber;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by peter on 8/7/16.
 */

public class MainViewListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_TEST = 1;
    public static final int TYPE_COURSE = 2;

    private List<MainScreenItem> mItems;

    private final MainActivity.TestItemListener mTestItemListener;

    public MainViewListAdapter(List<MainScreenItem> items, MainActivity.TestItemListener testItemListener) {
        mItems = items;
        mTestItemListener = testItemListener;
    }

    @Override
    public int getItemViewType(int position) {
        MainScreenItem item = mItems.get(position);
        if (item.getClass() == MainScreenHeader.class) {
            return TYPE_COURSE;
        } else {
            return TYPE_TEST;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_COURSE: {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.course_item, parent, false);
                RecyclerView.ViewHolder vh = new CourseViewHolder(v);
                return vh;
            }
            case TYPE_TEST: {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.test_item, parent, false);
                RecyclerView.ViewHolder vh = new TestViewHolder(v);
                return vh;
            }
            default:
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.error_item, parent, false);
                RecyclerView.ViewHolder vh = new TestViewHolder(v);
                Timber.d("onCreateViewHolder: Wrong viewType encountered");
                return vh;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_COURSE: {
                MainScreenHeader header = (MainScreenHeader) mItems.get(position);
                ((CourseViewHolder) holder).courseCode.setText(header.course.code);
                ((CourseViewHolder) holder).moreButton.setOnClickListener(
                        view -> mTestItemListener.onMoreButtonClicked(header.course));
                break;
            }
            case TYPE_TEST: {
                MainScreenTest mainScreenTest = (MainScreenTest) mItems.get(position);
                Test test = mainScreenTest.test;

                ((TestViewHolder) holder).testIcon.setImageDrawable(
                        Helpers.getIcon(
                                test.id.toString(),
                                test.courseCode,
                                14 // fontSize in sp
                        )
                );
                ((TestViewHolder) holder).testName.setText(test.name);

                ((TestViewHolder) holder).questionCount.setText(test.questionCount + " q");

                ((TestViewHolder) holder).itemView.setOnClickListener(view -> mTestItemListener.onTestClick(test));

                break;
            }
            default:
                Timber.d("onCreateViewHolder: Wrong viewType encountered");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void replaceData(List<MainScreenItem> items) {
        setList(items);
        notifyDataSetChanged();
    }

    private void setList(List<MainScreenItem> items) {
        checkNotNull(items);
        mItems = items;
    }
}
