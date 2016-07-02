package com.pascoapp.wba02_android.takeTest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pascoapp.wba02_android.firebasePojos.Question;

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
        if (currentQuestion.getType().equalsIgnoreCase("mcq")) {
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