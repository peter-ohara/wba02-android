package com.pascoapp.wba02_android.Programme;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.pascoapp.wba02_android.School.School;

/**
 * ParseObject Subclass for Programme. Handles Programme logic
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

    public ParseObject getSchool() {
        return getParseObject("school");
    }
    public void setSchool(School school) {
        put("school", school);
    }

    public static ParseQuery<Programme> getQuery() {
        return ParseQuery.getQuery(Programme.class);
    }
}
