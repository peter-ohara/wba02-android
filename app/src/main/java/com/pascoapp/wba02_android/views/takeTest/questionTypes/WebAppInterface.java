package com.pascoapp.wba02_android.views.takeTest.questionTypes;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.services.questions.Question;
import com.pascoapp.wba02_android.services.questions.Questions;
import com.pascoapp.wba02_android.services.users.User;
import com.pascoapp.wba02_android.services.users.Users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by peter on 8/19/16.
 */
public class WebAppInterface {
    Context mContext;
    private String sorryText = "This feature will be unlocked in a later update. Stay tuned!";

    private String questionKey;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public WebAppInterface(Context mContext, String questionKey) {
        this.mContext = mContext;
        this.questionKey = questionKey;
    }

    @JavascriptInterface
    public void openDiscussionScreen() {
        Toast.makeText(mContext, sorryText, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void checkFillIn(String[] result) {
        List<String> resultList = new ArrayList<>(Arrays.asList(result));

        String answers = "[";
        answers += "\"" + resultList.get(0) + "\"";
        resultList.remove(0);

        for (String answer: resultList) {
            answers += ", \"" + answer + "\"";
        }
        answers += "]";

        Toast.makeText(mContext, answers, Toast.LENGTH_SHORT).show();
        System.out.println(questionKey + " : " + user.getUid() + " : " + answers);

        Helpers.getDatabaseInstance().getReference().child(Questions.QUESTIONS_KEY)
                .child(questionKey).child("submittedAnswers").child(user.getUid())
                .setValue(answers);
    }
}
