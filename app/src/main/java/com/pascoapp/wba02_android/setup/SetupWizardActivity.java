package com.pascoapp.wba02_android.setup;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

import com.parse.ParseException;
import com.parse.SaveCallback;
import com.pascoapp.wba02_android.AuthenticateUserActivity;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.parseSubClasses.Programme;
import com.pascoapp.wba02_android.parseSubClasses.School;
import com.pascoapp.wba02_android.parseSubClasses.Student;

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

//    private OnSetupChangesListener mListener;

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    public static ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;
    private Student student;
    private View coordinatorLayoutView;
    private ProgressBar loadingIndicator;
    private EnterVoucherFragment enterVoucherFragment;
    private ChooseSchoolFragment chooseSchoolFragment;
    private ChooseProgrammeFragment chooseProgrammeFragment;
    private ChooseLevelFragment chooseLevelFragment;
    private ChooseSemesterFragment chooseSemesterFragment;
    private ReviewChoicesFragment reviewChoicesFragment;
    private School mSchool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_wizard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadingIndicator = (ProgressBar) findViewById(R.id.loading_indicator);
        coordinatorLayoutView = findViewById(R.id.snackbarPosition);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.viewpager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        student = Student.getCurrentUser();
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
        student.setVoucher(voucherNumber);
        moveToNextPage();
    }

    @Override
    public void onSchoolSelected(School school) {
        mSchool = school;
        student.setSchool(mSchool);
        if (chooseProgrammeFragment != null) chooseProgrammeFragment.fillProgrammeList(mSchool);
        moveToNextPage();
    }

    @Override
    public void onProgrammeSelected(Programme programme) {
        student.setProgramme(programme);
        moveToNextPage();
    }

    @Override
    public School getSchool() {
        return mSchool;
    }

    @Override
    public void onLevelSelected(Integer level) {
        student.setLevel(level);
        moveToNextPage();
    }

    @Override
    public void onSemesterSelected(Integer semester) {
        student.setSemester(semester);
        moveToNextPage();
    }

    @Override
    public void onSubmitStudentsChoices(final Student student) {
        loadingIndicator.setVisibility(View.VISIBLE);

        student.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                loadingIndicator.setVisibility(View.GONE);
                if (e == null) {
                    Intent intent = new Intent(SetupWizardActivity.this, AuthenticateUserActivity.class);
                    // No need to send student along as Extra since it has been saved.
                    // It would be fetched at the other end
                    startActivity(intent);
                    // Close the Setup Wizard and prevent navigation back to it
                    finish();
                } else {
                    Snackbar.make(coordinatorLayoutView, e.getCode() + " : " + e.getMessage(),
                            Snackbar.LENGTH_INDEFINITE)
                            .setAction("Retry", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    onSubmitStudentsChoices(student);
                                }
                            }).show();
                }
            }
        });
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

//    public interface OnSetupChangedListener {
//        void onSchoolChanged(School school);
//    }
}
