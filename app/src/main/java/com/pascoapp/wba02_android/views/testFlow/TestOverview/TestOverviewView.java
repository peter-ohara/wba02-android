package com.pascoapp.wba02_android.views.testFlow.TestOverview;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.pascoapp.wba02_android.App;
import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.State;
import com.pascoapp.wba02_android.services.courses.Course;
import com.pascoapp.wba02_android.services.lecturers.Lecturer;
import com.pascoapp.wba02_android.services.programmes.Programme;
import com.pascoapp.wba02_android.services.tests.Test;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import trikita.anvil.Anvil;
import trikita.anvil.RenderableAdapter;
import trikita.anvil.RenderableView;
import trikita.jedux.Action;
import trikita.jedux.Store;

import static trikita.anvil.BaseDSL.init;
import static trikita.anvil.BaseDSL.text;
import static trikita.anvil.BaseDSL.withId;
import static trikita.anvil.BaseDSL.xml;
import static trikita.anvil.DSL.adapter;
import static trikita.anvil.DSL.*;

/**
 * Created by peter on 8/4/16.
 */
public class TestOverviewView extends RenderableView {

    private Context context;
    private AppCompatActivity appCompatActivity;

    @Inject
    Store<Action, State> store;

    public TestOverviewView(Context context) {
        super(context);
        this.context = context;
        appCompatActivity = ((AppCompatActivity) context);
        App.getStoreComponent().inject(this);
    }

    @Override
    public void view() {
        Map<String, Object> resolvedData = store.getState().currentResolvedData();
        Test test = (Test) resolvedData.get("test");
        Course course = (Course) resolvedData.get("course");
        Lecturer lecturer = (Lecturer) resolvedData.get("lecturer");
        List<Programme> programmes = (List<Programme>) resolvedData.get("programmes");

        System.out.println("TestOverview :" + test);
        System.out.println("TestOverview :" + course);
        System.out.println("TestOverview :" + lecturer);
        System.out.println("TestOverview :" + programmes);
        xml(R.layout.activity_test_overview, () -> {

            withId(R.id.toolbar, () -> {
                init(() -> {
                    Toolbar toolbar = Anvil.currentView();
                    appCompatActivity.setSupportActionBar(toolbar);
                    //appCompatActivity.getSupportActionBar().setIcon(R.mipmap.ic_action_logo);
                    appCompatActivity.setTitle("");
                    appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                text(test.getDuration() + "hrs");
            });

            withId(R.id.snackbarPosition, () -> {
                init(() -> {
                    CoordinatorLayout coordinatorLayoutView = Anvil.currentView();
                });
            });

//            withId(R.id.lecturerIcon, () -> {
//                imageDrawable(Helpers.getIcon(lecturer.getKey(), lecturer.firstName));
//            });

            withId(R.id.lecturerName, () -> {
               text(lecturer.getFirstName() + " " + lecturer.getLastName());
            });

            withId(R.id.programmesList, () -> {
                size(MATCH, WRAP);
                adapter(RenderableAdapter.withItems(programmes, (i, programme) -> {
                    xml(R.layout.programme_item, () -> {
                       withId(R.id.programmeName, () -> {
                           text(programme.getName());
                       });
                    });
                }));
            });

            withId(R.id.instructionsList, () -> {
                size(MATCH, WRAP);
                adapter(RenderableAdapter.withItems(test.getInstructions(), (i, instruction) -> {
                    xml(R.layout.instruction_item, () -> {
                        withId(R.id.instruction, () -> {
                            text(instruction);
                        });
                    });
                }));
            });

            withId(R.id.bottomBar, () -> {
                text("Start");
                onClick(view -> {

                });
            });
        });
    }
}
