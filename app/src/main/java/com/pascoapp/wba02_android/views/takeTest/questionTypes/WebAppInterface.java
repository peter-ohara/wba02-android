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
    private String sorryText = "This feature will be unlocked in a later put. Stay tuned!";

    WebAppInterface(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public void openDiscussionScreen() {
        Toast.makeText(mContext, sorryText, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void checkFillIn(String[] result) {
        List<String> answers = Arrays.asList(result);
        Toast.makeText(mContext, sorryText, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void checkMcq(String result) {
        Toast.makeText(mContext, sorryText, Toast.LENGTH_SHORT).show();
    }
}
