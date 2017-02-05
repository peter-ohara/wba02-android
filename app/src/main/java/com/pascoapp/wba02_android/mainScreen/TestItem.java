package com.pascoapp.wba02_android.mainScreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestItem {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("course_code")
    @Expose
    public String courseCode;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("question_count")
    @Expose
    public Integer questionCount;

}