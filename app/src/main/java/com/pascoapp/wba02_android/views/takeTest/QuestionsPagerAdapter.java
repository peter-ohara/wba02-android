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

    public static final String TYPE_END_PAGE = "com.pascoapp.wba02_android.endPageKey";
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
        } else if (currentQuestion.getType().equalsIgnoreCase("header")) {
            return HeaderFragment.newInstance(currentQuestion);
        } else if (currentQuestion.getType().equalsIgnoreCase(TYPE_END_PAGE)) {
            return TestEndFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return mQuestions.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Question currentQuestion = mQuestions.get(position);
        if (currentQuestion.getType().equalsIgnoreCase("header")) {
            return currentQuestion.getTitle();
        } else if (currentQuestion.getType().equalsIgnoreCase(TYPE_END_PAGE)) {
            return currentQuestion.getTitle();
        } else {
            return mQuestions.get(position).getNumber();
        }

    }
}