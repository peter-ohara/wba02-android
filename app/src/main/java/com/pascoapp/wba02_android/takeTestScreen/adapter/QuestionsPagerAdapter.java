package com.pascoapp.wba02_android.takeTestScreen.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.takeTestScreen.TestContent;
import com.pascoapp.wba02_android.takeTestScreen.questionScreens.FillInFragment;
import com.pascoapp.wba02_android.takeTestScreen.questionScreens.TestEndFragment;

import java.util.List;
import java.util.Map;

public class QuestionsPagerAdapter extends FragmentStatePagerAdapter {

    public static final String TYPE_END_PAGE = "end_page";
    private static final String TAG = QuestionsPagerAdapter.class.getSimpleName();
    private List<TestContent> testContents;

    public QuestionsPagerAdapter(FragmentManager fm, List<TestContent> testContents) {
        super(fm);
        this.testContents = testContents;
    }

    @Override
    public Fragment getItem(int position) {
        TestContent testContent = testContents.get(position);
        Log.d(TAG, "getItem: " + testContent);
        if (testContent.type.equals("end_page")) {
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