package com.pascoapp.wba02_android.takeTest.testContentScreens;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.pascoapp.wba02_android.R;
import com.pascoapp.wba02_android.takeTest.TakeTestActivity;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckAnswerDialogFragment extends DialogFragment {

    @BindView(R.id.barChart)
    BarChart mBarChart;
    private String title;
    private Map<String, Integer> data;
    private TakeTestActivity takeTestActivity;


    public CheckAnswerDialogFragment() {
    }


    public static CheckAnswerDialogFragment newInstance(String title, Map<String, Integer> data) {
        CheckAnswerDialogFragment frag = new CheckAnswerDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putParcelable("data", (Parcelable) data);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString("title");
            data = getArguments().getParcelable("data");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_check_answer, container);
        ButterKnife.bind(this, view);
        getDialog().setTitle(title);

        takeTestActivity = (TakeTestActivity) getActivity();

        ColorGenerator generator = ColorGenerator.DEFAULT;

        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            mBarChart.addBar(
                    new BarModel(entry.getKey(),
                            entry.getValue(),
                            generator.getColor(entry.getKey()))
            );
        }

        return view;
    }

    @OnClick(R.id.backButton)
    public void onBackButtonClick() {
        dismiss();
    }

    @OnClick(R.id.continueButton)
    public void onContinueButtonClick() {
        dismiss();
        takeTestActivity.moveToNextScreen();
    }


}
