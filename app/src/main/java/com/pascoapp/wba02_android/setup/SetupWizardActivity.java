package com.pascoapp.wba02_android.setup;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.pascoapp.wba02_android.R;

public class SetupWizardActivity extends FragmentActivity implements
        ChooseLevelFragment.OnFragmentInteractionListener,
        ChooseProgrammeFragment.OnFragmentInteractionListener,
        ChooseSchoolFragment.OnFragmentInteractionListener,
        ChooseSemesterFragment.OnFragmentInteractionListener,
        EnterVoucherFragment.OnFragmentInteractionListener,
        ReviewChoicesFragment.OnFragmentInteractionListener {

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_voucher);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.viewpager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return EnterVoucherFragment.newInstance("abc", "abc");
                case 1:
                    return ChooseSchoolFragment.newInstance("abc", "abc");
                case 2:
                    return ChooseProgrammeFragment.newInstance("abc", "abc");
                case 3:
                    return ChooseLevelFragment.newInstance("abc", "abc");
                case 4:
                    return ChooseSemesterFragment.newInstance("abc", "abc");
                case 5:
                    return ReviewChoicesFragment.newInstance("abc", "abc");
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }
}
