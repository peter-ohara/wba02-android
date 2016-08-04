package com.pascoapp.wba02_android.takeTest;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.pascoapp.wba02_android.State;
import com.pascoapp.wba02_android.dataFetching.Programme;
import com.pascoapp.wba02_android.dataFetching.Test;

import java.util.ArrayList;
import java.util.List;

import trikita.jedux.Action;
import trikita.jedux.Store;

/**
 * Created by peter on 7/23/16.
 */

public class TestViewModel {

    private Test test;

    private Store<Action, State> store;

    public TestViewModel(Test test, Store<Action, State> store) {
        this.test = test;
        this.store = store;
    }

    public Test getTest() {
        return test;
    }

    public String getCourseCode() {
        return store.getState().courses().get(test.getCourseKey()).getCode();
    }

    public String getCourseName() {
        return store.getState().courses().get(test.getCourseKey()).getName();
    }

    public String getTestName() {
        Long year = test.getYear();
        String type;
        if (test.getType().equalsIgnoreCase("endOfSem")) {
            type = "End of Semester Exam";
        } else if (test.getType().equalsIgnoreCase("midSem")) {
            type = "Mid-Semester Exam";
        } else if (test.getType().equalsIgnoreCase("classTest")) {
            type = "Class Test";
        } else if (test.getType().equalsIgnoreCase("assignment")) {
            type = "Assignment";
        } else {
            type = "Unknown";
        }
        return year + " " + type;
    }


    public String getTestDuration() {
        return test.getDuration() + "hrs";
    }

    public Drawable getLecturerIcon() {
        // TODO: Fetch Actual lecturer name
        String name = getTestName();
        // generate color based on a nodeKey (same nodeKey returns the same color), useful for list/grid views
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(name);

        return TextDrawable.builder()
                .buildRound(
                        name.substring(0, 1).toUpperCase(),
                        color
                );
    }


    public String getLecturerName() {
        return "Prof. Sesi Gob3";
    }

    public int getQuestionCount() {
        return 42;
    }

    public Drawable getIcon() {
        String name = store.getState().courses().get(test.getCourseKey()).getCode();
        // generate color based on a nodeKey (same nodeKey returns the same color), useful for list/grid views
        ColorGenerator generator = ColorGenerator.DEFAULT;
        int color = generator.getColor(name);

        int dp = (int) (13 * Resources.getSystem().getDisplayMetrics().density);

        return TextDrawable.builder()
                .beginConfig()
                .fontSize(dp) /* size in px */
                .toUpperCase()
                .endConfig()
                .buildRound(
                        name.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)")[0] +
                                "\n" + name.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)")[1],
                        color
                );
    }

    public List<Programme> getProgrammes() {
        List<Programme> programmes = new ArrayList<>();
        programmes.add(new Programme("Computer Science [B.Sc.]", "knust"));
        programmes.add(new Programme("Computer Engineering [B.Sc.]", "knust"));
        programmes.add(new Programme("Information Technology [B.Sc.]", "knust"));
        return programmes;
    }

    public List<String> getInstructions() {
        return test.getInstructions();
    }

    @Override
    public String toString() {
        return "TestViewModel{" +
                "test=" + test +
                '}';
    }
}
