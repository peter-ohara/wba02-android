package com.pascoapp.wba02_android;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.pascoapp.wba02_android.services.tests.Test;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Created by peter on 8/4/16.
 */

public class Helpers {

    private static FirebaseDatabase mDatabase;

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

    public static <K, V extends Comparable<? super V>> Map<K, V> sortMapByValue(Map<K, V> map ) {
        List<Map.Entry<K, V>> list = new LinkedList<>( map.entrySet() );
        Collections.sort( list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare( Map.Entry<K, V> o1, Map.Entry<K, V> o2 )
            {
                return ( o1.getValue() ).compareTo( o2.getValue() );
            }
        } );

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put( entry.getKey(), entry.getValue() );
        }
        return result;
    }

    public static FirebaseDatabase getDatabaseInstance() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();

            if (BuildConfig.DEBUG) {
                // do something for a debug build
                Log.d("Helpers", "Running in debug mode, offline persistence is disabled");
            } else {
                mDatabase.setPersistenceEnabled(true);
            }

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            mDatabase.getReference().child("users").child(user.getUid()).keepSynced(true);
        }
        return mDatabase;
    }

    public static String createFirebasePath(String... paths) {
        String newPath = "";
        for (String path : paths) {
            newPath += "/" + path;
        }
        return newPath;
    }

}
