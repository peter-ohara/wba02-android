package com.pascoapp.wba02_android.parseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * ParseObject Subclass for School. Handles School logic
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

    public static ParseQuery<School> getQuery() {
        ParseQuery<School> query = ParseQuery.getQuery(School.class);
        return query;
    }
}
