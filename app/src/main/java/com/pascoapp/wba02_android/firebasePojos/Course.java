package com.pascoapp.wba02_android.firebasePojos;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

/**
 * Handles Course logic
 */
@IgnoreExtraProperties
public class Course {

    public static final String COURSES_KEY = "courses";
    public String code;
    public String name;
    public Long semester;
    public Long level;
    public String programme;
    public String school;

    public Course() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Course(String code, String name, Long semester, Long level, String programme, String school) {
        this.code = code;
        this.name = name;
        this.semester = semester;
        this.level = level;
        this.programme = programme;
        this.school = school;
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

    public String getProgramme() {
        return programme;
    }

    public String getSchool() {
        return school;
    }

    public static void fetchCourse(String courseKey, ValueEventListener valueEventListener) {
        DatabaseReference testRef = FirebaseDatabase.getInstance().getReference()
                .child(COURSES_KEY).child(courseKey);
        testRef.addValueEventListener(valueEventListener);
    }

    @Override
    public String toString() {
        return "Course{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", semester='" + semester + '\'' +
                ", level='" + level + '\'' +
                ", programme='" + programme + '\'' +
                ", school='" + school + '\'' +
                '}';
    }
}
