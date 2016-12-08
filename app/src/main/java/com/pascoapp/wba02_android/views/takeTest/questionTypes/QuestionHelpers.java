package com.pascoapp.wba02_android.views.takeTest.questionTypes;

import com.pascoapp.wba02_android.services.comments.Comment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by peter on 8/19/16.
 */
public class QuestionHelpers {

    public static Integer getVotes(Comment comment) {
        Map<String, Integer> votes = comment.getVotes();
        Integer sumVotes = 0;
        if (votes != null) {
            for (Map.Entry<String, Integer> entry: comment.getVotes().entrySet()) {
                sumVotes += entry.getValue();
            }
        }
        return sumVotes;
    }

    public static Comment createCommentFromJson(String newCommentJson) throws JSONException {
        JSONObject commentValues = new JSONObject(newCommentJson);

        Comment comment = new Comment();
        comment.setKey(commentValues.getString("id"));
        comment.setId(commentValues.getString("id"));
        comment.setCreated(commentValues.getString("created"));
        comment.setModified(commentValues.getString("modified"));
        comment.setContent(commentValues.getString("content"));
        comment.setFullname(commentValues.getString("fullname"));
        comment.setProfile_picture_url(commentValues.getString("profile_picture_url"));
        comment.setUpvote_count(commentValues.getLong("upvote_count"));

        if (commentValues.has("parent"))
            comment.setParent(commentValues.getString("parent"));
        if (commentValues.has("created_by_admin"))
            comment.setCreated_by_admin(commentValues.getBoolean("created_by_admin"));
        if (commentValues.has("created_by_current_user"))
            comment.setCreated_by_current_user(commentValues.getBoolean("created_by_current_user"));
        if (commentValues.has("user_has_upvoted"))
            comment.setUser_has_upvoted(commentValues.getBoolean("user_has_upvoted"));

        return comment;
    }

    public static JSONObject convertCommentToJson(Comment comment) throws JSONException {
        JSONObject commentJson = new JSONObject();

        commentJson.put("id", comment.getId() );
        commentJson.put("parent", comment.getParent() );
        commentJson.put("created", comment.getCreated() );
        commentJson.put("modified", comment.getModified() );
        commentJson.put("content", comment.getContent() );
        commentJson.put("fullname", comment.getFullname() );
        commentJson.put("profile_picture_url", comment.getProfile_picture_url() );
        commentJson.put("created_by_admin", comment.getCreated_by_admin() );
        commentJson.put("created_by_current_user", comment.getCreated_by_current_user() );
        commentJson.put("upvote_count", comment.getUpvote_count() );
        commentJson.put("user_has_upvoted", comment.getUser_has_upvoted() );

        return commentJson;
    }

}
