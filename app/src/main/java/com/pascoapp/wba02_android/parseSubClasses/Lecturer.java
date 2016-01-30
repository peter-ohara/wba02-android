package com.pascoapp.wba02_android.parseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Handles Lecturer logic
 */
@ParseClassName("Lecturer")
public class Lecturer extends ParseObject {

    public Lecturer() {
    }

    public String getFirstName() {
        return getString("firstName");
    }
    public void setFirstName(String firstName) {
        put("firstName", firstName);
    }

    public String getLastName() {
        return getString("lastName");
    }
    public void setLastName(String lastName) {
        put("lastName", lastName);
    }

    public String getFullName() {
        return getString("firstName") + " " + getString("lastName");
    }
    public void setName(String firstName, String lastName) {
        put("firstName", firstName);
        put("lastName", lastName);
    }

    public static ParseQuery<Lecturer> getQuery() {
        ParseQuery<Lecturer> query = ParseQuery.getQuery(Lecturer.class);
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_THEN_NETWORK);
        return query;
    }

    @Override
    public String toString() {
        return getFullName();
    }
}
