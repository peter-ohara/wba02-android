package com.pascoapp.wba02_android;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pascoapp.wba02_android.Question.Question;

import java.util.List;

/**
 * Created by peter on 12/19/15.
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
        String questionId = currentQuestion.getObjectId();
        return QuestionFragment.newInstance(questionId);
    }


    @Override
    public int getCount() {
        return mQuestions.size();
    }
}
