package com.pascoapp.wba02_android.main;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pascoapp.wba02_android.App;
import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.router.Route;
import com.pascoapp.wba02_android.State;
import com.pascoapp.wba02_android.router.RouteActions;
import com.pascoapp.wba02_android.services.courses.Course;
import com.pascoapp.wba02_android.services.tests.Test;
import com.pascoapp.wba02_android.router.Router;

import javax.inject.Inject;

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

    @Inject
    Store<Action, State> store;
;

    public TestViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        App.getStoreComponent().inject(this);

        testIconView = (ImageView) itemView.findViewById(R.id.testIcon);
        testNameView = (TextView) itemView.findViewById(R.id.testName);
        lecturerNameView = (TextView) itemView.findViewById(R.id.lecturerName);
        questionCountView = (TextView) itemView.findViewById(R.id.questionCount);
    }

    public void setTest(Test test) {
        Course course = store.getState().courses().get(test.getCourseKey());

        testIconView.setImageDrawable(Helpers.getCourseIcon(course));
        testNameView.setText(Helpers.getTestName(test));
//        lecturerNameView.setText(testViewModel.getLecturerName());
//        questionCountView.setText(testViewModel.getQuestionCount() + " q");

        itemView.setOnClickListener(view -> {
            Route route = new Route(Router.Screens.TEST_OVERVIEW_SCREEN, test);
            store.dispatch(RouteActions.showScreen(route));
        });
    }
}
