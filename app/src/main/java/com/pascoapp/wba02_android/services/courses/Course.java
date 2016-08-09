package com.pascoapp.wba02_android.services.courses;

import com.google.firebase.database.IgnoreExtraProperties;
import com.pascoapp.wba02_android.services.FirebaseItem;

/**
 * Handles Course logic
 */
@IgnoreExtraProperties
public class Course implements FirebaseItem {

    private String key;
    private String code;
    private String name;
    private Long semester;
    private Long level;
    private String programmeKey;
    private String schoolKey;

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

    @Override
    public void setKey(String key) {
        this.key = key;
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
