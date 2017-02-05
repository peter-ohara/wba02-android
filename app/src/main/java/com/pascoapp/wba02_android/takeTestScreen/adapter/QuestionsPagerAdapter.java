package com.pascoapp.wba02_android.takeTestScreen.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.pascoapp.wba02_android.takeTestScreen.TestContent;
import com.pascoapp.wba02_android.takeTestScreen.questionScreens.EssayFragment;
import com.pascoapp.wba02_android.takeTestScreen.questionScreens.FillInFragment;
import com.pascoapp.wba02_android.takeTestScreen.questionScreens.HeaderFragment;
import com.pascoapp.wba02_android.takeTestScreen.questionScreens.MulitpleChoiceFragment;
import com.pascoapp.wba02_android.takeTestScreen.questionScreens.TestEndFragment;

import java.util.List;

public class QuestionsPagerAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = QuestionsPagerAdapter.class.getSimpleName();
    public static final String MULTIPLE_CHOICE_QUESTION = "multiple_choice_question";
    public static final String FILL_IN_QUESTION = "fill_in_question";
    public static final String ESSAY_QUESTION = "essay_question";
    public static final String HEADER = "header";
    public static final String TEST_END = "test_end";

    private List<TestContent> testContents;

    public QuestionsPagerAdapter(FragmentManager fm, List<TestContent> testContents) {
        super(fm);
        this.testContents = testContents;
    }

    @Override
    public Fragment getItem(int position) {
        TestContent testContent = testContents.get(position);
        Log.d(TAG, "getItem: " + testContent);
        if (testContent.type.equals(MULTIPLE_CHOICE_QUESTION)) {
            return MulitpleChoiceFragment.newInstance(testContent);
        } else if (testContent.type.equals(FILL_IN_QUESTION)) {
            return FillInFragment.newInstance(testContent);
        } else if (testContent.type.equals(ESSAY_QUESTION)) {
            return EssayFragment.newInstance(testContent);
        } else if (testContent.type.equals(HEADER)) {
            return HeaderFragment.newInstance(testContent);
        } else if (testContent.type.equals(TEST_END)) {
            return TestEndFragment.newInstance();
        } else {
            return FillInFragment.newInstance(testContent);
        }
    }

    @Override
    public int getCount() {
        return testContents.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        TestContent testContent = testContents.get(position);
        return testContent.title;
    }
}