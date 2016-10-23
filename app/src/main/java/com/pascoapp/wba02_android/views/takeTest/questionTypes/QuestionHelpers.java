package com.pascoapp.wba02_android.views.takeTest.questionTypes;

import android.content.Context;
import android.graphics.Color;
import android.webkit.WebView;

import com.pascoapp.wba02_android.services.comments.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by peter on 8/19/16.
 */
public class QuestionHelpers {

    public static void loadItemInWebView(Context context, WebView w, String html, String questionKey) {
        w.getSettings().setJavaScriptEnabled(true);
        w.addJavascriptInterface(new WebAppInterface(context, questionKey), "Android");
        w.setBackgroundColor(Color.TRANSPARENT);

        String mime = "text/html";
        String encoding = "utf-8";
        String baseURL = "file:///android_res/raw/";
        w.loadDataWithBaseURL(baseURL, html, mime, encoding, null);
    }

    public static List<Comment> toThreadedComments(List<Comment> comments){

        //comments should be sorted by date first

        //The resulting array of threaded comments
        List<Comment> threaded = new ArrayList<Comment>();

        //An array used to hold processed comments which should be removed at the end of the cycle
        List<Comment> removeComments = new ArrayList<Comment>();

        //get the root comments first (comments with no parent)
        for(int i = 0; i < comments.size(); i++){
            Comment c = comments.get(i);
            if(c.getCommentParent() == null){
                c.setCommentDepth(0); //A property of Comment to hold its depth
                c.setChildCount(0); //A property of Comment to hold its child count
                threaded.add(c);
                removeComments.add(c);
            }
        }

        if(removeComments.size() > 0){
            //clear processed comments
            comments.removeAll(removeComments);
            removeComments.clear();
        }

        int depth = 0;
        //get the child comments up to a max depth of 10
        while(comments.size() > 0 && depth <= 10){
            depth++;
            for(int j = 0; j< comments.size(); j++){
                Comment child = comments.get(j);
                //check root comments for match
                for(int i = 0; i < threaded.size(); i++){
                    Comment parent = threaded.get(i);
                    if(parent.getKey() == child.getCommentParent()){
                        parent.setChildCount(parent.getChildCount()+1);
                        child.setCommentDepth(depth+parent.getCommentDepth());
                        threaded.add(i+parent.getChildCount(),child);
                        removeComments.add(child);
                        continue;
                    }
                }
            }
            if(removeComments.size() > 0){
                //clear processed comments
                comments.removeAll(removeComments);
                removeComments.clear();
            }
        }

        return threaded;
    }
}
