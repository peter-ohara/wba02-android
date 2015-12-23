package com.pascoapp.wba02_android.takeTest;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pascoapp.wba02_android.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
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
    private ArrayList<String> choices;
    private String answer;
    private String questionType;

    // Question State Variables
    private boolean answered;
    private boolean answeredCorrectly;
    private boolean answeredWrongly;

    // UI elements
    private View view;
    private TextView mQuestionField;
    private View messageView;
    private TextView correctAnswerMessageTextView;
    private TextView wrongAnswerMessageTextView;
    private TextView correctionTextView;
    private Button checkButton;

    private OnFragmentInteractionListener listener;

    @android.support.annotation.Nullable
    @Override
    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public Button getCheckButton() {
        return checkButton;
    }

    public void setCheckButton(Button checkButton) {
        this.checkButton = checkButton;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            question = getArguments().getString(ARG_QUESTION);
            choices = getArguments().getStringArrayList(ARG_CHOICES);
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

        mQuestionField = (TextView) view.findViewById(R.id.question);
        mQuestionField.setText(question);

        if (!questionType.equalsIgnoreCase("essay")) {
            // Essays are do not have states so all the
            messageView = view.findViewById(R.id.message_view);
            correctAnswerMessageTextView = (TextView) view.findViewById(R.id.correct_answer_message);
            wrongAnswerMessageTextView = (TextView) view.findViewById(R.id.wrong_answer_message);
            correctionTextView = (TextView) view.findViewById(R.id.correction);

            checkButton = (Button) view.findViewById(R.id.check_answer_button);

            if (answered) {
                setAnsweredState();
                if (answeredCorrectly) setAnsweredCorrectlyState();
                else if (answeredWrongly) setAnsweredWronglyState();
            } else {
                setUnAnsweredState();
            }
        }

        return view;
    }

    private void setUnAnsweredState() {
        answered = false;
        answeredCorrectly = false;
        answeredWrongly = false;

        messageView.setVisibility(View.GONE);

        enableAnswerField();

        checkButton.setText("Check");
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckButtonPressed();
            }
        });
    }

    private void setAnsweredState() {
        answered = true;

        messageView.setVisibility(View.VISIBLE);

        disableAnswerField();

        checkButton.setText("Continue");
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onContinueButtonPressed();
            }
        });
    }

    private void setAnsweredCorrectlyState() {
        answeredCorrectly = true;
        displayCorrectMessage();
    }

    private void setAnsweredWronglyState() {
        answeredWrongly = true;
        displayWrongMessage();
    }

    private void displayCorrectMessage() {
        messageView.setBackgroundColor(Color.parseColor("#def0a5"));

        correctAnswerMessageTextView.setText("You are correct!.");
        wrongAnswerMessageTextView.setText("");
        correctionTextView.setText("");
    }

    private void displayWrongMessage() {
        messageView.setBackgroundColor(Color.parseColor("#ffd4cc"));

        correctAnswerMessageTextView.setText("");
        wrongAnswerMessageTextView.setText("Oops, that's not correct.");
        correctionTextView.setText(getAdjustedAnswer());
    }

    public void onCheckButtonPressed() {
        setAnsweredState();

        if (getAdjustedAnswer().equalsIgnoreCase(getStudentsAnswer())) {
            setAnsweredCorrectlyState();
        } else {
            setAnsweredWronglyState();
        }
    }

    public void onContinueButtonPressed() {
        if (listener != null) {
            listener.onNext();
        }
    }

    public void enableCheckButton() {
        checkButton.setEnabled(true);
        checkButton.setFocusable(true);
        checkButton.setTextColor(Color.WHITE);
        checkButton.setBackgroundColor(Color.parseColor("#7eb530"));
    }

    public void disableCheckButton() {
        checkButton.setEnabled(false);
        checkButton.setFocusable(false);
        checkButton.setTextColor(Color.parseColor("#1A000000"));
        checkButton.setBackgroundColor(Color.parseColor("#40000000"));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public String toString() {
        return "Type: " + questionType
                + "Question: " + question
                + "Answer: " + getAdjustedAnswer();
    }

    public abstract void enableAnswerField();

    public abstract void disableAnswerField();

    public abstract String getStudentsAnswer();

    public abstract String getAdjustedAnswer();

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void onPrevious();

        void onNext();
    }
}
