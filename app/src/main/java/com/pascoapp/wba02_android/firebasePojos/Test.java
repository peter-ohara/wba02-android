package com.pascoapp.wba02_android.firebasePojos;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.List;

/**
 * Handles Test logic
 */
@IgnoreExtraProperties
public class Test implements Serializable {

    public static final String TESTS_KEY = "tests";

    public String key;

    public String type;
    public Long duration;
    public List<String> instructions;

    public String lecturerKey;
    public String courseKey;
    public String programmeKey;
    public String schoolKey;

    public Long year;

    public Test() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Test(String type, Long duration, List<String> instructions, String lecturerKey, String courseKey, String programmeKey, String schoolKey, Long year) {
        this.type = type;
        this.duration = duration;
        this.instructions = instructions;
        this.lecturerKey = lecturerKey;
        this.courseKey = courseKey;
        this.programmeKey = programmeKey;
        this.schoolKey = schoolKey;
        this.year = year;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public Long getDuration() {
        return duration;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public String getLecturerKey() {
        return lecturerKey;
    }

    public String getCourseKey() {
        return courseKey;
    }

    public String getProgrammeKey() {
        return programmeKey;
    }

    public String getSchoolKey() {
        return schoolKey;
    }

    public Long getYear() {
        return year;
    }

    @Override
    public String toString() {
        return "Test{" +
                "key='" + key + '\'' +
                ", type='" + type + '\'' +
                ", duration=" + duration +
                ", year=" + year +
                '}';
    }
}
