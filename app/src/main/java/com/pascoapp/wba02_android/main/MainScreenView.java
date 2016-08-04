package com.pascoapp.wba02_android.main;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pascoapp.wba02_android.Actions;
import com.pascoapp.wba02_android.App;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.Screens;
import com.pascoapp.wba02_android.State;
import com.pascoapp.wba02_android.dataFetching.Course;
import com.pascoapp.wba02_android.dataFetching.Test;
import com.pascoapp.wba02_android.takeTest.TakeTestActivity;
import com.pascoapp.wba02_android.takeTest.TestViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import trikita.anvil.Anvil;
import trikita.anvil.RenderableView;
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

public class MainScreenView extends RenderableView {

    private Context context;
    private AppCompatActivity appCompatActivity;

    @Inject
    Store<Action, State> store;


    public MainScreenView(Context context) {
        super(context);
        this.context = context;
        appCompatActivity = ((AppCompatActivity) context);
        App.getStoreComponent().inject(this);
    }

    @Override
    public void view() {
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
                visibility(store.getState().mainScreen().isFetching() ? VISIBLE : INVISIBLE);
            });

            withId(R.id.empty_view, () -> {
                visibility(store.getState().mainScreen().items().size() == 0 ? VISIBLE : INVISIBLE);
            });

            withId(R.id.coursesList, () -> {
                init(() -> {
                    RecyclerView recyclerView = Anvil.currentView();
                    recyclerView.setHasFixedSize(true);

                    // Use a linear layout manager
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));

                    BoughtCoursesAdapter boughtCoursesAdapter =
                            new BoughtCoursesAdapter(context, store);

                    // Set the adapter object to the RecyclerView
                    recyclerView.setAdapter(boughtCoursesAdapter);

                    store.subscribe(() -> {
                        boughtCoursesAdapter.clear();
                        boughtCoursesAdapter.addAll(getScreenItems());
                        boughtCoursesAdapter.notifyDataSetChanged();

                        if (store.getState().currentScreen()
                                .equals(Screens.TEST_OVERVIEW_SCREEN)) {
                            appCompatActivity
                                    .startActivity(new Intent(appCompatActivity,
                                            TakeTestActivity.class));

                        }
                    });

                });
                visibility(store.getState().mainScreen().items().size() > 0 ? VISIBLE : INVISIBLE);
            });

            withId(R.id.bottomBar, () -> {
                text(store.getState().selectedCourse());
            });
        });
    }

    // Equivalent of React componentDidMount()
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        fetchBoughtCourses(store);
    }

    private List<MainListItem> getScreenItems() {
        return Stream.of(store.getState().mainScreen().items())
                .flatMap(item -> Stream.of(getExpandableListSection(item)) )
                .collect(Collectors.toList());

//        Stream.of(store.getState().mainScreen().items())
//                .map(item -> item.getValue().add(item.getKey()) )
//                .collect(Collectors.toList());
//        return null;
    }

    @NonNull
    private List<MainListItem> getExpandableListSection(Map.Entry<String, List<String>> item) {
        List<MainListItem> screenItems = new ArrayList<>();

        MainListItem courseHeader = getMainListItemFromCourseKey(item);

        List<MainListItem> tests = Stream.of(item.getValue())
                .map(testKey -> getMainListItemFromTestKey(testKey))
                .collect(Collectors.toList());

        screenItems.add(courseHeader);
        screenItems.addAll(tests);
        return screenItems;
    }

    @NonNull
    private MainListItem getMainListItemFromTestKey(String testKey) {
        Test test = store.getState().tests().get(testKey);
        TestViewModel testViewModel = new TestViewModel(test, store);
        return new MainListItem(testViewModel);
    }

    @NonNull
    private MainListItem getMainListItemFromCourseKey(Map.Entry<String, List<String>> item) {
        Course course = store.getState().courses().get(item.getKey());
        CourseViewModel courseViewModel = new CourseViewModel(course);
        return new MainListItem(courseViewModel);
    }

    public static void fetchBoughtCourses(Store<Action, State> store) {
        store.dispatch(Actions.requestCourses(""));

        DatabaseReference coursesRef = FirebaseDatabase.getInstance().getReference().child("courses");
        Query coursesQuery = coursesRef.limitToFirst(9);
        coursesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Course> courses = new ArrayList<>();
                for (DataSnapshot courseSnapshot: dataSnapshot.getChildren()) {
                    Course course = courseSnapshot.getValue(Course.class);
                    course.key = courseSnapshot.getKey();
                    courses.add(course);
                }
                store.dispatch(Actions.receiveCoursesWithSuccess(courses));
                for (Course course : courses) {
                    fetchTestsForCourse(store, course.getKey());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                store.dispatch(Actions.receiveCoursesWithFailure(databaseError.getMessage()));
            }
        });
    }

    public static void fetchTestsForCourse(Store<Action, State> store, String courseKey) {
        store.dispatch(Actions.requestTests(courseKey));
        DatabaseReference testsRef = FirebaseDatabase.getInstance().getReference().child("tests");
        // TODO: Uncomment the line below then fix the bug that ensues
        // Query testQuery = testsRef.orderByChild("courseKey").equalTo(courseKey);
        Query testQuery = testsRef.orderByChild("courseKey");
        testQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Test> tests = new ArrayList<>();
                for (DataSnapshot testSnapshot: dataSnapshot.getChildren()) {
                    Test test = testSnapshot.getValue(Test.class);
                    test.key = testSnapshot.getKey();
                    tests.add(test);
                }
                store.dispatch(Actions.receiveTestsWithSuccess(tests));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                store.dispatch(Actions.receiveTestsWithFailure(databaseError.getMessage()));
            }
        });
    }

}
