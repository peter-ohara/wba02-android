package com.pascoapp.wba02_android.dataFetching;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Handles Course logic
 */
@IgnoreExtraProperties
public class Course {

    public String key;
    public String code;
    public String name;
    public Long semester;
    public Long level;
    public String programmeKey;
    public String schoolKey;

    public Course() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Course(String code, String name, Long semester, Long level, String programme, String schoolKey) {
        this.code = code;
        this.name = name;
        this.semester = semester;
        this.level = level;
        this.programmeKey = programme;
        this.schoolKey = schoolKey;
    }

    public String getKey() {
        return key;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Long getSemester() {
        return semester;
    }

    public Long getLevel() {
        return level;
    }

    public String getProgrammeKey() {
        return programmeKey;
    }

    public String getSchoolKey() {
        return schoolKey;
    }

    @Override
    public String toString() {
        return "Course{" +
                "key='" + key + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", semester=" + semester +
                ", level=" + level +
                '}';
    }
}
