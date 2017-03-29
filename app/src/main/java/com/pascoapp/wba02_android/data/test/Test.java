package com.pascoapp.wba02_android.data.test;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Test implements Parcelable {

    @SerializedName("id")
    @Expose
    public Integer id;
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
    @SerializedName("profile_picture_url")
    @Expose
    public String profilePictureUrl;
    @SerializedName("quiz_contents")
    @Expose
    public List<TestContent> testContents = null;

    @Expose
    public String name;
    @SerializedName("question_count")
    @Expose
    public Integer questionCount;

    @SerializedName("pasco_credits")
    @Expose
    public Integer pascoCredits;



    protected Test(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        courseCode = in.readString();
        courseName = in.readString();
        testName = in.readString();
        testDuration = in.readString();
        if (in.readByte() == 0x01) {
            instructions = new ArrayList<>();
            in.readList(instructions, String.class.getClassLoader());
        } else {
            instructions = null;
        }
        profilePictureUrl = in.readString();
        if (in.readByte() == 0x01) {
            testContents = new ArrayList<>();
            in.readList(testContents, TestContent.class.getClassLoader());
        } else {
            testContents = null;
        }
        name = in.readString();
        questionCount = in.readByte() == 0x00 ? null : in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        dest.writeString(courseCode);
        dest.writeString(courseName);
        dest.writeString(testName);
        dest.writeString(testDuration);
        if (instructions == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(instructions);
        }
        dest.writeString(profilePictureUrl);
        if (testContents == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(testContents);
        }
        dest.writeString(name);
        if (questionCount == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(questionCount);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Test> CREATOR = new Parcelable.Creator<Test>() {
        @Override
        public Test createFromParcel(Parcel in) {
            return new Test(in);
        }

        @Override
        public Test[] newArray(int size) {
            return new Test[size];
        }
    };


    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", courseCode='" + courseCode + '\'' +
                ", courseName='" + courseName + '\'' +
                ", testName='" + testName + '\'' +
                ", testDuration='" + testDuration + '\'' +
                ", instructions=" + instructions +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                ", testContents=" + testContents +
                ", name='" + name + '\'' +
                ", questionCount=" + questionCount +
                '}';
    }
}