package com.pascoapp.wba02_android.views.main;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.google.firebase.database.Query;
import com.pascoapp.wba02_android.App;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.State;
import com.pascoapp.wba02_android.router.RouteActions;
import com.pascoapp.wba02_android.router.Router;
import com.pascoapp.wba02_android.services.courses.Course;
import com.pascoapp.wba02_android.services.courses.Courses;
import com.pascoapp.wba02_android.services.tests.Test;
import com.pascoapp.wba02_android.services.tests.Tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
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

    private final MainViewListAdapter mainViewListAdapter;


    @Inject
    Store<Action, State> store;
    ;


    public MainView(Context context) {
        super(context);
        this.context = context;
        appCompatActivity = ((AppCompatActivity) context);
        mainViewListAdapter = new MainViewListAdapter(context, new ArrayList<>());
        App.getStoreComponent().inject(this);
        resolveMainScreenData(store);
    }

    @Override
    public void view() {
        if (store.getState().currentRoute().getScreen() != Router.Screens.MAIN_SCREEN) {
            return;
        }

        Map<String, Object> resolvedData = store.getState().currentResolvedData();
        List<MainViewListItem> items = (List<MainViewListItem>) resolvedData.get("items");
        if (items != null) {
            mainViewListAdapter.clear();
            mainViewListAdapter.addAll(items);
        }

        xml(R.layout.activity_main, () -> {
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

                Recycler.adapter(mainViewListAdapter);
            });

            withId(R.id.bottomBar, () -> {
                text("Choose School");
            });
        });
    }


    private void resolveMainScreenData(Store<Action, State> store) {
        System.out.println("Router: " + "In MAIN_SCREEN");

        Query coursesQuery = Courses.COURSES_REF.limitToFirst(3);
        Courses.fetchListOfCourses(store, coursesQuery)
                .concatMap(courses -> Observable.from(courses))
                .concatMap(course -> {
                    Query testsQuery = Tests.TESTS_REF.orderByChild("courseKey").equalTo(course.getKey());
                    return Tests.fetchListOfTests(store, testsQuery);
                })
                .concatMap(tests -> Observable.from(tests))
                .groupBy(Test::getCourseKey, test -> test)
                .concatMap(stringTestGroupedObservable -> {
                    Course course = store.getState().courses()
                            .get(stringTestGroupedObservable.getKey());
                    MainViewListItem mainViewListItem = new MainViewListItem(course);

                    return Observable.merge(Observable.just(mainViewListItem),
                            stringTestGroupedObservable.flatMap(test -> Observable.just(new MainViewListItem(test))));
                })
                .toList()
                .subscribe(mainViewListItems -> {
                    Map<String, Object> resolvedData = new HashMap<>();
                    resolvedData.put("items", mainViewListItems);
                    store.dispatch(RouteActions.setRouteResolutions(resolvedData));
                    System.out.println("MainScreen :" + mainViewListItems);
                });

    }
}
