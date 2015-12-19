package com.pascoapp.wba02_android.TakeTest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.pascoapp.wba02_android.Question.Question;
import com.pascoapp.wba02_android.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link McqFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link McqFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class McqFragment extends QuestionFragment {

    private TextView mQuestionField;
    private RadioGroup mRadioGroup;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param questionId id for fetching the question.
     * @return A new instance of fragment McqFragment.
     */
    public static McqFragment newInstance(String questionId) {
        McqFragment fragment = new McqFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUESTION_ID, questionId);
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
            setQuestionId(
                    getArguments().getString(ARG_QUESTION_ID)
            );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mcq, container, false);

        mQuestionField = (TextView) view.findViewById(R.id.question);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.choices);


        Button previousButton = (Button) view.findViewById(R.id.previous_button);
        Button nextButton = (Button) view.findViewById(R.id.next_button);

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPreviousButtonPressed();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onNextButtonPressed();
            }
        });

        ParseQuery<Question> query = Question.getQuery();
        query.getInBackground(getQuestionId(), new GetCallback<Question>() {
            @Override
            public void done(Question question, ParseException e) {
                setQuestion(question);
                mQuestionField.setText(question.getQuestion());

                for (int i = 0; i < question.getChoices().size(); i++) {
                    RadioButton radioButton = new RadioButton(getContext());
                    //radioButton.setId();
                    radioButton.setText(question.getChoices().get(i));
                    mRadioGroup.addView(radioButton);
                }
            }
        });

        return view;
    }

    public void onPreviousButtonPressed() {
        if (listener != null) {
            listener.onPrevious();
        }
    }

    public void onNextButtonPressed() {
        if (listener != null) {
            listener.onNext();
        }
    }

    @Override
    public String toString() {
        Question question = getQuestion();
        if (question != null) {
            return "Type: " + question.getType()
                    + "Question: " + question.getQuestion()
                    + "choices" + question.getChoices()
                    + "Answer: " + question.getAnswer();
        } else {
            return super.toString();
        }
    }
}
