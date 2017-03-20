package com.pascoapp.wba02_android.takeTestScreen.testContentScreens;

import android.webkit.JavascriptInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by peter on 3/19/17.
 */

public class WebAppInterface {

    private TestContentFragment testContentFragment;
    private Map<String, Integer> answerHistogram;

    public WebAppInterface(TestContentFragment testContentFragment,
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
