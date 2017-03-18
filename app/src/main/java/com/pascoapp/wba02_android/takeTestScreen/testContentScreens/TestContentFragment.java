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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pascoapp.wba02_android.APIUtils;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.WebviewActivity;
import com.pascoapp.wba02_android.takeTestScreen.TestContent;
import com.wang.avi.AVLoadingIndicatorView;
import com.x5.template.Chunk;
import com.x5.template.Theme;
import com.x5.template.providers.AndroidTemplates;

import java.util.SortedMap;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import timber.log.Timber;

public class TestContentFragment extends Fragment {

    public static final String MULTIPLE_CHOICE_QUESTION = "multiple_choice_question";
    public static final String FILL_IN_QUESTION = "fill_in_question";
    public static final String ESSAY_QUESTION = "essay_question";
    public static final String HEADER = "header";

    // the fragment initialization parameters
    private static final String ARG_TEST_CONTENT = "com.pascoapp.wba02_android.quizContent";

    // Member Variables related to the testContent
    private TestContent testContent;

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

        view = inflater.inflate(R.layout.fragment_mcq, container, false);

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
        AndroidTemplates loader = new AndroidTemplates(getContext());
        Theme theme = new Theme(loader);

        Chunk chunk = theme.makeChunk("header");

        switch (testContent.type) {
            case MULTIPLE_CHOICE_QUESTION:
                chunk = theme.makeChunk("mcq");
                Timber.d("getHtml: " + testContent.choices);
                SortedMap<String, String> choices = new TreeMap<>();
                char key = 'a';
                for (char i = 0; i < testContent.choices.size(); i++) {
                    choices.put(String.valueOf(key), testContent.choices.get(i));
                    key++;
                }

                Timber.d("getHtml: " + choices);

                chunk.set("choices", choices);
                break;
            case FILL_IN_QUESTION:
                chunk = theme.makeChunk("fillin");
                Chunk inputField = theme.makeChunk("fillin#input_field");
                chunk.set("a", inputField.toString());
                break;
            case ESSAY_QUESTION:
                chunk = theme.makeChunk("essay");
                break;
        }

        chunk.set("question", testContent.content);

        Gson gson = new GsonBuilder().create();
        String commentsJson = gson.toJson(testContent.comments);
        chunk.set("comments", commentsJson);

        Timber.d("getHtml: " + testContent.type);
        chunk.set("BASE_URL", APIUtils.BASE_URL);
        chunk.set("commentableType", testContent.type);
        chunk.set("commentableId", testContent.id);


//        if (user.getPhotoUrl() != null) {
//            chunk.set("photoUrl", "\"" + user.getPhotoUrl() + "\"");
//        } else {
//            chunk.set("photoUrl", "null");
//        }

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
