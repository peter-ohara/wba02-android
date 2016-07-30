package com.pascoapp.wba02_android;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.pascoapp.wba02_android.takeTest.TestViewModel;

/**
 * Created by peter on 7/25/16.
 */
public class TestViewHolder extends RecyclerView.ViewHolder {

    private final TextView testNameView;
    private View itemView;

    private TestViewModel testViewModel;
    private CourseViewModel courseViewModel;

    public TestViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;

        testNameView = (TextView) itemView.findViewById(R.id.testName);
    }

    public void setTestViewModel(TestViewModel testViewModel) {
        this.testViewModel = testViewModel;
        testNameView.setText(testViewModel.getTestName());
    }

    public void setCourseViewModel(CourseViewModel courseViewModel) {
        this.courseViewModel = courseViewModel;
    }
}
