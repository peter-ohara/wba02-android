package com.pascoapp.wba02_android.views.takeTest.questionTypes;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.pascoapp.wba02_android.R;

import java.io.Serializable;
import java.text.DecimalFormat;
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

        PieDataSet dataSet = new PieDataSet(entries, "Correct answer: " + "d");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.WHITE);
        // pieData.setValueTypeface(mTfLight);

        pieChart.setData(pieData);
        pieChart.setHighlightPerTapEnabled(true);
//        pieChart.getLegend().setEnabled(false);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);


        // entry label styling
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTextSize(24f);
        // pieChart.setEntryLabelTypeface(mTfRegular);
        pieChart.getDescription().setText("Answer Distribution");
        pieChart.getDescription().setTextSize(20f);

        pieChart.invalidate();  // refresh

        return view;
    }


    public class PercentFormatter implements IValueFormatter, IAxisValueFormatter
    {

        protected DecimalFormat mFormat;

        public PercentFormatter() {
            mFormat = new DecimalFormat("###,###,###");
        }

        /**
         * Allow a custom decimalformat
         *
         * @param format
         */
        public PercentFormatter(DecimalFormat format) {
            this.mFormat = format;
        }

        // IValueFormatter
        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormat.format(value);
        }

        // IAxisValueFormatter
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mFormat.format(value);
        }

        @Override
        public int getDecimalDigits() {
            return 1;
        }
    }

}
