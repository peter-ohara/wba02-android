package com.pascoapp.wba02_android.takeTest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.pascoapp.wba02_android.R;

import java.util.ArrayList;
import java.util.Map;


/**
 *
 */
public abstract class QuestionFragment extends Fragment {

    public static final String QUESTION_ERROR = "Unknown Error! Please report this question";

    // the fragment initialization parameters
    public static final String ARG_QUESTION = "question";
    public static final String ARG_CHOICES = "choices";
    public static final String ARG_ANSWER = "answer";
    public static final String ARG_TYPE = "questionType";

    // the fragment state parameters
    private static final String STATE_ANSWERED = "answered";
    private static final String STATE_ANSWERED_CORRECTLY = "answeredCorrectly";
    private static final String STATE_ANSWERED_WRONGLY = "answeredWrongly";

    // Member Variables related to the question
    private String question;
    private Map<String, String>choices;
    private String choicesString = "";
    private String answer;
    private String questionType;

    // Question State Variables
    private boolean answered;
    private boolean answeredCorrectly;
    private boolean answeredWrongly;

    // UI elements
    private View view;

    // TODO: Encapsulate this
    private WebView webview;

    @android.support.annotation.Nullable
    @Override
    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Map<String, String> getChoices() {
        return choices;
    }

    public String getAnswer() {
        return answer;
    }

    public String getChoicesString() {
        return choicesString;
    }

    public void setChoicesString(String choicesString) {
        this.choicesString = choicesString;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = getArguments().getString(ARG_QUESTION);
            choices = (Map<String, String>) getArguments().getSerializable(ARG_CHOICES);
            answer = getArguments().getString(ARG_ANSWER);
            questionType = getArguments().getString(ARG_TYPE);
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

        webview = (WebView) view.findViewById(R.id.webview);

        loadItemInWebView(
                getQuestion(), getChoicesString(),
                getAnswer(), getAdjustedAnswer(), questionType, webview);

        return view;
    }

    @Override
    public String toString() {
        return "Type: " + questionType
                + "Question: " + question
                + "Answer: " + getAdjustedAnswer();
    }

    protected void loadItemInWebView(String question, String choices, String answer,
                                     String adjustedAnswer, String questionType, WebView w) {

        String answerPrefix;
        if (questionType.equalsIgnoreCase("mcq")) answerPrefix = answer + ".";
        else answerPrefix = "";

        String HTMLString = String.format(
                "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<title>%s</title>" +
                "<style>"                           +
                "    h3 {"                          +
                "        text-align:center;"        +
                "        font-weight:100;"          +
                "    }"                             +
                "    a {"                           +
                "        text-decoration: none;"    +
                "        color: #ff0093;"           +
                "    }"                             +
                "    hr {"                          +
                "        border: 0;"                +
                "        height: 0;"                +
                "        border-top: 1px solid rgba(0, 0, 0, 0.1);"             +
                "        border-bottom: 1px solid rgba(255, 255, 255, 0.3);"    +
                "    }"                             +
                "</style>"                          +
                "<script type=\"text/javascript\" async " +
                "  src=\"file:///android_asset/MathJax/MathJax.js?config=AM_CHTML-full\"></script>" +
                "</head>" +
                "<body>" +
                "<p>%s</p>" +
                "<p>%s</p>" +
                "<p>Answer:</p>" +
                "<p>%s %s</p>" +
                "</body>" +
                "</html>", question, question, choices, answerPrefix, adjustedAnswer);

        w.getSettings().setJavaScriptEnabled(true);
        w.loadDataWithBaseURL("http://bar", HTMLString, "text/html", "utf-8", "");
    }

    public abstract String getAdjustedAnswer();
}
