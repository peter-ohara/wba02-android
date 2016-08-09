package com.pascoapp.wba02_android;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.pascoapp.wba02_android.services.FirebaseItem;
import com.pascoapp.wba02_android.services.courses.Course;
import com.pascoapp.wba02_android.services.tests.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static trikita.anvil.BaseDSL.dip;

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

    public static Drawable getIcon(String colorKey, String text) {
        // generate color based on a nodeKey (same nodeKey returns the same color), useful for list/grid views
        ColorGenerator generator = ColorGenerator.DEFAULT;
        int color = generator.getColor(colorKey);

        return TextDrawable.builder()
                .beginConfig()
                .fontSize(dip(13)) /* size in px */
                .toUpperCase()
                .endConfig()
                .buildRound(
                        text.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)")[0] +
                                "\n" + text.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)")[1],
                        color
                );
    }

    public static <T extends FirebaseItem> Map<String, T> convertToMap(List<T> items) {
        Map<String, T> map = new HashMap<>();
        for (T item : items) {
            map.put(item.getKey(), item);
        }
        return map;
    }
}
