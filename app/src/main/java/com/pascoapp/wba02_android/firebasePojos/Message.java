package com.pascoapp.wba02_android.firebasePojos;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Handles Message logic
 */
@IgnoreExtraProperties
public class Message {

    public String title;
    public String content;
    public Long date;

    public Long semester;
    public Long quarter;

    public String course;
    public String lecturer;
    public String programme;
    public String question;
    public String school;
    public String test;
    public String user;

    public Message() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Message(String title, String content, Long date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getDate() {
        return date;
    }

    public Long getSemester() {
        return semester;
    }

    public Long getQuarter() {
        return quarter;
    }

    public String getCourse() {
        return course;
    }

    public String getLecturer() {
        return lecturer;
    }

    public String getProgramme() {
        return programme;
    }

    public String getQuestion() {
        return question;
    }

    public String getSchool() {
        return school;
    }

    public String getTest() {
        return test;
    }

    public String getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "Message{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                '}';
    }
}