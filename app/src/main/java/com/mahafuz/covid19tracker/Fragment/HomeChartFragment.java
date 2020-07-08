package com.mahafuz.covid19tracker.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.mahafuz.covid19tracker.CustomMarkerView;
import com.mahafuz.covid19tracker.Model.DailyCaseModel;
import com.mahafuz.covid19tracker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeChartFragment extends Fragment {
    List<DailyCaseModel> dailyCaseModelList;
    LineChart mLineChart;

    public HomeChartFragment(List<DailyCaseModel> dailyCaseModelList) {
        this.dailyCaseModelList = dailyCaseModelList;
    }

    public HomeChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_chart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLineChart = getView().findViewById(R.id.mp_chart_view);
        if (this.dailyCaseModelList!=null) {
            plotMPChart(this.dailyCaseModelList);
        }
    }

    private void plotMPChart(List<DailyCaseModel> dailyCaseModelList) {
        List<DailyCaseModel> dailyCaseSubList = dailyCaseModelList.subList(dailyCaseModelList.size() - 30, dailyCaseModelList.size());
        List<Entry> entriesConfirmed = new ArrayList<>();
        List<Entry> entriesRecovered = new ArrayList<>();
        List<Entry> entriesDeceased = new ArrayList<>();
        final String[] labels = new String[dailyCaseSubList.size()];
        for (int i=0; i<dailyCaseSubList.size(); i++){
            entriesConfirmed.add(new Entry(i, Integer.parseInt(dailyCaseSubList.get(i).getConfirmed())));
            entriesRecovered.add(new Entry(i, Integer.parseInt(dailyCaseSubList.get(i).getRecovered())));
            entriesDeceased.add(new Entry(i, Integer.parseInt(dailyCaseSubList.get(i).getDeceased())));
            labels[i] = dailyCaseSubList.get(i).getDate().substring(8,10) + "/" + dailyCaseSubList.get(i).getDate().substring(5,7);
        }

        ValueFormatter valueFormatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return labels[(int)value];
            }
        };


        LineDataSet confirmDataSet = new LineDataSet(entriesConfirmed, "Confirmed");
        confirmDataSet.setColor(getResources().getColor(R.color.confirm_light));
        confirmDataSet.setCircleColor(getResources().getColor(R.color.confirm_dark));
        confirmDataSet.setCircleHoleColor(getResources().getColor(R.color.confirm_dark));
        confirmDataSet.setCircleRadius(2);
        confirmDataSet.setDrawValues(false);

        LineDataSet recoveredDataSet = new LineDataSet(entriesRecovered, "Recovered");
        recoveredDataSet.setColor(getResources().getColor(R.color.recovered_light));
        recoveredDataSet.setCircleColor(getResources().getColor(R.color.recovered_dark));
        recoveredDataSet.setCircleHoleColor(getResources().getColor(R.color.recovered_dark));
        recoveredDataSet.setCircleRadius(2);
        recoveredDataSet.setDrawValues(false);

        LineDataSet deceasedDataSet = new LineDataSet(entriesDeceased, "Deceased");
        deceasedDataSet.setColor(getResources().getColor(R.color.deceased_light));
        deceasedDataSet.setCircleColor(getResources().getColor(R.color.deceased_dark));
        deceasedDataSet.setCircleHoleColor(getResources().getColor(R.color.deceased_dark));
        deceasedDataSet.setCircleRadius(2);
        deceasedDataSet.setDrawValues(false);

        LineData lineData = new LineData();
        lineData.addDataSet(confirmDataSet);
        lineData.addDataSet(recoveredDataSet);
        lineData.addDataSet(deceasedDataSet);
        mLineChart.setData(lineData);
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setValueFormatter(valueFormatter);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        mLineChart.setGridBackgroundColor(Color.GRAY);
        mLineChart.getAxisRight().setEnabled(false);
        mLineChart.getDescription().setText("Zoom In/Out or Tap on dots | X-axis:DD/MM | Y-axis:Cases count");
        mLineChart.getDescription().setTextSize(9);
        mLineChart.invalidate();
        mLineChart.setTouchEnabled(true);
        IMarker mv = new CustomMarkerView(getContext(), R.layout.chart_marker);
        mLineChart.setMarkerView(mv);
        mLineChart.animateXY(3000,3000);
    }
}
