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

    public String getCode() {
        return getString("code");
    }
    public void setCode(String code) {
        put("code", code);
    }

    public String getName() {
        return getString("name");
    }

    public int getLevel() {
        return getInt("level");
    }

    public void setName(String name) {
        put("name", name);
    }

    public static ParseQuery<Level> getQuery() {
        ParseQuery<Level> query = ParseQuery.getQuery(Level.class);
        query.include("level");
        return query;
    }
}
