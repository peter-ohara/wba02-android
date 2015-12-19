package com.pascoapp.wba02_android;

import android.content.Context;
import android.net.Uri;
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


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link QuestionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link QuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class QuestionFragment extends Fragment {

    // the fragment initialization parameters
    private static final String ARG_QUESTION_ID = "questionId";

    private String mQuestionId;

    private OnFragmentInteractionListener mListener;
    private Question mQuestion;
    private TextView mQuestionField;
    private RadioGroup mRadioGroup;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param questionId id for fetching the question.
     * @return A new instance of fragment QuestionFragment.
     */
    public static QuestionFragment newInstance(String questionId) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUESTION_ID, questionId);
        fragment.setArguments(args);
        return fragment;
    }
    public QuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQuestionId = getArguments().getString(ARG_QUESTION_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_question, container, false);

        mQuestionField = (TextView) view.findViewById(R.id.question);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.radiogroup);


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
        query.getInBackground(mQuestionId, new GetCallback<Question>() {
            @Override
            public void done(Question question, ParseException e) {
                mQuestion = question;
                mQuestionField.setText(mQuestion.getQuestion());

                for (int i = 0; i < mQuestion.getChoices().size(); i++) {
                    RadioButton radioButton = new RadioButton(getContext());
                    //radioButton.setId();
                    radioButton.setText(mQuestion.getChoices().get(i));
                    mRadioGroup.addView(radioButton);
                }
            }
        });

        return view;
    }

    public void onPreviousButtonPressed() {
        if (mListener != null) {
            mListener.onPrevious();
        }
    }

    public void onNextButtonPressed() {
        if (mListener != null) {
            mListener.onNext();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void onPrevious();

        void onNext();
    }
}
