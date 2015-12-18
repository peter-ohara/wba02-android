package com.pascoapp.wba02_android.Test;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.pascoapp.wba02_android.Course.Course;
import java.util.Date;

/**
 * ParseObject Subclass for Test. Handles Test logic
 */
@ParseClassName("Test")
public class Test extends ParseObject{

    public Test() {
    }

    // TODO: Make getLecturer return ParseRelation
    public String getLecturer() {
        return getString("lecturer");
    }
    // TODO: Change Input parameter return ParseRelation
    public void setLecturer(String lecturer) {
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

    public ParseObject getCourse() {
        return getParseObject("course");
    }
    public void setCourse(Course course) {
        put("course", course);
    }

    public static ParseQuery<Test> getQuery() {
        return ParseQuery.getQuery(Test.class);
    }
}
