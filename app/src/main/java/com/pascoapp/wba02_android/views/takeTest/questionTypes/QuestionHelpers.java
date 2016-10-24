package com.pascoapp.wba02_android.views.takeTest.questionTypes;

import com.pascoapp.wba02_android.services.comments.Comment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
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

    public static List<Comment> toThreadedComments(List<Comment> comments){

        //comments should be sorted by date first
        Collections.sort(comments, new Comparator<Comment>(){
            public int compare(Comment o1, Comment o2){
                if(getVotes(o1) == getVotes(o2))
                    return 0;
                return getVotes(o1) > getVotes(o2) ? -1 : 1;
            }
        });

        //The resulting array of threaded comments
        List<Comment> threaded = new ArrayList<Comment>();

        //An array used to hold processed comments which should be removed at the end of the cycle
        List<Comment> removeComments = new ArrayList<Comment>();

        //get the root comments first (comments with no parent)
        for(int i = 0; i < comments.size(); i++){
            Comment c = comments.get(i);
            if(c.getParent() == null){
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
                    if(parent.getKey().equals(child.getParent())){
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
