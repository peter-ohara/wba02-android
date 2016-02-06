package com.pascoapp.wba02_android.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.Course;

import java.util.ArrayList;

/**
 * Created by peter on 2/6/16.
 */
public class SelectCourseSpinnerAdapter extends BaseAdapter {

    private ArrayList<Course> mCourses = new ArrayList<>();

    public void clear() {
        mCourses.clear();
    }

    public void addCourse(int position, Course course) {
        mCourses.add(position, course);
    }

    public void addCourses(ArrayList<Course> courses) {
        mCourses.addAll(courses);
    }

    @Override
    public int getCount() {
        return mCourses.size();
    }

    @Override
    public Object getItem(int position) {
        return mCourses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null || !convertView.getTag().toString().equals("DROPDOWN")) {
            convertView = LayoutInflater
                    .from(parent.getContext()).inflate(R.layout.toolbar_spinner_item_dropdown, parent, false);
            convertView.setTag("DROPDOWN");
        }

        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(getTitle(position));

        return convertView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null || !convertView.getTag().toString().equals("NON_DROPDOWN")) {
            convertView = LayoutInflater
                    .from(parent.getContext()).inflate(R.layout.
                    toolbar_spinner_item_actionbar, parent, false);
            convertView.setTag("NON_DROPDOWN");
        }
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        textView.setText(getTitle(position));
        return convertView;
    }

    private String getTitle(int position) {
        return position >= 0 && position < mCourses.size() ? mCourses.get(position).getCode() + " " + mCourses.get(position).getName() : "";
    }
}
