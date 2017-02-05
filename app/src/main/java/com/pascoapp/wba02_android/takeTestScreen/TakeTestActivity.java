package com.pascoapp.wba02_android.takeTestScreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.pascoapp.wba02_android.APIUtils;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.takeTestScreen.adapter.QuestionsPagerAdapter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by peter on 2/5/17.
 */

public class TakeTestActivity extends AppCompatActivity {

    public static final String EXTRA_TEST_ID = "com.pascoapp.wba02_android.testKey";

    public static String POSITION = "POSITION";

    private List<TestContent> testContents = new ArrayList<>();
    private PagerAdapter mPagerAdapter;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.loading_indicator) AVLoadingIndicatorView loadingIndicator;
    @BindView(R.id.snackbarPosition) CoordinatorLayout coordinatorLayout;
    @BindView(R.id.sliding_tabs) TabLayout tabLayout;
    @BindView(R.id.viewpager) ViewPager mPager;

    private TakeTestItem data;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_test);
        ButterKnife.bind(this);

        setupToolbar();

        setupViewPager();

        refreshData(getTestId());
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION, tabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // TODO: Figure out why this call doesn't work and as a consequence
        // changing screen orientation does not preserve the users position
        Timber.d("onRestoreInstanceState: " + mPager.getCurrentItem());
        Timber.d("onRestoreInstanceState: " + savedInstanceState.getInt(POSITION));
        mPager.setCurrentItem(savedInstanceState.getInt(POSITION));
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        setTitle("");
    }

    private void setupViewPager() {
        mPager.setOffscreenPageLimit(10);
        mPagerAdapter = new QuestionsPagerAdapter(getSupportFragmentManager(), testContents);
        mPager.setAdapter(mPagerAdapter);
        // Give the TabLayout the ViewPager
        tabLayout.setupWithViewPager(mPager);
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public Integer getTestId() {
        Intent intent = getIntent();
        return intent.getIntExtra(EXTRA_TEST_ID, 0);
    }

    private void refreshData(Integer dataId) {
        loadingIndicator.show();

        Timber.d("fetchData: testId = " + dataId);

        APIUtils.getPascoService(com.pascoapp.wba02_android.takeTestScreen.TakeTestService.class).getData(dataId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fetchedData -> {
                    loadingIndicator.hide();

                    this.data = fetchedData;

                    setTitle(data.title);
                    testContents.addAll(data.testContents);

                    TestContent endPageData = TestContent.createEndPage();
                    testContents.add(endPageData);

                    mPagerAdapter.notifyDataSetChanged();
                }, e -> {
                    loadingIndicator.hide();
                    Snackbar.make(coordinatorLayout, e.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Retry", view -> refreshData(dataId))
                            .show();
                    e.printStackTrace();
                });
    }

    public void moveToPreviousScreen() {
        if (mPager.getCurrentItem() == 0) {
            quit();
        } else {
            // Show the previous question
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public void moveToNextScreen() {
        if (mPager.getCurrentItem() == mPagerAdapter.getCount() - 1) {
            Toast.makeText(this, "Should Quit", Toast.LENGTH_SHORT).show();
        } else {
            // Show the previous question
            mPager.setCurrentItem(mPager.getCurrentItem() + 1);
        }
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            quit();
        } else {
            // Show the previous question
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public void quit() {
        super.onBackPressed();
    }
}
