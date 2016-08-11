package com.pascoapp.wba02_android.views.takeTest.question;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Picture;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.services.questions.Question;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @BindView(R.id.webview) WebView webview;

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
        setQuestion(webview, question);
        return view;
    }

    private void setAnswer(TextView answerView, String answer) {
        answerView.setText( answer );
    }

    private void setQuestion(WebView webview, String question) {
        String htmlString = QuestionTemplates.fillInTemplate
                .replace("{ questionKey }", question);
        loadItemInWebView(webview, htmlString);
    }

    protected void loadItemInWebView(WebView w, String htmlString) {
        w.getSettings().setJavaScriptEnabled(true);
        w.addJavascriptInterface(new WebAppInterface(getContext()), "Android");
        w.setBackgroundColor(Color.TRANSPARENT);
        // TODO: Change "http://bar" to something more apprioprate. See documentation for loadWithBaseUrl()
        w.loadDataWithBaseURL("http://bar", htmlString, "text/html", "utf-8", "");
    }

    public class WebAppInterface {
        Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void openDiscussionScreen() {
            Toast.makeText(mContext, "Discussion Screen Opened", Toast.LENGTH_SHORT).show();
        }
    }

}
