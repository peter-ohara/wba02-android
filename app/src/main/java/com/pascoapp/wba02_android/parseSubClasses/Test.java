package com.pascoapp.wba02_android.parseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Date;

/**
 * Handles Test logic
 */
@ParseClassName("Test")
public class Test extends ParseObject{

    public Test() {
    }

    public Lecturer getLecturer() {
        return (Lecturer) getParseObject("lecturer");
    }
    public void setLecturer(ParseObject lecturer) {
        put("lecturer", lecturer);
    }

    public Date getYear() {
        return getDate("year");
    }
    public void setYear(Date year) {
        put("year", year);
    }

    public String getType() {
        return getString("type");
    }
    public void setType(String type) {
        put("type", type);
    }

    public Number getDuration() {
        return getNumber("duration");
    }
    public void setDuration(Number duration) {
        put("duration", duration);
    }

    public String getInstructions() {
        return getString("instructions");
    }
    public void setInstructions(String instructions) {
        put("instructions", instructions);
    }

    public Course getCourse() {
        return (Course) getParseObject("course");
    }
    public void setCourse(Course course) {
        put("course", course);
    }

    public static ParseQuery<Test> getQuery() {
        ParseQuery<Test> query = ParseQuery.getQuery(Test.class);
        query.include("lecturer");
        query.include("course");
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
        return query;
    }

    @Override
    public String toString() {
        return getType() + "; "
                + getYear() + "; "
                + getCourse() + "; "
                + getLecturer() + "; "
                + getDuration() + " hrs";
    }
}
