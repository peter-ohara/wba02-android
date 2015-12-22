package com.pascoapp.wba02_android.parseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * ParseObject Subclass for Level. Handles Level logic
 */
@ParseClassName("Level")
public class Level extends ParseObject {

    public Level() {
    }

    public String getName() {
        return getString("name");
    }

    public int getLevelIndex() {
        return getInt("index");
    }

    public static ParseQuery<Level> getQuery() {
        ParseQuery<Level> query = ParseQuery.getQuery(Level.class);
        //query.include("parent");
        return query;
    }
}
