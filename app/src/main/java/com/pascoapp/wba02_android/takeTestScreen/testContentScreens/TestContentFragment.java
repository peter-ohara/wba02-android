package com.pascoapp.wba02_android.takeTestScreen.testContentScreens;

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

import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class TestContentFragment extends Fragment {

    public static final String MULTIPLE_CHOICE_QUESTION = "multiple_choice_question";
    public static final String FILL_IN_QUESTION = "fill_in_question";
    public static final String ESSAY_QUESTION = "essay_question";
    public static final String HEADER = "header";

    // the fragment initialization parameters
    private static final String ARG_TEST_CONTENT = "com.pascoapp.wba02_android.testContent";

    // Member Variables related to the testContent
    private TestContent testContent;

    protected FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @BindView(R.id.webview) WebView webview;
    @BindView(R.id.loading_indicator) AVLoadingIndicatorView loadingIndicator;

    public static TestContentFragment newInstance(TestContent testContent) {
        TestContentFragment fragment = new TestContentFragment();
        Bundle args = new Bundle();

        args.putParcelable(ARG_TEST_CONTENT, testContent);
        fragment.setArguments(args);
        return fragment;
    }

    public TestContentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            testContent = getArguments().getParcelable(ARG_TEST_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        switch (testContent.type) {
            case MULTIPLE_CHOICE_QUESTION:
                view = inflater.inflate(R.layout.fragment_mcq, container, false);
                break;
            case FILL_IN_QUESTION:
                view = inflater.inflate(R.layout.fragment_fill_in, container, false);
                break;
            case ESSAY_QUESTION:
                view = inflater.inflate(R.layout.fragment_essay, container, false);
                break;
            default:
                view = inflater.inflate(R.layout.fragment_header, container, false);
                break;
        }

        ButterKnife.bind(this, view);
        loadingIndicator.hide();

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
        switch (testContent.type) {
            case MULTIPLE_CHOICE_QUESTION:
                return getMcqHtml(testContent);
            case FILL_IN_QUESTION:
                return getFillInHtml(testContent);
            case ESSAY_QUESTION:
                return getEssayHtml(testContent);
            default:
                return getHeaderHtml(testContent);
        }
    }

    private String getMcqHtml(TestContent testContent) {
        AndroidTemplates loader = new AndroidTemplates(getContext());
        Theme theme = new Theme(loader);
        Chunk chunk = theme.makeChunk("mcq");
        chunk.set("question", testContent.content);

        Timber.d("getHtml: " + testContent.choices);
        SortedMap<String, String> choices = new TreeMap<>();
        char key = 'a';
        for (char i = 0; i < testContent.choices.size(); i++) {
            choices.put(String.valueOf(key), testContent.choices.get(i));
            key++;
        }

        Timber.d("getHtml: " + choices);

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

    private String getFillInHtml(TestContent testContent) {
        AndroidTemplates loader = new AndroidTemplates(getContext());
        Theme theme = new Theme(loader);
        Chunk chunk = theme.makeChunk("fillin");
        chunk.set("question", testContent.content);

        Chunk inputField = theme.makeChunk("fillin#input_field");
        chunk.set("a", inputField.toString());

        if (user.getPhotoUrl() != null) {
            chunk.set("photoUrl", "\"" + user.getPhotoUrl() + "\"");
        } else {
            chunk.set("photoUrl", "null");
        }

        return chunk.toString();
    }

    private String getEssayHtml(TestContent testContent) {
        AndroidTemplates loader = new AndroidTemplates(getContext());
        Theme theme = new Theme(loader);
        Chunk chunk = theme.makeChunk("essay");
        chunk.set("question", testContent.content);

        Chunk inputField = theme.makeChunk("fillin#input_field");
        chunk.set("a", inputField.toString());

        if (user.getPhotoUrl() != null) {
            chunk.set("photoUrl", "\"" + user.getPhotoUrl() + "\"");
        } else {
            chunk.set("photoUrl", "null");
        }

        return chunk.toString();
    }

    private String getHeaderHtml(TestContent testContent) {
        AndroidTemplates loader = new AndroidTemplates(getContext());
        Theme theme = new Theme(loader);
        Chunk chunk = theme.makeChunk("header");
        chunk.set("question", testContent.content);

        return chunk.toString();
    }

}
