package com.pascoapp.wba02_android.views.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.views.takeTest.TestOverviewActivity;

import java.util.List;


public class MainViewListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String LOG_TAG = MainViewListAdapter.class.getSimpleName();

    public static final int TYPE_TEST = 1;
    public static final int TYPE_COURSE = 2;

    private List<JsonObject> mItems;

    private Context context;

    public MainViewListAdapter(Context context, List<JsonObject> mItems) {
        this.context = context;
        this.mItems = mItems;
    }

    @Override
    public int getItemViewType(int position) {
        JsonObject item = mItems.get(position);

        if (item.has("type")
                && item.get("type").getAsString().equals("header")) {
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
                JsonObject header = mItems.get(position);
                String title = header.get("title").getAsString();
                ((CourseViewHolder) holder).courseCode.setText(title);
                ((CourseViewHolder) holder).moreButton.setOnClickListener(view -> {
                    Toast.makeText(context, "More tests coming soon!", Toast.LENGTH_SHORT).show();
                });
                break;
            }
            case TYPE_TEST: {
                JsonObject test =  mItems.get(position);

                Integer testId = test.get("id").getAsInt();
                String courseCode = test.get("course_code").getAsString();
                String testName = test.get("name").getAsString();
                String questionCount = test.get("question_count").getAsString();

                ((TestViewHolder) holder).testIcon.setImageDrawable(
                        Helpers.getIcon(
                                testId.toString(),
                                courseCode,
                                14 // fontSize in sp
                        )
                );
                ((TestViewHolder) holder).testName.setText(testName);
                ((TestViewHolder) holder).questionCount.setText(questionCount);

                ((TestViewHolder) holder).itemView.setOnClickListener(view -> {
                    Intent intent = new Intent(context, TestOverviewActivity.class);
                    intent.putExtra(TestOverviewActivity.EXTRA_TEST_ID, testId);
                    context.startActivity(intent);
                });
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}
