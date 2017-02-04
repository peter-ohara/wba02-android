package com.pascoapp.wba02_android.views;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.views.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by peter on 2/4/17.
 */

public abstract class ToolbarBaseActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            dataId = savedInstanceState.getInt(getExtraKeyForDataId());
        } else {
            setDataIdFromIntentExtras();
        }
        setContentView(getLayout());
        ButterKnife.bind(this);
        setupToolbar();
        setUpViewItems();
        fetchData();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }


}
