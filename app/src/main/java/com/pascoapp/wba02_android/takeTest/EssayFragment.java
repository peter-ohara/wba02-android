package com.pascoapp.wba02_android.takeTest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.firebasePojos.Question;

import java.io.Serializable;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
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
        args.putSerializable(ARG_CHOICES, (Serializable) question.getChoices());
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
    public String getAdjustedAnswer() {
        return getAnswer();
    }

}
