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

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;


/**
 * Created by peter on 8/4/16.
 */

public class Helpers {

    private static FirebaseDatabase mDatabase;

    public static String getTestName(Test test) {
        Integer year = test.getYear();
        return year + " " + test.getTestType();
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
            mDatabase.setPersistenceEnabled(true);

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

    public static <V> V getOrDefault(Map<String, V> map, String key, V defaultValue) {
        if (map.containsKey(key)) {
            return map.get(key);
        } else {
            return defaultValue;
        }
    }

    public static void logTheError(String TAG, Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException exception = (HttpException) throwable;
            Response response = exception.response();
            Log.d(TAG, "exception.getMessage(): " + exception.getMessage());
            Log.d(TAG, "response.body(): " + response.body());
//                        Converter<ResponseBody, MyError> converter = new GsonConverterFactory()
//                                .responseBodyConverter(MyError.class, Annotation[0]);
//                        MyError error = converter.convert(response.errorBody());
        } else {
            Log.d(TAG, throwable.getMessage());
        }
    }
}
