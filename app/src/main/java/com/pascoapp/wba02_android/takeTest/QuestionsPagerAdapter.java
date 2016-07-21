package com.pascoapp.wba02_android.takeTest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pascoapp.wba02_android.TestOverviewFragment;
import com.pascoapp.wba02_android.firebasePojos.Question;
import com.pascoapp.wba02_android.firebasePojos.Test;

import java.util.List;

/**
 * Allows the user to flip left and right through the questions of a test
 */
class QuestionsPagerAdapter extends FragmentStatePagerAdapter {

    private final Test mTest;
    private List<Question> mQuestions;

    public QuestionsPagerAdapter(FragmentManager fm, Test test, List<Question> questions) {
        super(fm);
        mTest = test;
        mQuestions = questions;
    }

    @Override
    public Fragment getItem(int position) {
        Question currentQuestion = mQuestions.get(position);
        if (position == 0) {
            return TestOverviewFragment.newInstance(mTest);
        } else if (currentQuestion.getType().equalsIgnoreCase("mcq")) {
            return McqFragment.newInstance(currentQuestion);
        } else if (currentQuestion.getType().equalsIgnoreCase("fillIn")) {
            return FillInFragment.newInstance(currentQuestion);
        } else if (currentQuestion.getType().equalsIgnoreCase("essay")) {
            return EssayFragment.newInstance(currentQuestion);
        }
        return null;
    }

    @Override
    public int getCount() {
        return mQuestions.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Question " + mQuestions.get(position).getNumber();
    }
}