package com.pascoapp.wba02_android.views.takeTest.questionTypes;

import android.content.Context;
import android.webkit.JavascriptInterface;

import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.services.questions.Questions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by peter on 12/26/16.
 */
class FillInWebAppInterface {
    private FillInFragment fillInFragment;
    Context mContext;

    private String questionKey;

    public FillInWebAppInterface(FillInFragment fillInFragment) {
        this.fillInFragment = fillInFragment;
    }

    @JavascriptInterface
    public void checkFillIn(String[] result) {
        List<String> resultList = new ArrayList<>(Arrays.asList(result));

        String answers = "[";
        answers += "\"" + resultList.get(0) + "\"";
        resultList.remove(0);

        for (String answer : resultList) {
            answers += ", \"" + answer + "\"";
        }
        answers += "]";

        System.out.println(questionKey + " : " + fillInFragment.user.getUid() + " : " + answers);

        Helpers.getDatabaseInstance().getReference().child(Questions.QUESTIONS_KEY)
                .child(questionKey).child("submittedAnswers").child(fillInFragment.user.getUid())
                .setValue(answers);
    }
}
