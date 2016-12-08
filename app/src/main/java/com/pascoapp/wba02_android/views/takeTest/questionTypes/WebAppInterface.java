package com.pascoapp.wba02_android.views.takeTest.questionTypes;

import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.pascoapp.wba02_android.services.comments.Comment;
import com.pascoapp.wba02_android.services.comments.Comments;

import org.json.JSONException;

/**
 * Created by peter on 12/8/16.
 */
public class WebAppInterface {
    private McqFragment questionFragment;
    private String sorryText = "This feature will be unlocked in a later update. Stay tuned!";

    private String questionKey;

    public WebAppInterface(McqFragment questionFragment, String questionKey) {
        this.questionFragment = questionFragment;
        this.questionKey = questionKey;
    }

    @JavascriptInterface
    public String getComments() {
        return questionFragment.commentsArray.toString();
    }

    @JavascriptInterface
    public String postComment(String newCommentJson) {
        try {
            Comment comment = QuestionHelpers.createCommentFromJson(newCommentJson);
            String key = comment.getId();
            Comments.COMMENTS_REF.child(questionKey).child(key).setValue(comment);
            return newCommentJson;
        } catch (JSONException e) {
            Toast.makeText(questionFragment.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return null;
    }

    @JavascriptInterface
    public String putComment(String newCommentJson) {
        try {
            Comment comment = QuestionHelpers.createCommentFromJson(newCommentJson);
            String key = comment.getId();
            Comments.COMMENTS_REF.child(questionKey).child(key).setValue(comment);
            return newCommentJson;
        } catch (JSONException e) {
            Toast.makeText(questionFragment.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return null;
    }

    @JavascriptInterface
    public void deleteComment(String newCommentJson) {
        try {
            Comment comment = QuestionHelpers.createCommentFromJson(newCommentJson);
            String key = comment.getId();
            Comments.COMMENTS_REF.child(questionKey).child(key).setValue(null);
        } catch (JSONException e) {
            Toast.makeText(questionFragment.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @JavascriptInterface
    public String upvoteComment(String newCommentJson) {
        return questionFragment.commentsArray.toString();
    }

    @JavascriptInterface
    public String uploadAttachments() {
        return questionFragment.commentsArray.toString();
    }

}
