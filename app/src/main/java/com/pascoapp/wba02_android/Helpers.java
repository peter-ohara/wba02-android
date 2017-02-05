package com.pascoapp.wba02_android;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import java.util.Map;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;


/**
 * Created by peter on 8/4/16.
 */

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

    public static int dip(int pixels) {
        return (int) (pixels * Resources.getSystem().getDisplayMetrics().density);
    }

}
