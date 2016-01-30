package com.pascoapp.wba02_android.parseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Handles Programme logic
 */
@ParseClassName("Programme")
public class Programme extends ParseObject {

    public Programme() {
    }

    public String getName() {
        return getString("name");
    }
    public void setName(String name) {
        put("name", name);
    }

    public School getSchool() {
        return (School) getParseObject("school");
    }
    public void setSchool(School school) {
        put("school", school);
    }

    public static ParseQuery<Programme> getQuery() {
        ParseQuery<Programme> query = ParseQuery.getQuery(Programme.class);
        query.include("school");
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        return query;
    }

    @Override
    public String toString() {
        return getName() + " - " + getSchool().getShortName();
    }
}
