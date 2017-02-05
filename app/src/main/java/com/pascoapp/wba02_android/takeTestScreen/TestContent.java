package com.pascoapp.wba02_android.takeTestScreen;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class TestContent implements Parcelable {

    @SerializedName("priority")
    @Expose
    public Integer priority = 0;
    @SerializedName("type")
    @Expose
    public String type = "essay_type";
    @SerializedName("title")
    @Expose
    public String title = "";
    @SerializedName("content")
    @Expose
    public String content = "";
    @SerializedName("choices")
    @Expose
    public List<String> choices = new ArrayList<>();
    @SerializedName("comments")
    @Expose
    public List<Object> comments = new ArrayList<>();
    public final static Parcelable.Creator<TestContent> CREATOR = new Creator<TestContent>() {


        @SuppressWarnings({
                "unchecked"
        })
        public TestContent createFromParcel(Parcel in) {
            TestContent instance = new TestContent();
            instance.priority = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.type = ((String) in.readValue((String.class.getClassLoader())));
            instance.title = ((String) in.readValue((String.class.getClassLoader())));
            instance.content = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.choices, (java.lang.String.class.getClassLoader()));
            in.readList(instance.comments, (java.lang.Object.class.getClassLoader()));
            return instance;
        }

        public TestContent[] newArray(int size) {
            return (new TestContent[size]);
        }

    }
            ;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(priority);
        dest.writeValue(type);
        dest.writeValue(title);
        dest.writeValue(content);
        dest.writeList(choices);
        dest.writeList(comments);
    }

    public int describeContents() {
        return 0;
    }

    public static TestContent createEndPage() {
        TestContent endPageData = new TestContent();
        endPageData.title = "END";
        endPageData.content = "";
        endPageData.type = "end_page";
        endPageData.comments = new ArrayList<>();
        return endPageData;
    }

}
