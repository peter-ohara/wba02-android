package com.pascoapp.wba02_android.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.gson.JsonObject;
import com.pascoapp.wba02_android.Helpers;
import com.pascoapp.wba02_android.services.APIUtils;
import com.pascoapp.wba02_android.services.TakeTestService;
import com.pascoapp.wba02_android.services.courses.CourseService;
import com.pascoapp.wba02_android.services.tests.Test;

import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class BaseActivity extends AppCompatActivity {

    public final String TAG = BaseActivity.class.getSimpleName();

    protected Integer dataId;
    protected JsonObject data;

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
        setUpViewItems();
        fetchData();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(getExtraKeyForDataId(), dataId);
    }

    protected abstract int getLayout();
    protected abstract String getApiEndPoint();
    protected abstract String getExtraKeyForDataId();

    protected void setDataIdFromIntentExtras() {
        Intent intent = getIntent();
        dataId = intent.getIntExtra(getExtraKeyForDataId(), -1);
    }

    protected abstract void setUpViewItems();

    protected void fetchData() {
        if (dataId != -1) {
            fetchDataWithId();
            return;
        }

        beforeFetchingData();

        Log.d(TAG, "fetchingData at [" + getApiEndPoint() + "]");

        APIUtils.getDataService().getData(getApiEndPoint())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fetchedData -> {
                    this.data = fetchedData;
                    afterFetchingData(fetchedData);
                }, throwable -> {
                    onErrorFetchingData(throwable);
                    Helpers.logTheError(TAG, throwable);
                });
    }

    protected void fetchDataWithId() {
        beforeFetchingData();

        Log.d(TAG, "fetchingData at [" + getApiEndPoint() + "]with dataId = " + dataId);

        APIUtils.getDataService().getData(getApiEndPoint(), dataId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(fetchedData -> {
                    this.data = fetchedData;
                    afterFetchingData(fetchedData);
                }, throwable -> {
                    onErrorFetchingData(throwable);
                    Helpers.logTheError(TAG, throwable);
                });
    }

    protected abstract void beforeFetchingData();
    protected abstract void afterFetchingData(JsonObject data);
    protected abstract void onErrorFetchingData(Throwable e);

    public int getDataId() {
        return dataId;
    }

    public Object getData() {
        return data;
    }
}
