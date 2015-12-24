package com.pascoapp.wba02_android.takeTest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.Question;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EssayFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EssayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EssayFragment extends QuestionFragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param question id for fetching the question.
     * @return A new instance of fragment EssayFragment.
     */
    public static EssayFragment newInstance(Question question) {
        EssayFragment fragment = new EssayFragment();
        Bundle args = new Bundle();

        args.putString(ARG_QUESTION, question.getQuestion());
        args.putStringArrayList(ARG_CHOICES, (ArrayList<String>) question.getChoices());
        args.putString(ARG_ANSWER, question.getAnswer());
        args.putString(ARG_TYPE, question.getType());

        fragment.setArguments(args);
        return fragment;
    }

    public EssayFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_essay, container, false);

        setView(view);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void enableAnswerField() {
        // No AnswerField so nothing here
    }

    @Override
    public void disableAnswerField() {
        // No AnswerField so nothing here
    }

    @Override
    public String getAdjustedAnswer() {
        return getAnswer();
    }

    @Override
    public String getStudentsAnswer() {
        // Return the correct answer, thus the user will always be correct for an essay
        return getAnswer();
    }
}
