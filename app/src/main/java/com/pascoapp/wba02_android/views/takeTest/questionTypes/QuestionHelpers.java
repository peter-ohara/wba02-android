package com.pascoapp.wba02_android.views.takeTest.questionTypes;

import android.content.Context;
import android.graphics.Color;
import android.webkit.WebView;

/**
 * Created by peter on 8/19/16.
 */
public class QuestionHelpers {

    public static void loadItemInWebView(Context context, WebView w, String html) {
        w.getSettings().setJavaScriptEnabled(true);
        w.addJavascriptInterface(new WebAppInterface(context), "Android");
        w.setBackgroundColor(Color.TRANSPARENT);

        String mime = "text/html";
        String encoding = "utf-8";
        String baseURL = "file:///android_res/raw/";
        w.loadDataWithBaseURL(baseURL, html, mime, encoding, null);
    }
}
