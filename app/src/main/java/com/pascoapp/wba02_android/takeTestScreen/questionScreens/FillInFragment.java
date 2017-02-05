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
import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.takeTestScreen.TestContent;
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
    private TestContent testContent;

    protected FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    @BindView(R.id.webview)
    WebView webview;

    @BindView(R.id.loading_indicator)
    AVLoadingIndicatorView loadingIndicator;

    public static FillInFragment newInstance(TestContent testContent) {
        FillInFragment fragment = new FillInFragment();
        Bundle args = new Bundle();

        args.putParcelable(ARG_TEST_CONTENT, testContent);
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
            testContent = getArguments().getParcelable(ARG_TEST_CONTENT);
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


}
