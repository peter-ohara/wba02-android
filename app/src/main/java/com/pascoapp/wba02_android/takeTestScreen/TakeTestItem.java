package com.pascoapp.wba02_android.takeTestScreen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TakeTestItem {

    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("quiz_contents")
    @Expose
    public List<TestContent> testContents = null;

    @Override
    public String toString() {
        return "TakeTestItem{" +
                "title='" + title + '\'' +
                ", testContents=" + testContents +
                '}';
    }
}