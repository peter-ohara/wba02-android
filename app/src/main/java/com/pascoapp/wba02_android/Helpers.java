package com.pascoapp.wba02_android;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.pascoapp.wba02_android.services.tests.Test;


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

    public static Drawable getIcon(String colorKey, String text, int textSize) {
        // generate color based on a nodeKey (same nodeKey returns the same color), useful for list/grid views
        ColorGenerator generator = ColorGenerator.DEFAULT;
        int color = generator.getColor(colorKey);


        return TextDrawable.builder()
                .beginConfig()
                .fontSize(dip(textSize)) /* size in px */
                .toUpperCase()
                .endConfig()
                .buildRound(text, color);
    }

    public static int dip(int pixels) {
        return (int) (pixels * Resources.getSystem().getDisplayMetrics().density);
    }
}
