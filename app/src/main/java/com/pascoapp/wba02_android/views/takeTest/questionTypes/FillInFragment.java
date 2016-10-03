package com.pascoapp.wba02_android.views.takeTest.questionTypes;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.TextView;

import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.services.questions.Question;
import com.pascoapp.wba02_android.views.takeTest.TakeTestActivity;
import com.x5.template.Chunk;
import com.x5.template.Theme;
import com.x5.template.providers.AndroidTemplates;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.pascoapp.wba02_android.views.takeTest.questionTypes.QuestionHelpers.loadItemInWebView;

public class FillInFragment extends Fragment {

    public static final String QUESTION_ERROR = "Unknown Error! Please report this questionKey";

    // the fragment initialization parameters
    public static final String ARG_QUESTION = "questionKey";
    public static final String ARG_ANSWER = "answer";


    // the fragment state parameters
    private static final String STATE_ANSWERED = "answered";
    private static final String STATE_ANSWERED_CORRECTLY = "answeredCorrectly";
    private static final String STATE_ANSWERED_WRONGLY = "answeredWrongly";

    // Member Variables related to the questionKey
    private String question;
    private String answer;

    // Question State Variables
    private boolean answered;
    private boolean answeredCorrectly;
    private boolean answeredWrongly;

    @BindView(R.id.webview)
    WebView webview;

    public static FillInFragment newInstance(Question question) {
        FillInFragment fragment = new FillInFragment();
        Bundle args = new Bundle();

        args.putString(ARG_QUESTION, question.getQuestion());
        args.putString(ARG_ANSWER, question.getAnswer());

        fragment.setArguments(args);
        return fragment;
    }

    public FillInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = getArguments().getString(ARG_QUESTION);
            answer = getArguments().getString(ARG_ANSWER);
        }

        if (savedInstanceState != null) {
            answered = savedInstanceState.getBoolean(STATE_ANSWERED);
            answeredCorrectly = savedInstanceState.getBoolean(STATE_ANSWERED_CORRECTLY);
            answeredWrongly = savedInstanceState.getBoolean(STATE_ANSWERED_WRONGLY);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putBoolean(STATE_ANSWERED, answered);
        outState.putBoolean(STATE_ANSWERED_CORRECTLY, answeredCorrectly);
        outState.putBoolean(STATE_ANSWERED_WRONGLY, answeredWrongly);

        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fill_in, container, false);
        ButterKnife.bind(this, view);
        loadItemInWebView(getActivity(), webview, getHtml(question));
        return view;
    }

    private void setWebviewContent(WebView webview, String question) {
        AndroidTemplates loader = new AndroidTemplates(getContext());
        Theme theme = new Theme(loader);

        // Fetch template from this file: themes/examples/hello.chtml
        // Inside that file there is a template "snippet" named #example_1
        Chunk chunk = theme.makeChunk("essay");

        chunk.set("question", question);
        String html = chunk.toString();
        loadItemInWebView(getActivity(), webview, html);
    }

    private String getHtml(String question) {
        AndroidTemplates loader = new AndroidTemplates(getContext());
        Theme theme = new Theme(loader);
        Chunk chunk = theme.makeChunk("fillin");
        chunk.set("question", question);


        Chunk inputField = theme.makeChunk("fillin#input_field");
        chunk.set("a", inputField.toString());
        return chunk.toString();
    }

}