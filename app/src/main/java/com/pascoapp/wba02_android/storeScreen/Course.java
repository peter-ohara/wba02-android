package com.pascoapp.wba02_android.storeScreen;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Course implements Parcelable
{

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
    public final static Parcelable.Creator<Course> CREATOR = new Creator<Course>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Course createFromParcel(Parcel in) {
            Course instance = new Course();
            instance.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
            instance.code = ((String) in.readValue((String.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.level = ((String) in.readValue((String.class.getClassLoader())));
            instance.semester = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Course[] newArray(int size) {
            return (new Course[size]);
        }

    }
            ;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(code);
        dest.writeValue(name);
        dest.writeValue(level);
        dest.writeValue(semester);
    }

    public int describeContents() {
        return 0;
    }


    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", level='" + level + '\'' +
                ", semester='" + semester + '\'' +
                '}';
    }
}