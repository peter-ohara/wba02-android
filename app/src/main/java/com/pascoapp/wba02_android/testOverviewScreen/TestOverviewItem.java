package com.pascoapp.wba02_android.testOverviewScreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TestOverviewItem {

    @SerializedName("course_code")
    @Expose
    public String courseCode;
    @SerializedName("course_name")
    @Expose
    public String courseName;
    @SerializedName("quiz_name")
    @Expose
    public String testName;
    @SerializedName("quiz_duration")
    @Expose
    public String testDuration;
    @SerializedName("instructions")
    @Expose
    public List<String> instructions = null;

    @Override
    public String toString() {
        return "TestOverviewItem{" +
                "courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", testName='" + testName + '\'' +
                ", testDuration='" + testDuration + '\'' +
                ", instructions=" + instructions +
                '}';
    }
}