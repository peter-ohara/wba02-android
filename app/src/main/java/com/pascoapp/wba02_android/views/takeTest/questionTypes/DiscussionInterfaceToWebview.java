package com.pascoapp.wba02_android.views.takeTest.questionTypes;

import android.util.Log;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pascoapp.wba02_android.services.comments.Comment;
import com.pascoapp.wba02_android.services.comments.Comments;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

/**
 * Created by peter on 12/8/16.
 */
public class DiscussionInterfaceToWebview {
    private McqFragment questionFragment;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private String questionKey;

    public DiscussionInterfaceToWebview(McqFragment questionFragment, String questionKey) {
        this.questionFragment = questionFragment;
        this.questionKey = questionKey;
    }

    public String getQuestionKey() {
        return questionKey;
    }

    @JavascriptInterface
    public String postComment(String commentJsonString) {
        try {
            JSONObject commentJson = Comments.convertStringToCommentJson(commentJsonString);

            String key = Comments.COMMENTS_REF.child(questionKey).push().getKey();

            Comment comment = new Comment();
            comment.setKey(key);
            commentJson.put("id", key);
            commentJson.put("key", key);
            Log.d(TAG, "deleteComment: " + commentJson);

            if (commentJson.has("parent")) {
                if (!commentJson.getString("parent").equals("null")) {
                    comment.setParent(commentJson.getString("parent"));
                }
            }

            comment.setCreated(commentJson.getString("created"));
            comment.setModified(commentJson.getString("modified"));
            comment.setContent(commentJson.getString("content"));

            comment.setAuthorId(user.getUid());

            comment.setUpvoteCount(commentJson.getLong("upvote_count"));

            Comments.COMMENTS_REF.child(questionKey).child(key).setValue(comment);
            return commentJson.toString();
        } catch (JSONException e) {
            Toast.makeText(questionFragment.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return null;
    }

    @JavascriptInterface
    public String putComment(String commentJsonString) {
        try {
            JSONObject commentJson = Comments.convertStringToCommentJson(commentJsonString);
            Log.d(TAG, "deleteComment: " + commentJson);

            String key = commentJson.getString("id");


            Comment comment = new Comment();
            comment.setKey(key);

            if (commentJson.has("parent")) {
                if (!commentJson.getString("parent").equals("null")) {
                    comment.setParent(commentJson.getString("parent"));
                }
            }

            comment.setCreated(commentJson.getString("created"));
            comment.setModified(commentJson.getString("modified"));
            comment.setContent(commentJson.getString("content"));

            comment.setAuthorId(user.getUid());

            comment.setUpvoteCount(commentJson.getLong("upvote_count"));

            Comments.COMMENTS_REF.child(questionKey).child(key).setValue(comment);
            return commentJsonString;
        } catch (JSONException e) {
            Toast.makeText(questionFragment.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return null;
    }

    @JavascriptInterface
    public void deleteComment(String commentJsonString) {
        try {
            JSONObject commentJson = Comments.convertStringToCommentJson(commentJsonString);

            Log.d(TAG, "deleteComment: " + commentJson);

            String key = commentJson.getString("id");
            Comments.COMMENTS_REF.child(questionKey).child(key).setValue(null);
        } catch (JSONException e) {
            Toast.makeText(questionFragment.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public String upvoteComment(String newCommentJson) {
        return newCommentJson;
    }

    @JavascriptInterface
    public String uploadAttachments() {
        return "";
    }
}
