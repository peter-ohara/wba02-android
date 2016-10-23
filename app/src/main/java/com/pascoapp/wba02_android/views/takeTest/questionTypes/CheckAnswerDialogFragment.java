package com.pascoapp.wba02_android.views.takeTest.questionTypes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.pascoapp.wba02_android.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by peter on 10/22/16.
 */
public class CheckAnswerDialogFragment extends DialogFragment {

    @BindView(R.id.pieChart)
    PieChart pieChart;
    private String title;
    private Map<String, Integer> data;


    public CheckAnswerDialogFragment() {
    }


    public static CheckAnswerDialogFragment newInstance(String title, Map<String, Integer> data) {
        CheckAnswerDialogFragment frag = new CheckAnswerDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putSerializable("data", (Serializable) data);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString("title");
            data = (Map<String, Integer>) getArguments().getSerializable("data");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_check_answer, container);
        ButterKnife.bind(this, view);
        getDialog().setTitle(title);

        // PieChart stuff
        List<PieEntry> entries = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : data.entrySet())
        {
            entries.add(new PieEntry(entry.getValue(), entry.getKey()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Who chose what");
//        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setColors(new int[] {
                ColorTemplate.MATERIAL_COLORS[0],
                ColorTemplate.MATERIAL_COLORS[1],
                ColorTemplate.MATERIAL_COLORS[2],
                ColorTemplate.MATERIAL_COLORS[3],
        });
        PieData pieData = new PieData(dataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();  // refresh

        return view;
    }
}
