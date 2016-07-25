package com.pascoapp.wba02_android;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by peter on 7/25/16.
 */
public class BindingHolder extends RecyclerView.ViewHolder {
    private ViewDataBinding binding;

    public BindingHolder(View v) {
        super(v);
        binding = DataBindingUtil.bind(v);
    }

    public ViewDataBinding getBinding() {
        return binding;
    }
}
