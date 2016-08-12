package com.pascoapp.wba02_android.views.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.services.courses.Course;
import com.pascoapp.wba02_android.services.tests.Test;
import com.pascoapp.wba02_android.views.takeTest.TestOverviewActivity;

import java.util.List;

import trikita.anvil.recyclerview.Recycler;

import static trikita.anvil.BaseDSL.dip;
import static trikita.anvil.BaseDSL.margin;
import static trikita.anvil.BaseDSL.text;
import static trikita.anvil.BaseDSL.withId;
import static trikita.anvil.BaseDSL.xml;
import static trikita.anvil.DSL.imageDrawable;
import static trikita.anvil.DSL.onClick;

/**
 * Created by peter on 8/7/16.
 */

public class MainViewListAdapter extends Recycler.Adapter {

    public static final int TYPE_TEST = 1;
    public static final int TYPE_COURSE = 2;

    private List<MainViewListItem> mItems;

    private Context context;

    public MainViewListAdapter(Context context, List<MainViewListItem> mItems) {
        this.context = context;
        this.mItems = mItems;
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
    public void view(RecyclerView.ViewHolder holder) {
        int position = holder.getAdapterPosition();
        switch (getItemViewType(position)) {
            case TYPE_COURSE:
                Course course = mItems.get(position).getCourse();
                xml(R.layout.course_item, () -> {
                    withId(R.id.courseCode, () -> {
                        text(course.getCode());
                    });
                });
                break;
            case TYPE_TEST:
                Test test = mItems.get(position).getTest();
                xml(R.layout.test_item, () -> {
                    margin(dip(16), dip(4));

                    withId(R.id.testName, () -> {
                        text(Helpers.getTestName(test));
                    });
                    withId(R.id.lecturerName, () -> {
                        text(test.getLecturerKey());
                    });
                    withId(R.id.questionCount, () -> {
                        text("42 q");
                    });
                    withId(R.id.testIcon, () -> {
                        // TODO: Fetch Course, then fetch course code instead of
                        // using test.courseKey as the text for the image
                        // it works for now cos in the sample data,
                        // courseCode and courseKey are the same thing
                        imageDrawable(
                                Helpers.getIcon(
                                        test.getKey(),
                                        test.courseKey,
                                        13 // fontsize in dp
                                )
                        );
                    });
                    onClick(view -> {
                        Intent intent = new Intent(context, TestOverviewActivity.class);
                        context.startActivity(intent);
                    });
                });
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
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
}
