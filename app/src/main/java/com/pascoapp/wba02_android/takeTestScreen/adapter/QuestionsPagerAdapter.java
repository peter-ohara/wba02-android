package com.pascoapp.wba02_android.takeTestScreen.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.pascoapp.wba02_android.takeTestScreen.TestContent;
import com.pascoapp.wba02_android.takeTestScreen.testContentScreens.TestContentFragment;
import com.pascoapp.wba02_android.takeTestScreen.testContentScreens.TestEndFragment;

import java.util.List;

import timber.log.Timber;

public class QuestionsPagerAdapter extends FragmentStatePagerAdapter {

    public static final String TEST_END = "quiz_end";

    private List<TestContent> testContents;

    public QuestionsPagerAdapter(FragmentManager fm,
                                 List<TestContent> testContents) {
        super(fm);
        this.testContents = testContents;
    }

    @Override
    public Fragment getItem(int position) {
        TestContent testContent = testContents.get(position);
        Timber.d("getItem: " + testContent);
        if (testContent.type.equals(TEST_END)) {
            return TestEndFragment.newInstance();
        } else {
            return TestContentFragment.newInstance(testContent);
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