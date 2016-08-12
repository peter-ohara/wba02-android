package com.pascoapp.wba02_android.views.takeTest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pascoapp.wba02_android.services.questions.Question;
import com.pascoapp.wba02_android.views.takeTest.questionTypes.EssayFragment;
import com.pascoapp.wba02_android.views.takeTest.questionTypes.FillInFragment;
import com.pascoapp.wba02_android.views.takeTest.questionTypes.HeaderFragment;
import com.pascoapp.wba02_android.views.takeTest.questionTypes.McqFragment;
import com.pascoapp.wba02_android.views.takeTest.questionTypes.TestEndFragment;

import java.util.List;

public class QuestionsPagerAdapter extends FragmentStatePagerAdapter {

    private List<Question> mQuestions;

    public QuestionsPagerAdapter(FragmentManager fm, List<Question> questions) {
        super(fm);
        mQuestions = questions;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == mQuestions.size()) {
            return TestEndFragment.newInstance();
        }

        Question currentQuestion = mQuestions.get(position);
        if (currentQuestion.getType().equalsIgnoreCase("mcq")) {
            return McqFragment.newInstance(currentQuestion);
        } else if (currentQuestion.getType().equalsIgnoreCase("fillIn")) {
            return FillInFragment.newInstance(currentQuestion);
        } else if (currentQuestion.getType().equalsIgnoreCase("essay")) {
            return EssayFragment.newInstance(currentQuestion);
        } else if (currentQuestion.getType().equalsIgnoreCase("header")) {
            return HeaderFragment.newInstance(currentQuestion);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (mQuestions.size() == 0) {
            return 0;
        } else {
            return mQuestions.size() + 1;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == mQuestions.size()) {
            return "End";
        }

        Question currentQuestion = mQuestions.get(position);
        if (currentQuestion.getType().equalsIgnoreCase("header")) {
            return "Header";
        } else {
            return mQuestions.get(position).getNumber();
        }

    }
}