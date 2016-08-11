package com.pascoapp.wba02_android.router;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.Query;
import com.pascoapp.wba02_android.Actions;
import com.pascoapp.wba02_android.views.main.MainActivity;
import com.pascoapp.wba02_android.State;
import com.pascoapp.wba02_android.services.courses.Courses;
import com.pascoapp.wba02_android.services.lecturers.Lecturers;
import com.pascoapp.wba02_android.services.programmes.Programmes;
import com.pascoapp.wba02_android.services.tests.Test;
import com.pascoapp.wba02_android.views.takeTest.TestOverview.TestOverviewActivity;

import java.util.HashMap;
import java.util.Map;

import trikita.jedux.Action;
import trikita.jedux.Store;

import static com.pascoapp.wba02_android.Actions.ActionType.SHOW_SCREEN;

/**
 * Created by peter on 7/30/16.
 */
public class Router implements Store.Middleware<Action, State> {

    @Override
    public void dispatch(Store<Action, State> store, Action action, Store.NextDispatcher<Action> next) {
        next.dispatch(action);

        Action actionCast = (Action) action;
        Actions.ActionType type = (Actions.ActionType) actionCast.type;
        if (type != SHOW_SCREEN ) { return; }

        Route route = (Route) actionCast.value;
        System.out.println("Router: " + route);

        switch (route.getScreen()) {
            case MAIN_SCREEN: {
                // resolveMainScreenData(store);
                //backstack.navigate(new MainView(activity));
                return;
            }
            case TEST_OVERVIEW_SCREEN: {
                resolveTestOverviewData(store);
                return;
            }
        }
    }

    private void resolveTestOverviewData(Store<Action, State> store) {
        // Resolve dependencies for test overview screen
        // then show testoverview screen
        Test test = (Test) store.getState().currentRoute().getPayload();
        Map<String, Object> resolvedData = new HashMap<>();
        resolvedData.put("test", test);
        Courses.fetchCourse(store, test.getCourseKey())
                .flatMap(course -> {
                    resolvedData.put("course", course);
                    return Lecturers.fetchLecturer(store, test.getLecturerKey());
                })
                .flatMap(lecturer -> {
                    resolvedData.put("lecturer", lecturer);
                    Query query = Programmes.PROGRAMMES_REF.limitToFirst(5);
                    return Programmes.fetchListOfProgrammes(store, query);
                })
                .subscribe(programmes -> {
                    resolvedData.put("programmes", programmes);
                    store.dispatch(RouteActions.setRouteResolutions(resolvedData));

                    activity.startActivity(new Intent(activity, TestOverviewActivity.class));
                    //backstack.navigate(new TestOverviewView(activity));
                }, firebaseException -> {
                    Toast.makeText(activity, firebaseException.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }



    public enum Screens {
        NOT_SET,
        MAIN_SCREEN,
        TEST_OVERVIEW_SCREEN
    }

    private AppCompatActivity activity;
    private Backstack backstack;

    public void init(MainActivity mainActivity) {
        this.activity = mainActivity;
        backstack = new Backstack(activity, toView -> activity.setContentView(toView));
    }


    public void navigate(View view) { backstack.navigate(view); }
    public int size() { return backstack.size(); }

    // Fix navigation back error
    public boolean back() { return backstack.back(); }
    public void load(Bundle bundle) { backstack.load(bundle); }
    public void save(Bundle bundle) { backstack.save(bundle); }

}