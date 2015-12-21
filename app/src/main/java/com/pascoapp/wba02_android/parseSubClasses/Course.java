package com.pascoapp.wba02_android.parseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * ParseObject Subclass for Course. Handles Course logic
 */
@ParseClassName("Course")
public class Course extends ParseObject {
    public Course() {
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
    public void setName(String name) {
        put("name", name);
    }

    public ParseObject getProgramme() {
        return getParseObject("programme");
    }
    public void setProgramme(Programme programme) {
        put("programme", programme);
    }

    public static ParseQuery<Course> getQuery() {
        ParseQuery<Course> query = ParseQuery.getQuery(Course.class);
        query.include("programme");
        return query;
    }

}
