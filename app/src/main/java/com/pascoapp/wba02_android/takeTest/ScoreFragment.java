package com.pascoapp.wba02_android.takeTest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.Question;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScoreFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ScoreFragment extends QuestionFragment {

    private TextView scoreView;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param question id for fetching the question.
     * @return A new instance of fragment ScoreFragment.
     */
    public static ScoreFragment newInstance(Question question) {
        ScoreFragment fragment = new ScoreFragment();
        Bundle args = new Bundle();

        args.putString(ARG_TYPE, question.getType());

        fragment.setArguments(args);
        return fragment;
    }


    public ScoreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_score, container, false);

        scoreView = (TextView) view.findViewById(R.id.score_text_view);
        String scoreStr = listener.getScore() + " / " + listener.getMaxScore();
        scoreView.setText(scoreStr);

        setView(view);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void enableAnswerField() {

    }

    @Override
    public void disableAnswerField() {

    }

    @Override
    public String getStudentsAnswer() {
        return null;
    }

    @Override
    public String getAdjustedAnswer() {
        return null;
    }
}
