package com.pascoapp.wba02_android.parseSubClasses;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Handles Test logic
 */
@IgnoreExtraProperties
public class Test {

    public String type;
    public Long duration;
    public String instructions;

    public String lecturer;
    public String course;
    public String programme;
    public String school;

    public Long year;

    public Test() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Test(String type, Long duration, String instructions, String lecturer, String course, String programme, String school, Long year) {
        this.type = type;
        this.duration = duration;
        this.instructions = instructions;
        this.lecturer = lecturer;
        this.course = course;
        this.programme = programme;
        this.school = school;
        this.year = year;
    }

    public String getType() {
        return type;
    }

    public Long getDuration() {
        return duration;
    }

    public String getInstructions() {
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

    @Override
    public String toString() {
        return "Test{" +
                "type='" + type + '\'' +
                ", duration='" + duration + '\'' +
                ", instructions='" + instructions + '\'' +
                ", lecturer='" + lecturer + '\'' +
                ", course='" + course + '\'' +
                ", programme='" + programme + '\'' +
                ", school='" + school + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
