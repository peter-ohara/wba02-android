package com.pascoapp.wba02_android.services.comments;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@IgnoreExtraProperties
@NoArgsConstructor
public class Comment {

    @Exclude
    private String key;

    @Exclude
    private String id;

    @Getter @Setter
    private String parent;

    @Getter @Setter @NonNull
    private String created;

    @Getter @Setter @NonNull
    private String modified;


    @Getter @Setter @NonNull
    private String content;

    // File related fields
    // TODO: Add File related fields

    // Author related fields
    @Getter @Setter @NonNull
    private String authorId;

    @Getter @Setter
    private Long upvoteCount;

    @Getter @Setter
    private Map<String, Integer> votes;



    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
        this.id = key;
    }

    public String getId() {
        return id;
    }

}
