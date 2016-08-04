package com.pascoapp.wba02_android.takeTest;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.firebase.database.Query;
import com.pascoapp.wba02_android.App;
import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.State;
import com.pascoapp.wba02_android.dataFetching.Course;
import com.pascoapp.wba02_android.dataFetching.CourseDatabaseManager;
import com.pascoapp.wba02_android.dataFetching.Lecturer;
import com.pascoapp.wba02_android.dataFetching.LecturerDatabaseManager;
import com.pascoapp.wba02_android.dataFetching.Programme;
import com.pascoapp.wba02_android.dataFetching.ProgrammeDatabaseManager;
import com.pascoapp.wba02_android.dataFetching.School;
import com.pascoapp.wba02_android.dataFetching.SchoolDatabaseManager;
import com.pascoapp.wba02_android.dataFetching.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.inject.Inject;

import trikita.anvil.RenderableAdapter;
import trikita.anvil.RenderableView;
import trikita.anvil.Anvil;
import trikita.jedux.Action;
import trikita.jedux.Store;

import static trikita.anvil.BaseDSL.init;
import static trikita.anvil.BaseDSL.text;
import static trikita.anvil.BaseDSL.withId;
import static trikita.anvil.BaseDSL.xml;
import static trikita.anvil.DSL.imageView;
import static trikita.anvil.DSL.linearLayout;
import static trikita.anvil.DSL.*;
import static trikita.anvil.DSL.textView;
import static trikita.anvil.DSL.visibility;
/**
 * Created by peter on 8/4/16.
 */
public class TestOverviewComponent extends RenderableView {

    private Context context;
    private AppCompatActivity appCompatActivity;

    @Inject
    Store<Action, State> store;

    private Test test;
    private Lecturer lecturer;
    private Course course;
    private School school;
    private List<Programme> programmes;


    public TestOverviewComponent(Context context) {
        super(context);
        this.context = context;
        appCompatActivity = ((AppCompatActivity) context);
        App.getStoreComponent().inject(this);

        String testKey = store.getState().testOverviewComponent().test();
        test = store.getState().tests().get(testKey);
        lecturer = store.getState().lecturers().get(test.getLecturerKey());
        course = store.getState().courses().get(test.getCourseKey());
        Map<String, Programme> storedProgrammes = store.getState().programmes();
        // Filter by programmeKeys in test
        storedProgrammes.keySet().retainAll(test.getProgrammeKeys().keySet());
        programmes = (List<Programme>) storedProgrammes.values();
        school = store.getState().schools().get(test.getSchoolKey());
    }

    @Override
    public void view() {
        xml(R.layout.activity_take_test, () -> {

            withId(R.id.toolbar, () -> {
                init(() -> {
                    Toolbar toolbar = Anvil.currentView();
                    appCompatActivity.setSupportActionBar(toolbar);
                    appCompatActivity.getSupportActionBar().setIcon(R.mipmap.ic_action_logo);
                });
            });

            withId(R.id.courseCode, () -> {
                text(course.getCode());
            });

            withId(R.id.courseName, () -> {
                text(course.getName());
            });

            withId(R.id.testName, () -> {
                text(Helpers.getTestName(test));
            });

            withId(R.id.testDuration, () -> {
                text(test.getDuration()+ "hrs");
            });

            withId(R.id.snackbarPosition, () -> {
                init(() -> {
                    CoordinatorLayout coordinatorLayoutView = Anvil.currentView();
                });
            });

            withId(R.id.loading_indicator, () -> {
                visibility(store.getState().isFetching() ? VISIBLE : INVISIBLE);
            });

            withId(R.id.empty_view, () -> {
                visibility(store.getState().testOverviewComponent().test().equals(State.NOT_SET) ? VISIBLE : INVISIBLE);
            });

            withId(R.id.programmesList, () -> {
                adapter(RenderableAdapter.withItems(programmes, (i, programme) -> {
                    xml(R.layout.programme_item, () -> {
                       withId(R.id.programmeName, () -> {
                           text(programme.getName());
                       });
                    });
                }));
            });

            withId(R.id.instructionsList, () -> {
                adapter(RenderableAdapter.withItems(test.getInstructions(), (i, instruction) -> {
                    xml(R.layout.instruction_item, () -> {
                        withId(R.id.instruction, () -> {
                            text(instruction);
                        });
                    });
                }));
            });

            withId(R.id.bottomBar, () -> {
                text(store.getState().selectedCourse());
            });
        });
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        // fetchTest(test.getLecturerKey());
        LecturerDatabaseManager.fetchLecturer(store, test.getLecturerKey());
        CourseDatabaseManager.fetchCourse(store, test.getCourseKey());
        Query query = ProgrammeDatabaseManager.PROGRAMMES_REF.limitToFirst(5);
        ProgrammeDatabaseManager.fetchListOfProgrammes(store, query);
        SchoolDatabaseManager.fetchSchool(store, test.getSchoolKey());
    }
}
