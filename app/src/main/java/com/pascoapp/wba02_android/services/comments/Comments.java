package com.pascoapp.wba02_android.services.comments;

import android.content.Context;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.services.users.User;
import com.pascoapp.wba02_android.services.users.Users;
import com.pascoapp.wba02_android.views.takeTest.questionTypes.DiscussionInterfaceToWebview;
import com.pascoapp.wba02_android.views.takeTest.questionTypes.McqFragment;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by peter on 8/4/16.
 */

public class Comments {

    private static final String TAG = Comments.class.getSimpleName();

    public static final String COMMENTS_KEY = "comments";
    public static final DatabaseReference COMMENTS_REF
            = Helpers.getDatabaseInstance().getReference().child(COMMENTS_KEY);



    public static JSONObject convertStringToCommentJson(String newCommentJson) throws JSONException {
        return new JSONObject(newCommentJson);
    }

    public static ChildEventListener createCommentEventListener(
            Context context,
            WebView webview,
            final DiscussionInterfaceToWebview discussionInterfaceToWebview,
            FirebaseUser user) {

        return new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                    // A new comment has been added, add it to the displayed list
                    Comment comment = dataSnapshot.getValue(Comment.class);

                    Users.fetchUser(comment.getAuthorId())
                             .subscribe(author -> {
                                String commentJsonString
                                        = commentToJsonString(comment, author, user);
                                 Log.d(TAG, "onChildAdded:" + commentJsonString);
                                 webview.loadUrl("javascript:createComment("+ commentJsonString +")");
                            });
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                    // A comment has changed, use the key to determine if we are displaying this
                    // comment and if so displayed the changed comment.
                    Comment newComment = dataSnapshot.getValue(Comment.class);
                    String commentKey = dataSnapshot.getKey();

                    String commentJsonString = "";
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                    // A comment has changed, use the key to determine if we are displaying this
                    // comment and if so remove it.
                    String commentKey = dataSnapshot.getKey();
                    webview.loadUrl("javascript:removeComment(\'"+ commentKey +"\')");
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                    Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                    // A comment has changed position, use the key to determine if we are
                    // displaying this comment and if so move it.
                    Comment movedComment = dataSnapshot.getValue(Comment.class);
                    String commentKey = dataSnapshot.getKey();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.w(TAG, "postComments:onCancelled", databaseError.toException());
                    Toast.makeText(context, "Failed to load comments.",
                            Toast.LENGTH_SHORT).show();
                }
            };
    }

    public static String commentToJsonString(Comment comment, User author, FirebaseUser currentUser) {
        String displayName = author.getDisplayName();

        String photoUrl = null;
        if (author.getPhotoUrl() != null) {
            photoUrl = "\"" + author.getPhotoUrl() + "\"";
        }

        Boolean createdByAdmin = false;
        if (true) {
            createdByAdmin = false;
        }

        Boolean createdByCurrentUser = false;
        if (comment.getAuthorId().equals(currentUser.getUid())) {
            createdByCurrentUser = true;
        }

        Boolean userHasUpvoted = false;
        if (true) {
            userHasUpvoted = false;
        }

        String parent = null;
        if (comment.getParent() != null) {
            parent = "\"" + comment.getParent() + "\"";
        }

        return "{\n" +
                "               \"id\": \"" + comment.getId() +"\",\n" +
                "               \"parent\": " + parent + ",\n" +
                "               \"created\": \"" + comment.getCreated() + "\",\n" +
                "               \"modified\": \"" + comment.getModified() + "\",\n" +
                "               \"content\": \"" + comment.getContent() + "\",\n" +
                "               \"fullname\": \"" + displayName + "\",\n" +
                "               \"profile_picture_url\": " + photoUrl + ",\n" +
                "               \"created_by_admin\": " + createdByAdmin + ",\n" +
                "               \"created_by_current_user\": " + createdByCurrentUser + ",\n" +
                "               \"upvote_count\":" + comment.getUpvoteCount() +  ",\n" +
                "               \"user_has_upvoted\": " + userHasUpvoted + "\n" +
                "            }";
    }
}
