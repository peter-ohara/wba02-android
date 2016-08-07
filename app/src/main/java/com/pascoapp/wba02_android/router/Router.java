package com.pascoapp.wba02_android.router;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.Query;
import com.pascoapp.wba02_android.Actions;
import com.pascoapp.wba02_android.MainActivity;
import com.pascoapp.wba02_android.State;
import com.pascoapp.wba02_android.services.courses.Courses;
import com.pascoapp.wba02_android.services.lecturers.Lecturers;
import com.pascoapp.wba02_android.services.programmes.Programmes;
import com.pascoapp.wba02_android.services.tests.Test;
import com.pascoapp.wba02_android.services.tests.Tests;
import com.pascoapp.wba02_android.main.MainComponent;
import com.pascoapp.wba02_android.takeTest.TestOverview.TestOverviewComponent;

import org.jdeferred.Promise;
import org.jdeferred.android.AndroidDeferredManager;

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
                resolveMainScreenData(store);
                return;
            }
            case TEST_OVERVIEW_SCREEN: {
                resolveTestOverviewData(store);
                return;
            }
        }
    }

    private void resolveMainScreenData(Store<Action, State> store) {
        System.out.println("Router: " + "In MAIN_SCREEN");

        // Resolve dependencies for main screen
        // then show main Screen
        AndroidDeferredManager dm = new AndroidDeferredManager();
        Query coursesQuery = Courses.COURSES_REF.limitToFirst(5);
        Promise coursesPromise = Courses.fetchListOfCourses(store, coursesQuery);
        Query testsQuery = Tests.TESTS_REF.limitToFirst(5);
        Promise testsPromise = Tests.fetchListOfTests(store, testsQuery);

        dm.when(coursesPromise, testsPromise)
                .done(results -> {
                    Map<String, Object> resolutions = new HashMap<>();
                    resolutions.put("courses", results.get(0).getResult());
                    resolutions.put("tests", results.get(1).getResult());
                    System.out.println("Router: " + resolutions);
                    store.dispatch(RouteActions.setRouteResolutions(resolutions));
                    System.out.println("Router: " + store.getState().currentResolvedData());
                    backstack.navigate(new MainComponent(activity));
                    System.out.println("Router: " + "backstack navigated to MainComponent");
                })
                .fail(result -> {
                    Toast.makeText(activity, result.toString(), Toast.LENGTH_SHORT).show();
                    System.out.println("Router: " + "Failed to resolve courses or tests");
                });
    }

    private void resolveTestOverviewData(Store<Action, State> store) {
        // Resolve dependencies for test overview screen
        // then show testoverview screen
        Test test = (Test) store.getState().currentRoute().getPayload();
        ProgressDialog progress = new ProgressDialog(activity);
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(true);
        progress.setProgressNumberFormat(null);
        progress.setProgressPercentFormat(null);
        progress.show();

        AndroidDeferredManager dm = new AndroidDeferredManager();
        Promise coursePromise = Courses.fetchCourse(store, test.getCourseKey());
        Promise lecturerPromise = Lecturers.fetchLecturer(store, test.getLecturerKey());
        Query query = Programmes.PROGRAMMES_REF.limitToFirst(5);
        Promise programmesPromise = Programmes.fetchListOfProgrammes(store, query);

        dm.when(coursePromise, lecturerPromise, programmesPromise)
                .done(results -> {
                    Map<String, Object> resolutions = new HashMap<>();
                    resolutions.put("test", test);
                    resolutions.put("course", results.get(0).getResult());
                    resolutions.put("lecturer", results.get(1).getResult());
                    resolutions.put("programmes", results.get(2).getResult());
                    store.dispatch(RouteActions.setRouteResolutions(resolutions));

                    backstack.navigate(new TestOverviewComponent(activity));
                    progress.dismiss();

                })
                .fail(result -> {
                    progress.dismiss();
                    Toast.makeText(activity, result.toString(), Toast.LENGTH_SHORT).show();
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