package com.pascoapp.wba02_android.views.takeTest.questionTypes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.services.questions.Question;
import com.x5.template.Chunk;
import com.x5.template.Theme;
import com.x5.template.providers.AndroidTemplates;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pascoapp.wba02_android.views.takeTest.questionTypes.QuestionHelpers.loadItemInWebView;

public class HeaderFragment extends Fragment {

    // the fragment initialization parameters
    public static final String ARG_CONTENT = "contentKey";
    private static final String ARG_TITLE = "titleKey";

    private String title;
    private String content;

    @BindView(R.id.webview)
    WebView webview;

    public static HeaderFragment newInstance(Question question) {
        HeaderFragment fragment = new HeaderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CONTENT, question.getContent());
        args.putString(ARG_TITLE, question.getTitle());
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
            title = getArguments().getString(ARG_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_header, container, false);
        ButterKnife.bind(this, view);
        loadItemInWebView(getActivity(), webview, getHtml(title, content), "");
        return view;
    }

    private String getHtml(String title, String content) {
        AndroidTemplates loader = new AndroidTemplates(getContext());
        Theme theme = new Theme(loader);
        Chunk chunk = theme.makeChunk("header");
        chunk.set("title", title);
        chunk.set("content", content);
        return chunk.toString();
    }

}
