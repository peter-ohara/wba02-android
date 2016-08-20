package com.pascoapp.wba02_android.views.takeTest.questionTypes;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.pascoapp.wba02_android.views.takeTest.TakeTestActivity;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * Created by peter on 8/19/16.
 */
public class WebAppInterface {
    Context mContext;

    WebAppInterface(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public void openDiscussionScreen() {
        ((TakeTestActivity) mContext).openDiscussionActivity();
    }

    @JavascriptInterface
    public void checkFillIn(String[] result) {
        List<String> answers = Arrays.asList(result);
        System.out.println(answers);
        Toast.makeText(mContext, answers.toString(), Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void checkMcq(String result) {
        System.out.println(result);
        Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
    }
}
