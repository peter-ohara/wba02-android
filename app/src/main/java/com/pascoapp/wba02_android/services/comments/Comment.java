package com.pascoapp.wba02_android.services.comments;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@IgnoreExtraProperties
@Data
@ToString(exclude = {"commentDepth", "childCount", "commentId"})
@NoArgsConstructor
public class Comment {
    @Exclude
    private String key;

    private String parent;

    @Exclude
    private int commentDepth;
    @Exclude
    private int childCount;

    private String author;
    private String message;
    private Boolean flagged;
    private Long timestamp;

    // user, vote
    private Map<String, Integer> votes;

}
