package com.pascoapp.wba02_android.parseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Handles School logic
 */
@ParseClassName("School")
public class School extends ParseObject {

    public School() {
    }

    public String getName() {
        return getString("name");
    }

    public void setName(String name) {
        put("name", name);
    }

    public String getShortName() {
        return getString("shortName");
    }

    public void setShortName(String shortName) {
        put("shortName", shortName);
    }

    public static ParseQuery<School> getQuery() {
        ParseQuery<School> query = ParseQuery.getQuery(School.class);
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        return query;
    }

    @Override
    public String toString() {
        return getName();
    }
}
