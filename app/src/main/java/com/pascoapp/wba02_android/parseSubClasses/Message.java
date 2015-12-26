package com.pascoapp.wba02_android.parseSubClasses;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Date;

/**
 * Handles Message logic
 */
@ParseClassName("Message")
public class Message extends ParseObject {
    public Message() {
    }

    public String getTitle() {
        return getString("title");
    }
    public void setTitle(String title) {
        put("title", title);
    }

    public String getContent() {
        return getString("content");
    }
    public void setContent(String content) {
        put("content", content);
    }

    public Date getDate() {
        // TODO: Change this to sent time
        return getCreatedAt();
    }

    public void setDate(Date date) {
        put("date", date);
    }

    public Programme getProgramme() {
        return (Programme) getParseObject("programme");
    }
    public void setProgramme(Programme programme) {
        put("programme", programme);
    }

    public static ParseQuery<Message> getQuery() {
        ParseQuery<Message> query = ParseQuery.getQuery(Message.class);
        query.include("programme");
        query.setCachePolicy(ParseQuery.CachePolicy.CACHE_ELSE_NETWORK);
        return query;
    }

    @Override
    public String toString() {
        return getTitle() + ": "
                + getContent() + "; "
                + getProgramme();
    }
}