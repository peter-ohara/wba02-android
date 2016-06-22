package com.pascoapp.wba02_android.takeTest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.Question;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link McqFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class McqFragment extends QuestionFragment {


    private static final String LOG_TAG = McqFragment.class.getSimpleName();
    private Map<String, String> choices;

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
        args.putString(ARG_TYPE, question.getType());

        fragment.setArguments(args);
        return fragment;
    }

    public McqFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mcq, container, false);

        String possible_answers = "";

        choices = getChoices();
        Iterator it = choices.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            possible_answers += pair.getKey() + ". ";
            possible_answers += pair.getValue();
            possible_answers += "<br>";
            it.remove(); // avoids a ConcurrentModificationException
        }

        setChoicesString(possible_answers);

        setView(view);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public String getAdjustedAnswer() {
        String answer = getAnswer();

        Log.d(LOG_TAG, answer);
        Log.d(LOG_TAG, choices.toString());
        return choices.get(answer);
    }

}
