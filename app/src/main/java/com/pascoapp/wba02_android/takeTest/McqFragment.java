package com.pascoapp.wba02_android.takeTest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.Question;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link McqFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link McqFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class McqFragment extends QuestionFragment {

    private TextView mChoices;


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
        args.putStringArrayList(ARG_CHOICES, (ArrayList<String>) question.getChoices());
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

        mChoices = (TextView) view.findViewById(R.id.choices);

        String possible_answers = "";
        String[] indexes = new String[]{"a", "b", "c", "d", "e"};

        ArrayList<String> choices = getChoices();
        for (int i = 0; i < choices.size(); i++) {
            possible_answers += indexes[i] + ". ";
            possible_answers += choices.get(i);
            possible_answers += "\n";
        }

        mChoices.setText(possible_answers);

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
    public String getAdjustedAnswer() {
        String answer = getAnswer();

        if (answer.equalsIgnoreCase("a")) {
            return getChoices().get(0);
        } else if (answer.equalsIgnoreCase("b")) {
            return getChoices().get(1);
        } else if (answer.equalsIgnoreCase("c")) {
            return getChoices().get(2);
        } else if (answer.equalsIgnoreCase("d")) {
            return getChoices().get(3);
        } else if (answer.equalsIgnoreCase("e")) {
            return getChoices().get(4);
        } else {
            return QUESTION_ERROR;
        }
    }

    @Override
    public String getStudentsAnswer() {
        return null;
    }
}
