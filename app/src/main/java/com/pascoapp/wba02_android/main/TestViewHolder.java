package com.pascoapp.wba02_android.main;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pascoapp.wba02_android.Actions;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.Screens;
import com.pascoapp.wba02_android.State;
import com.pascoapp.wba02_android.takeTest.TestViewModel;

import trikita.jedux.Action;
import trikita.jedux.Store;

/**
 * Created by peter on 7/25/16.
 */
public class TestViewHolder extends RecyclerView.ViewHolder {

    private final TextView testNameView;
    private final ImageView testIconView;
    private final TextView lecturerNameView;
    private final TextView questionCountView;
    private View itemView;

    private Store<Action, State> store;

    public TestViewHolder(View itemView, Store<Action, State> store) {
        super(itemView);
        this.itemView = itemView;
        this.store = store;

        testIconView = (ImageView) itemView.findViewById(R.id.testIcon);
        testNameView = (TextView) itemView.findViewById(R.id.testName);
        lecturerNameView = (TextView) itemView.findViewById(R.id.lecturerName);
        questionCountView = (TextView) itemView.findViewById(R.id.questionCount);
    }

    public void setTestViewModel(TestViewModel testViewModel) {
        testIconView.setImageDrawable(testViewModel.getIcon());
        testNameView.setText(testViewModel.getTestName());
        lecturerNameView.setText(testViewModel.getLecturerName());
        questionCountView.setText(testViewModel.getQuestionCount() + " q");

        itemView.setOnClickListener(view -> {
            store.dispatch(Actions.selectCourse(testViewModel.getTest().getKey()));
            store.dispatch(Actions.showScreen(Screens.TEST_OVERVIEW_SCREEN));
        });
    }
}
