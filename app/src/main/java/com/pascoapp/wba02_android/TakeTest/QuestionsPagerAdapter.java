package com.pascoapp.wba02_android.TakeTest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pascoapp.wba02_android.Question.Question;

import java.util.List;

/**
 * Allows the user to flip left and right through the questions of a test
 */
class QuestionsPagerAdapter extends FragmentStatePagerAdapter {

    private List<Question> mQuestions;


    public QuestionsPagerAdapter(FragmentManager fm, List<Question> questions) {
        super(fm);
        mQuestions = questions;
    }

    @Override
    public Fragment getItem(int position) {
        Question currentQuestion = mQuestions.get(position);
        return QuestionFragmentFactory.getQuestionFragment(currentQuestion);
    }


    @Override
    public int getCount() {
        return mQuestions.size();
    }
}
