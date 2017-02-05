package com.pascoapp.wba02_android.takeTestScreen.questionScreens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.takeTestScreen.TakeTestActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestEndFragment extends Fragment {

    @BindView(R.id.endTestButton) Button endTestButton;

    public static TestEndFragment newInstance() {
        TestEndFragment fragment = new TestEndFragment();
        return fragment;
    }

    public TestEndFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_end, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.endTestButton)
    public void moveToPreviousScreen(View view) {
        ((TakeTestActivity) getActivity()).quit();
    }

}
