package com.pascoapp.wba02_android.views.takeTest.questionTypes;

import android.webkit.JavascriptInterface;

import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.services.questions.Questions;

/**
 * Created by peter on 12/26/16.
 */
class McqWebAppInterface {

    private McqFragment mcqFragment;

    public McqWebAppInterface(McqFragment mcqFragment) {
        this.mcqFragment = mcqFragment;
    }

    @JavascriptInterface
    public void checkMcq(String result) {

        // create or update question's submittedAnswers with this users answer
        Helpers.getDatabaseInstance().getReference().child(Questions.QUESTIONS_KEY)
                .child(mcqFragment.questionKey).child("submittedAnswers").child(mcqFragment.user.getUid())
                .setValue(result);

        CheckAnswerDialogFragment checkAnswerDialogFragment
                = CheckAnswerDialogFragment.newInstance("Answers", mcqFragment.answerHash);
        checkAnswerDialogFragment.show(mcqFragment.getChildFragmentManager(), "CheckAnswerDialog");
    }
}
