package com.pascoapp.wba02_android.takeTest.testContentScreens;

import android.webkit.JavascriptInterface;

import java.util.Map;

class WebAppInterface {

    private TestContentFragment testContentFragment;
    private Map<String, Integer> answerHistogram;

    WebAppInterface(TestContentFragment testContentFragment,
                    Map<String, Integer> answerHistogram) {
        this.testContentFragment = testContentFragment;
        this.answerHistogram = answerHistogram;
    }

    @JavascriptInterface
    public void checkMcq(String result) {

        // TODO: Save students answer


        CheckAnswerDialogFragment checkAnswerDialogFragment
                = CheckAnswerDialogFragment.newInstance("Answers", answerHistogram);
        checkAnswerDialogFragment.show(testContentFragment.getChildFragmentManager(), "CheckAnswerDialog");
    }
}
