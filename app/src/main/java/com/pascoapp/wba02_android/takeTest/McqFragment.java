package com.pascoapp.wba02_android.takeTest;

import android.graphics.Color;
import android.graphics.Picture;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.firebasePojos.Question;

import java.io.Serializable;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link McqFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class McqFragment extends Fragment {

    private static final String LOG_TAG = McqFragment.class.getSimpleName();
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
    private String answer;

    // Question State Variables
    private boolean answered;
    private boolean answeredCorrectly;
    private boolean answeredWrongly;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param question id for fetching the question.
     * @return A new instance of fragment McqFragment.
     */
    public static McqFragment newInstance(Question question) {
        McqFragment fragment = new McqFragment();
        Bundle args = new Bundle();

        args.putString(ARG_QUESTION, question.getQuestion());
        args.putSerializable(ARG_CHOICES, (Serializable) question.getChoices());
        args.putString(ARG_ANSWER, question.getAnswer());

        fragment.setArguments(args);
        return fragment;
    }

    public McqFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = getArguments().getString(ARG_QUESTION);
            choices = (Map<String, String>) getArguments().getSerializable(ARG_CHOICES);
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mcq, container, false);


        final WebView webview = (WebView) view.findViewById(R.id.webview);
        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        TextView answerView = (TextView) view.findViewById(R.id.answer);
        final View answerArea = view.findViewById(R.id.answerArea);
        Button checkButton = (Button) view.findViewById(R.id.checkButton);

        setQuestion(webview, question);
        setChoices(radioGroup, choices);
        setAnswer(answerView, answer, choices);

        webview.setPictureListener(new WebView.PictureListener() {
            @Override
            public void onNewPicture(WebView view, Picture picture) {
                // Display the content
            }
        });

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Check the answer
                answerArea.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    private void setAnswer(TextView answerView, String answer, Map<String, String> choices) {
        answerView.setText( answer + ". " + choices.get(answer) );
    }

    private void setChoices(RadioGroup radioGroup, Map<String, String> choices) {

        for (String key: choices.keySet()) {
            String optionNumber = key;
            String option = choices.get(key);

            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setTextColor(Color.parseColor("#575757"));
            radioButton.setTextSize(20);
            radioButton.setText(optionNumber + ". " + option);
            radioGroup.addView(radioButton);
        }
    }

    private void setQuestion(WebView webview, String question) {
        String htmlString = QuestionTemplates.mcqTemplate
                .replace("{ question }", question);
        loadItemInWebView(webview, htmlString);
    }

    protected void loadItemInWebView(WebView w, String htmlString) {
        w.getSettings().setJavaScriptEnabled(true);
        w.setBackgroundColor(Color.TRANSPARENT);
        // TODO: Change "http://bar" to something more apprioprate. See documentation for loadWithBaseUrl()
        w.loadDataWithBaseURL("http://bar", htmlString, "text/html", "utf-8", "");
    }

}
