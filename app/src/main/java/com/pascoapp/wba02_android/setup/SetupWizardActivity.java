package com.pascoapp.wba02_android.setup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.Student;

public class SetupWizardActivity extends AppCompatActivity implements
        ChooseLevelFragment.OnFragmentInteractionListener,
        ChooseProgrammeFragment.OnFragmentInteractionListener,
        ChooseSchoolFragment.OnFragmentInteractionListener,
        ChooseSemesterFragment.OnFragmentInteractionListener,
        EnterVoucherFragment.OnFragmentInteractionListener,
        ReviewChoicesFragment.OnFragmentInteractionListener {

    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 6;

    public static final String EXTRA_PAGE =
            "com.pascoapp.wba02_android.setup.SetupWizardActivity.setup_page";

    public static final int ENTER_VOUCHER_PAGE = 0;
    public static final int CHOOSE_SCHOOL_PAGE = 1;
    public static final int CHOOSE_PROGRAMME_PAGE = 2;
    public static final int CHOOSE_LEVEL_PAGE = 3;
    public static final int CHOOSE_SEMESTER_PAGE = 4;
    public static final int REVIEW_CHOICES_PAGE = 5;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    public static ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_wizard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
    public void onSubmitInteraction(Student student) {
        // TODO: Consider passing student id to AuthenticateUserActivity
        Intent intent = new Intent(SetupWizardActivity.this, AuthenticateUserActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFragmentInteraction() {
        // TODO: Next and Previous functionality
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter{
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }



        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case ENTER_VOUCHER_PAGE:
                    return EnterVoucherFragment.newInstance("abc", "abc");
                case CHOOSE_SCHOOL_PAGE:
                    return ChooseSchoolFragment.newInstance("abc", "abc");
                case CHOOSE_PROGRAMME_PAGE:
                    return ChooseProgrammeFragment.newInstance("abc", "abc");
                case CHOOSE_LEVEL_PAGE:
                    return ChooseLevelFragment.newInstance("abc", "abc");
                case CHOOSE_SEMESTER_PAGE:
                    return ChooseSemesterFragment.newInstance("abc", "abc");
                case REVIEW_CHOICES_PAGE:
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
