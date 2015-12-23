package com.pascoapp.wba02_android.takeTest;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.Question;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FillInFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FillInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FillInFragment extends QuestionFragment {

    private EditText mAnswerField;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param question id for fetching the question.
     * @return A new instance of fragment FillInFragment.
     */
    public static FillInFragment newInstance(Question question) {
        FillInFragment fragment = new FillInFragment();
        Bundle args = new Bundle();

        args.putString(ARG_QUESTION, question.getQuestion());
        args.putStringArrayList(ARG_CHOICES, (ArrayList<String>) question.getChoices());
        args.putString(ARG_ANSWER, question.getAnswer());
        args.putString(ARG_TYPE, question.getType());

        fragment.setArguments(args);
        return fragment;
    }

    public FillInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fill_in, container, false);

        mAnswerField = (EditText) view.findViewById(R.id.answer);
        mAnswerField.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == 42) {
                    onCheckButtonPressed();
                    dismissKeyboard();
                    handled = true;
                }
                return handled;
            }
        });

        mAnswerField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(getStudentsAnswer())) {
                    enableCheckButton();
                } else {
                    disableCheckButton();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        setView(view);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void dismissKeyboard() {
        // hide virtual keyboard
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mAnswerField.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    @Override
    public void enableAnswerField() {
        mAnswerField.setEnabled(true);
        mAnswerField.setFocusable(true);
    }

    @Override
    public void disableAnswerField() {
        mAnswerField.setEnabled(false);
        mAnswerField.setFocusable(false);
    }

    @Override
    public String getAdjustedAnswer() {
        return getAnswer();
    }

    @Override
    public String getStudentsAnswer() {
        return mAnswerField.getText().toString();
    }
}
