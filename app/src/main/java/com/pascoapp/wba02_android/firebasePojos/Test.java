package com.pascoapp.wba02_android.firebasePojos;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.List;

/**
 * Handles Test logic
 */
@IgnoreExtraProperties
public class Test implements Serializable {

    public static final String TESTS_KEY = "tests";
    public String key;
    public String type;
    public Long duration;
    public List<String> instructions;

    public String lecturer;
    public String course;
    public String programme;
    public String school;

    public Long year;

    public Test() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Test(String type, Long duration, List<String> instructions, String lecturer, String course, String programme, String school, Long year) {
        this.type = type;
        this.duration = duration;
        this.instructions = instructions;
        this.lecturer = lecturer;
        this.course = course;
        this.programme = programme;
        this.school = school;
        this.year = year;
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public Long getDuration() {
        return duration;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public String getLecturer() {
        return lecturer;
    }

    public String getCourse() {
        return course;
    }

    public String getProgramme() {
        return programme;
    }

    public String getSchool() {
        return school;
    }

    public Long getYear() {
        return year;
    }

    public static void fetchTest(String testKey, ValueEventListener valueEventListener) {
        DatabaseReference testRef = FirebaseDatabase.getInstance().getReference()
                .child(TESTS_KEY).child(testKey);
        testRef.addValueEventListener(valueEventListener);
    }

    @Override
    public String toString() {
        return "Test{" +
                "key='" + key + '\'' +
                ", type='" + type + '\'' +
                ", duration=" + duration +
                ", instructions=" + instructions +
                ", lecturer='" + lecturer + '\'' +
                ", course='" + course + '\'' +
                ", programme='" + programme + '\'' +
                ", school='" + school + '\'' +
                ", year=" + year +
                '}';
    }
}
