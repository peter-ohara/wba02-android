package com.pascoapp.wba02_android.setup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pascoapp.wba02_android.AuthenticateUserActivity;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.User;

public class SetupWizardActivity extends AppCompatActivity implements
        EnterVoucherFragment.OnFragmentInteractionListener,
        ChooseSchoolFragment.OnFragmentInteractionListener,
        ChooseProgrammeFragment.OnFragmentInteractionListener,
        ChooseLevelFragment.OnFragmentInteractionListener,
        ChooseSemesterFragment.OnFragmentInteractionListener,
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

    private OnSetupChangedListener mListener;

    private DatabaseReference mDatabaseRef;


    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    public static ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    private View coordinatorLayoutView;
    private ProgressBar loadingIndicator;
    private EnterVoucherFragment enterVoucherFragment;
    private ChooseSchoolFragment chooseSchoolFragment;
    private ChooseProgrammeFragment chooseProgrammeFragment;
    private ChooseLevelFragment chooseLevelFragment;
    private ChooseSemesterFragment chooseSemesterFragment;
    private ReviewChoicesFragment reviewChoicesFragment;
    private String mSchoolKey;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_wizard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        userRef = mDatabaseRef.child("users").child(User.getUid());

        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);
        coordinatorLayoutView = findViewById(R.id.snackbarPosition);

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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case android.R.id.home:
                if (mPager.getCurrentItem() == 0) {
                    // If the user is currently looking at the first step, allow the system to handle the
                    // Back button. This calls finish() on this activity and pops the back stack.
                    return super.onOptionsItemSelected(item);
                } else {
                    // Otherwise, select the previous step.
                    mPager.setCurrentItem(mPager.getCurrentItem() - 1);
                }
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void onVoucherEntered(String voucherNumber) {
        userRef.child("voucher").setValue(voucherNumber);
        moveToNextPage();
    }

    @Override
    public void onSchoolSelected(String schoolKey) {
        mSchoolKey = schoolKey;
        userRef.child("school").setValue(mSchoolKey);
        if (chooseProgrammeFragment != null) chooseProgrammeFragment.fillProgrammeList(mSchoolKey);
        moveToNextPage();
    }

    @Override
    public void onProgrammeSelected(String programmeKey) {
        userRef.child("programme").setValue(programmeKey);
        moveToNextPage();
    }

    @Override
    public String getSchoolKey() {
        return mSchoolKey;
    }

    @Override
    public void onLevelSelected(Integer level) {
        userRef.child("level").setValue(level);
        moveToNextPage();
    }

    @Override
    public void onSemesterSelected(Integer semester) {
        userRef.child("semester").setValue(semester);
        moveToNextPage();
    }

    @Override
    public void onSubmitStudentsChoices() {
        loadingIndicator.setVisibility(View.VISIBLE);
        Intent intent = new Intent(SetupWizardActivity.this, AuthenticateUserActivity.class);
        startActivity(intent);
    }

    public void moveToNextPage() {
        mPager.setCurrentItem(mPager.getCurrentItem() + 1);
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
                    enterVoucherFragment = new EnterVoucherFragment();
                    return enterVoucherFragment;
                case CHOOSE_SCHOOL_PAGE:
                    chooseSchoolFragment = new ChooseSchoolFragment();
                    return chooseSchoolFragment;
                case CHOOSE_PROGRAMME_PAGE:
                    chooseProgrammeFragment = new ChooseProgrammeFragment();
                    return chooseProgrammeFragment;
                case CHOOSE_LEVEL_PAGE:
                    chooseLevelFragment = new ChooseLevelFragment();
                    return chooseLevelFragment;
                case CHOOSE_SEMESTER_PAGE:
                    chooseSemesterFragment = new ChooseSemesterFragment();
                    return chooseSemesterFragment;
                case REVIEW_CHOICES_PAGE:
                    reviewChoicesFragment = new ReviewChoicesFragment();
                    return reviewChoicesFragment;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

    }

    public interface OnSetupChangedListener {
        void onSchoolChanged(String schoolKey);
    }
}
