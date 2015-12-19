package com.pascoapp.wba02_android.TakeTest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.pascoapp.wba02_android.Question.Question;
import com.pascoapp.wba02_android.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FillInFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FillInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FillInFragment extends QuestionFragment {

    private TextView mQuestionField;
    private EditText mAnswerField;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param questionId id for fetching the question.
     * @return A new instance of fragment FillInFragment.
     */
    public static FillInFragment newInstance(String questionId) {
        FillInFragment fragment = new FillInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUESTION_ID, questionId);
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
            setQuestionId(
                    getArguments().getString(ARG_QUESTION_ID)
            );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fill_in, container, false);

        mQuestionField = (TextView) view.findViewById(R.id.question);
        mAnswerField = (EditText) view.findViewById(R.id.answer);


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
}
