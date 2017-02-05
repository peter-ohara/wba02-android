package com.pascoapp.wba02_android.takeTestScreen.questionScreens;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.takeTestScreen.TestContent;
import com.wang.avi.AVLoadingIndicatorView;
import com.x5.template.Chunk;
import com.x5.template.Theme;
import com.x5.template.providers.AndroidTemplates;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MulitpleChoiceFragment extends Fragment {

    private static final String TAG = MulitpleChoiceFragment.class.getSimpleName();

    // the fragment initialization parameters
    private static final String ARG_TEST_CONTENT = "com.pascoapp.wba02_android.testContent";

    // Member Variables related to the testContent
    private TestContent testContent;

    protected FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @BindView(R.id.webview) WebView webview;
    @BindView(R.id.loading_indicator) AVLoadingIndicatorView loadingIndicator;

    public static MulitpleChoiceFragment newInstance(TestContent testContent) {
        MulitpleChoiceFragment fragment = new MulitpleChoiceFragment();
        Bundle args = new Bundle();

        args.putParcelable(ARG_TEST_CONTENT, testContent);
        Log.d(TAG, "newInstance: " + testContent);
        fragment.setArguments(args);
        return fragment;
    }

    public MulitpleChoiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: " + testContent);
        if (getArguments() != null) {
            testContent = getArguments().getParcelable(ARG_TEST_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mcq, container, false);
        ButterKnife.bind(this, view);
        loadingIndicator.hide();

        Log.d(TAG, "onCreateView: " + testContent);
        loadItemInWebView(getContext(), webview, testContent);

        return view;
    }

    public void loadItemInWebView(Context context, WebView w, TestContent testContent) {
        w.getSettings().setJavaScriptEnabled(true);
        w.setBackgroundColor(Color.TRANSPARENT);

        w.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
                return true;
            }
        });

        String mime = "text/html";
        String encoding = "utf-8";
        String baseURL = "file:///android_res/raw/";
        w.loadDataWithBaseURL(baseURL, getHtml(testContent), mime, encoding, null);
    }

    private String getHtml(TestContent testContent) {
        AndroidTemplates loader = new AndroidTemplates(getContext());
        Theme theme = new Theme(loader);
        Chunk chunk = theme.makeChunk("mcq");
        chunk.set("question", testContent.content);

        Log.d(TAG, "getHtml: " + testContent.choices);
        SortedMap<String, String> choices = new TreeMap<>();
        char key = 'a';
        for (char i = 0; i < testContent.choices.size(); i++) {
            choices.put(String.valueOf(key), testContent.choices.get(i));
            key++;
        }

        Log.d(TAG, "getHtml: " + choices);

        chunk.set("choices", choices);

        if (user.getPhotoUrl() != null) {
            chunk.set("photoUrl", "\"" + user.getPhotoUrl() + "\"");
        } else {
            chunk.set("photoUrl", "null");
        }

//        // Fetch users previous answer
//        if (question.getSubmittedAnswers() != null) {
//            String usersAnswer = question.getSubmittedAnswers().get(user.getUid());
//            chunk.set("usersAnswer", usersAnswer);
//        }

//        chunk.set("commentsArray", someJs);
        chunk.set("commentCount", testContent.comments.size());

        return chunk.toString();
    }


}
