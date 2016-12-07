package com.pascoapp.wba02_android.services.comments;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Map;

import lombok.Data;
import lombok.NoArgsConstructor;

@IgnoreExtraProperties
@Data
@NoArgsConstructor
public class Comment {
    @Exclude
    private String key;

    private Long id;
    private Long parent;
    private String created;
    private String modified;
    private String content;
    private String fullname;
    private String profile_picture_url;
    private Boolean created_by_admin;
    private Boolean created_by_current_user;
    private Long upvote_count;
    private Boolean user_has_upvoted;

    // user, vote
    private Map<String, Integer> votes;

}
