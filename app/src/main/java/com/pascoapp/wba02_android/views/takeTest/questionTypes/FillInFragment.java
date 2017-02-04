package com.pascoapp.wba02_android.views.takeTest.questionTypes;

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
import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.R;
import com.wang.avi.AVLoadingIndicatorView;
import com.x5.template.Chunk;
import com.x5.template.Theme;
import com.x5.template.providers.AndroidTemplates;

import java.io.Serializable;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FillInFragment extends Fragment {

    public static final String QUESTION_ERROR = "Unknown Error! Please report this testContent";

    private static final String TAG = FillInFragment.class.getSimpleName();

    // the fragment initialization parameters
    private static final String ARG_TEST_CONTENT = "com.pascoapp.wba02_android.testContent";

    // Member Variables related to the testContent
    private Map<String, Object> testContent;

    protected FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @BindView(R.id.webview)
    WebView webview;

    @BindView(R.id.loading_indicator)
    AVLoadingIndicatorView loadingIndicator;

    public static FillInFragment newInstance(Map<String, Object> testContent) {
        FillInFragment fragment = new FillInFragment();
        Bundle args = new Bundle();

        args.putSerializable(ARG_TEST_CONTENT, (Serializable) testContent);
        Log.d(TAG, "newInstance: " + testContent);
        fragment.setArguments(args);
        return fragment;
    }

    public FillInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: " + testContent);
        if (getArguments() != null) {
            testContent = (Map<String, Object>) getArguments().getSerializable(ARG_TEST_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fill_in, container, false);
        ButterKnife.bind(this, view);
        loadingIndicator.hide();

        Log.d(TAG, "onCreateView: " + testContent);
        loadItemInWebView(getContext(), webview, testContent);

        return view;
    }

    public void loadItemInWebView(Context context, WebView w, Map<String, Object> testContent) {
        w.getSettings().setJavaScriptEnabled(true);
        w.setBackgroundColor(Color.TRANSPARENT);

        DiscussionInterfaceToWebview discussionInterfaceToWebview
                = new DiscussionInterfaceToWebview(FillInFragment.this, (String) testContent.get("id"));
//        w.addJavascriptInterface(new FillInWebAppInterface(this), "FillInAndroid");
        w.addJavascriptInterface(discussionInterfaceToWebview, "Android");

        w.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                setUpCommentListener(discussionInterfaceToWebview);
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

    private void setUpCommentListener(DiscussionInterfaceToWebview discussionInterfaceToWebview) {
//        String questionKey = discussionInterfaceToWebview.getQuestionKey();
//        Query commentsQuery = Comments.COMMENTS_REF.child(questionKey);
//        ChildEventListener commentEventListener
//                = Comments.createCommentEventListener(getContext(), webview,
//                discussionInterfaceToWebview, user);
//        commentsQuery.addChildEventListener(commentEventListener);
    }

    private String getHtml(Map<String, Object> testContent) {
        AndroidTemplates loader = new AndroidTemplates(getContext());
        Theme theme = new Theme(loader);
        Chunk chunk = theme.makeChunk("fillin");
        chunk.set("question", Helpers.getOrDefault(testContent, "content", "N/A"));

        Chunk inputField = theme.makeChunk("fillin#input_field");
        chunk.set("a", inputField.toString());

        if (user.getPhotoUrl() != null) {
            chunk.set("photoUrl", "\"" + user.getPhotoUrl() + "\"");
        } else {
            chunk.set("photoUrl", "null");
        }

        return chunk.toString();
    }


}
