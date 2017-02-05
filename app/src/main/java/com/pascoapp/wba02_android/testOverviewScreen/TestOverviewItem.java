package com.pascoapp.wba02_android.testOverviewScreen;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestOverviewItem {

    @SerializedName("course_code")
    @Expose
    public String courseCode;
    @SerializedName("course_name")
    @Expose
    public String courseName;
    @SerializedName("test_name")
    @Expose
    public String testName;
    @SerializedName("test_duration")
    @Expose
    public String testDuration;
    @SerializedName("instructions")
    @Expose
    public List<String> instructions = null;

}