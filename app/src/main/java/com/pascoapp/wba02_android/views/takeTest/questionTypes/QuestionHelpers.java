package com.pascoapp.wba02_android.views.takeTest.questionTypes;

import com.pascoapp.wba02_android.services.comments.Comment;

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

}
