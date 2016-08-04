package com.pascoapp.wba02_android.dataFetching;

import com.google.firebase.database.IgnoreExtraProperties;
import com.pascoapp.wba02_android.FirebaseItem;

/**
 * Handles Message logic
 */
@IgnoreExtraProperties
public class Message implements FirebaseItem {

    public String key;
    public String title;
    public String content;
    public Long date;

    public Long semester;
    public Long quarter;

    public String courseKey;
    public String lecturerKey;
    public String programmeKey;
    public String questionKey;
    public String schoolKey;
    public String testKey;
    public String userKey;

    public Message() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Message(String title, String content, Long date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }


    public String getKey() {
        return key;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
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

    public String getCourseKey() {
        return courseKey;
    }

    public String getLecturerKey() {
        return lecturerKey;
    }

    public String getProgrammeKey() {
        return programmeKey;
    }

    public String getQuestionKey() {
        return questionKey;
    }

    public String getSchoolKey() {
        return schoolKey;
    }

    public String getTestKey() {
        return testKey;
    }

    public String getUserKey() {
        return userKey;
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