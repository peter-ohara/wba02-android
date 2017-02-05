package com.pascoapp.wba02_android.mainScreen.adapter;

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
import com.pascoapp.wba02_android.mainScreen.TestItem;
import com.pascoapp.wba02_android.testOverviewScreen.TestOverviewActivity;

import java.util.List;

import timber.log.Timber;

/**
 * Created by peter on 8/7/16.
 */

public class MainViewListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_TEST = 1;
    public static final int TYPE_COURSE = 2;

    private List<MainScreenItem> mItems;

    private Context context;

    public MainViewListAdapter(Context context, List<MainScreenItem> mItems) {
        this.context = context;
        this.mItems = mItems;
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
                ((CourseViewHolder) holder).courseCode.setText(header.courseCode);
                ((CourseViewHolder) holder).moreButton.setOnClickListener(view -> {
                    Toast.makeText(context, "More tests coming soon!", Toast.LENGTH_SHORT).show();
                });
                break;
            }
            case TYPE_TEST: {
                MainScreenTest mainScreenTest = (MainScreenTest) mItems.get(position);
                TestItem test = mainScreenTest.test;

                ((TestViewHolder) holder).testIcon.setImageDrawable(
                        Helpers.getIcon(
                                test.id.toString(),
                                test.courseCode,
                                14 // fontSize in sp
                        )
                );
                ((TestViewHolder) holder).testName.setText(test.name);

                ((TestViewHolder) holder).questionCount.setText(test.questionCount + " q");

                ((TestViewHolder) holder).itemView.setOnClickListener(view -> {
                    Intent intent = new Intent(context, TestOverviewActivity.class);
                    intent.putExtra(TestOverviewActivity.EXTRA_TEST_ID, test.id);
                    context.startActivity(intent);
                });

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

}
