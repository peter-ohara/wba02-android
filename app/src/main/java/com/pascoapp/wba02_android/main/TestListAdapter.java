package com.pascoapp.wba02_android.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pascoapp.wba02_android.parseSubClasses.Lecturer;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.Test;

import java.util.Calendar;
import java.util.List;

/**
 * Provides a binding from an Tests fetched from parse
 * to views that are displayed within a RecyclerView
 */
public class TestListAdapter extends RecyclerView.Adapter {

    private List<Test> mTests;
    private OnItemClickListener mItemClickListener;

    public TestListAdapter(List<Test> mTests) {
        this.mTests = mTests;
    }

    public class TestViewHolder extends RecyclerView.ViewHolder {

        public TextView titleView;
        public TextView lecturerView;
        public TextView durationView;

        public String testId;

        public TestViewHolder(View itemView) {
            super(itemView);

            titleView = (TextView) itemView.findViewById(R.id.test_item_title);
            lecturerView = (TextView) itemView.findViewById(R.id.test_item_lecturer);
            durationView = (TextView) itemView.findViewById(R.id.test_item_duration);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mItemClickListener != null) {
                        mItemClickListener.onItemClick(view, testId);
                    }
                }
            });
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v  = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.test_list_item_template, parent, false);
        TestViewHolder vh = new TestViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Test test = mTests.get(position);

        String year;
        String type;
        Lecturer lecturer;

        Calendar cal = Calendar.getInstance();
        cal.setTime(test.getYear());
        year = String.valueOf(cal.get(Calendar.YEAR));

        if (test.getType().equalsIgnoreCase("end")) {
            type = "End of Semester Exam";
        } else if (test.getType().equalsIgnoreCase("midsem")) {
            type = "Mid-Semester Exam";
        } else if (test.getType().equalsIgnoreCase("classtest")) {
            type = "Class Test";
        } else if (test.getType().equalsIgnoreCase("assignment")) {
            type = "Assignment";
        } else {
            type = "Unknown";
        }

        lecturer = test.getLecturer();

        ((TestViewHolder) holder).titleView.setText(year + " " + type);
        ((TestViewHolder) holder).lecturerView.setText(lecturer.getFullName());
        ((TestViewHolder) holder).durationView.setText(test.getDuration() + "hrs");

        ((TestViewHolder) holder).testId = test.getObjectId();
    }

    @Override
    public int getItemCount() {
        return mTests.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, String testId);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }
}
