package com.pascoapp.wba02_android.services.comments;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@IgnoreExtraProperties
@Data
@ToString(exclude = {"commentParent", "commentDepth", "childCount", "commentId"})
@NoArgsConstructor
public class Comment {
    @Exclude
    private String key;

    @Exclude
    private String commentParent;
    @Exclude
    private int commentDepth;
    @Exclude
    private int childCount;

    private String author;
    private String message;
    private Boolean flagged;
    private Double timestamp;

}
