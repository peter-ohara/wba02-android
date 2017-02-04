package com.pascoapp.wba02_android.views.takeTest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.views.takeTest.questionTypes.FillInFragment;
import com.pascoapp.wba02_android.views.takeTest.questionTypes.TestEndFragment;

import java.util.List;
import java.util.Map;

public class QuestionsPagerAdapter extends FragmentStatePagerAdapter {

    public static final String TYPE_END_PAGE = "end_page";
    private static final String TAG = QuestionsPagerAdapter.class.getSimpleName();
    private List<Map<String, Object>> testContents;

    public QuestionsPagerAdapter(FragmentManager fm, List<Map<String, Object>> testContents) {
        super(fm);
        this.testContents = testContents;
    }

    @Override
    public Fragment getItem(int position) {
        Map<String, Object> testContent = testContents.get(position);
        Log.d(TAG, "getItem: " + testContent);
        if (testContent.get("type").equals("end_page")) {
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
        Map<String, Object> testContent = testContents.get(position);
        return (String) Helpers.getOrDefault(testContent, "title", "");
    }
}