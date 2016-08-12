package com.pascoapp.wba02_android.views.takeTest.questionTypes;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.services.questions.Question;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeaderFragment extends Fragment {

    // the fragment initialization parameters
    public static final String ARG_CONTENT = "contentKey";

    private String content;

    @BindView(R.id.webview) WebView webview;

    public static HeaderFragment newInstance(Question question) {
        HeaderFragment fragment = new HeaderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CONTENT, question.getContent());
        fragment.setArguments(args);
        return fragment;
    }

    public HeaderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            content = getArguments().getString(ARG_CONTENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_header, container, false);
        ButterKnife.bind(this, view);
        setContent(webview, content);
        return view;
    }

    private void setContent(WebView webview, String content) {
        String htmlString = QuestionTemplates.headerTemplate
                .replace("{ content }", content);
        loadItemInWebView(webview, htmlString);
    }

    protected void loadItemInWebView(WebView w, String htmlString) {
        w.getSettings().setJavaScriptEnabled(true);
        w.setBackgroundColor(Color.TRANSPARENT);
        // TODO: Change "http://bar" to something more apprioprate. See documentation for loadWithBaseUrl()
        w.loadDataWithBaseURL("http://bar", htmlString, "text/html", "utf-8", "");
    }


}
