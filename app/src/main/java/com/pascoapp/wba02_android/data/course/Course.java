package com.pascoapp.wba02_android.data.course;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.pascoapp.wba02_android.data.test.Test;

import java.util.ArrayList;
import java.util.List;

public class Course implements Parcelable {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("level")
    @Expose
    public String level;
    @SerializedName("semester")
    @Expose
    public String semester;

    @SerializedName("quiz_count")
    @Expose
    public String quizCount;
    @SerializedName("midsem_count")
    @Expose
    public String midsemCount;
    @SerializedName("class_test_count")
    @Expose
    public String classTestCount;
    @SerializedName("end_of_sem_count")
    @Expose
    public String endOfSemCount;
    @SerializedName("assignment_count")
    @Expose
    public String assignmentCount;

    @SerializedName("quizzes")
    @Expose
    public List<Test> tests = null;


    protected Course(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        code = in.readString();
        name = in.readString();
        level = in.readString();
        semester = in.readString();
        if (in.readByte() == 0x01) {
            tests = new ArrayList<>();
            in.readList(tests, Test.class.getClassLoader());
        } else {
            tests = null;
        }
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
        dest.writeString(code);
        dest.writeString(name);
        dest.writeString(level);
        dest.writeString(semester);
        if (tests == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(tests);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Course> CREATOR = new Parcelable.Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", level='" + level + '\'' +
                ", semester='" + semester + '\'' +
                ", tests=" + tests +
                '}';
    }
}