package com.pascoapp.wba02_android.views.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.services.courses.Course;
import com.pascoapp.wba02_android.services.tests.Test;
import com.pascoapp.wba02_android.views.takeTest.TestOverviewActivity;

import java.util.List;

/**
 * Created by peter on 8/7/16.
 */

public class MainViewListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String LOG_TAG = MainViewListAdapter.class.getSimpleName();

    public static final int TYPE_TEST = 1;
    public static final int TYPE_COURSE = 2;

    private List<MainViewListItem> mItems;

    private Context context;

    public MainViewListAdapter(Context context, List<MainViewListItem> mItems) {
        this.context = context;
        this.mItems = mItems;
    }

    public void add(MainViewListItem mainViewListItem) {
        mItems.add(mainViewListItem);
        notifyDataSetChanged();
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<MainViewListItem> items) {
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        MainViewListItem item = mItems.get(position);
        if (item.getCourse() != null) {
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
                Log.e(LOG_TAG, "onCreateViewHolder: Wrong viewType encountered");
                return vh;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_COURSE: {
                Course course = mItems.get(position).getCourse();
                ((CourseViewHolder) holder).courseCode.setText(course.getCode());
                ((CourseViewHolder) holder).moreButton.setOnClickListener(view -> {
                    Toast.makeText(context, "More tests coming soon!", Toast.LENGTH_SHORT).show();
                });
                break;
            }
            case TYPE_TEST: {
                Test test = mItems.get(position).getTest();

                ((TestViewHolder) holder).testIcon.setImageDrawable(
                        Helpers.getIcon(
                                test.getKey(),
                                test.getCourseKey(),
                                14 // fontSize in sp
                        )
                );
                ((TestViewHolder) holder).testName.setText(Helpers.getTestName(test));
                ((TestViewHolder) holder).lecturerName.setText("lecturer1");

                int questionCount = 0;
                if (test.getQuestionKeys() != null) {
                    questionCount = test.getQuestionKeys().size();
                }
                ((TestViewHolder) holder).questionCount.setText(questionCount + " q");

                ((TestViewHolder) holder).itemView.setOnClickListener(view -> {
                    Intent intent = new Intent(context, TestOverviewActivity.class);
                    intent.putExtra(TestOverviewActivity.EXTRA_TEST_KEY, test.getKey());
                    context.startActivity(intent);
                });

                break;
            }
            default:
                Log.e(LOG_TAG, "onCreateViewHolder: Wrong viewType encountered");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}
