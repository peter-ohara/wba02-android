package com.pascoapp.wba02_android;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;


public class Helpers {

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

    private static int dip(int pixels) {
        return (int) (pixels * Resources.getSystem().getDisplayMetrics().density);
    }

}
