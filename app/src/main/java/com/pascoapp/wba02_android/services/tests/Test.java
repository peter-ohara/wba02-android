package com.pascoapp.wba02_android.services.tests;

import com.google.firebase.database.IgnoreExtraProperties;
import com.pascoapp.wba02_android.services.FirebaseItem;

import java.util.List;
import java.util.Map;

/**
 * Handles Test logic
 */
@IgnoreExtraProperties
public class Test implements FirebaseItem {

    public String key;

    public String type;
    public Long duration;
    public List<String> instructions;

    public String lecturerKey;
    public String courseKey;
    public Map<String, Boolean> programmeKeys;
    public String schoolKey;

    public Long year;

    public Test() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Test(String type, Long duration,
                List<String> instructions, String lecturerKey, String courseKey,
                Map<String, Boolean> programmeKeys, String schoolKey, Long year) {
        this.type = type;
        this.duration = duration;
        this.instructions = instructions;
        this.lecturerKey = lecturerKey;
        this.courseKey = courseKey;
        this.programmeKeys = programmeKeys;
        this.schoolKey = schoolKey;
        this.year = year;
    }

    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
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

    public Map<String, Boolean> getProgrammeKeys() {
        return programmeKeys;
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
                "nodeKey='" + key + '\'' +
                ", type='" + type + '\'' +
                ", duration=" + duration +
                ", year=" + year +
                '}';
    }
}
