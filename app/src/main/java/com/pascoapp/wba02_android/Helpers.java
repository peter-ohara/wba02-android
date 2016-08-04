package com.pascoapp.wba02_android;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.pascoapp.wba02_android.dataFetching.Course;
import com.pascoapp.wba02_android.dataFetching.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by peter on 8/4/16.
 */

public class Helpers {

    public static String getTestName(Test test) {
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

    public Drawable getIcon(Course course) {
        String text = course.getCode();
        // generate color based on a nodeKey (same nodeKey returns the same color), useful for list/grid views
        ColorGenerator generator = ColorGenerator.DEFAULT;
        int color = generator.getColor(text);

        int dp = (int) (13 * Resources.getSystem().getDisplayMetrics().density);

        return TextDrawable.builder()
                .beginConfig()
                .fontSize(dp) /* size in px */
                .toUpperCase()
                .endConfig()
                .buildRound(
                        text.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)")[0] +
                                "\n" + text.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)")[1],
                        color
                );
    }



}
