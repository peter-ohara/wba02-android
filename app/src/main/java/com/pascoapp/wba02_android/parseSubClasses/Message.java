package com.pascoapp.wba02_android.parseSubClasses;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Handles Message logic
 */
@IgnoreExtraProperties
public class Message {

    public String title;
    public String content;
    public Long date;

    public String programme;

    public Message() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Message(String title, String content, Long date, String programme) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.programme = programme;
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

    public String getProgramme() {
        return programme;
    }

    @Override
    public String toString() {
        return "Message{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", date=" + date +
                ", programme='" + programme + '\'' +
                '}';
    }
}