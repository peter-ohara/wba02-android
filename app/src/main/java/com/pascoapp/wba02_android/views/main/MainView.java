package com.pascoapp.wba02_android.views.main;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.pascoapp.wba02_android.App;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.State;
import com.pascoapp.wba02_android.router.Router;
import com.pascoapp.wba02_android.services.courses.Course;
import com.pascoapp.wba02_android.services.tests.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import trikita.anvil.Anvil;
import trikita.anvil.RenderableView;
import trikita.anvil.recyclerview.Recycler;
import trikita.jedux.Action;
import trikita.jedux.Store;

import static trikita.anvil.BaseDSL.init;
import static trikita.anvil.BaseDSL.text;
import static trikita.anvil.BaseDSL.withId;
import static trikita.anvil.BaseDSL.xml;
import static trikita.anvil.DSL.visibility;

/**
 * Created by peter on 7/29/16.
 */

public class MainView extends RenderableView {

    private Context context;
    private AppCompatActivity appCompatActivity;

    @Inject
    Store<Action, State> store;


    public MainView(Context context) {
        super(context);
        this.context = context;
        appCompatActivity = ((AppCompatActivity) context);
        App.getStoreComponent().inject(this);
    }

    @Override
    public void view() {
        if (store.getState().currentRoute().getScreen() != Router.Screens.MAIN_SCREEN) {
            return;
        }

        Map<String, Object> resolvedData = store.getState().currentResolvedData();
        List<Course> courses = (List<Course>) resolvedData.get("courses");
        List<Test> tests = (List<Test>) resolvedData.get("tests");

        System.out.println("Main Screen: " + courses);
        System.out.println("Main Screen: " + tests);

        List<MainViewListItem> screenItems = new ArrayList<>();
        for (Course course : courses) {
            screenItems.add(new MainViewListItem(course));
        }
        for (Test test : tests) {
            screenItems.add(new MainViewListItem(test));
        }
        System.out.println("Main Screen: " + screenItems);


        xml(R.layout.main_view, () -> {
            withId(R.id.toolbar, () -> {
                init(() -> {
                    Toolbar toolbar = Anvil.currentView();
                    appCompatActivity.setSupportActionBar(toolbar);
                    appCompatActivity.getSupportActionBar().setIcon(R.mipmap.ic_action_logo);
                });
            });

            withId(R.id.snackbarPosition, () -> {
                init(() -> {
                    CoordinatorLayout coordinatorLayoutView = Anvil.currentView();
                });
            });

            withId(R.id.loading_indicator, () -> {
                visibility(store.getState().isFetching() ? VISIBLE : INVISIBLE);
            });


            withId(R.id.coursesList, () -> {
                Recycler.layoutManager(new LinearLayoutManager(getContext(),
                        LinearLayoutManager.VERTICAL, false));
                Recycler.hasFixedSize(true);

                Recycler.adapter(new MainViewListAdapter(context, screenItems));
            });

            withId(R.id.bottomBar, () -> {
                text("Choose School");
            });
        });
    }
}
