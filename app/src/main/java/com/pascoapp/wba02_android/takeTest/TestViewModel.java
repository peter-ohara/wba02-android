package com.pascoapp.wba02_android.takeTest;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.pascoapp.wba02_android.State;
import com.pascoapp.wba02_android.firebasePojos.Programme;
import com.pascoapp.wba02_android.firebasePojos.Test;

import java.util.List;

import trikita.jedux.Action;
import trikita.jedux.Store;

/**
 * Created by peter on 7/23/16.
 */

public class TestViewModel {

    private Test test;

    private Store<Action, State> store;

    public TestViewModel(Test test) {
        this.test = test;
    }

    public String getCourseCode() {
        return test.getCourseKey().getCode();
    }

    public String getCourseName() {
        return test.getCourseKey().getName();
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
        String lecturerName = test.getLecturerKey().getFirstName();
        // generate color based on a key (same key returns the same color)
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(lecturerName);

        return TextDrawable.builder()
                .buildRound(
                        lecturerName.substring(0, 1).toUpperCase(),
                        color
                );
    }

    public String getLecturerName() {
        return test.getLecturerKey().getFirstName() + " " + test.getLecturerKey().getLastName();
    }

    public int getQuestionCount() {
        return test.getNumQuestions();
    }

    public Drawable getIcon() {
        String name = test.getCourseKey().getCode();
        // generate color based on a key (same key returns the same color), useful for list/grid views
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
        return test.getProgrammes();
    }

    public List<String> getInstructions() {
        return test.getInstructions();
    }

    @Override
    public String toString() {
        return "TestViewModel{" +
                "testKey=" + test +
                '}';
    }
}
