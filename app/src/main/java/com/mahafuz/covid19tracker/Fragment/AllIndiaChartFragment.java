package com.mahafuz.covid19tracker.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.mahafuz.covid19tracker.Model.StateWiseModelNew;
import com.mahafuz.covid19tracker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllIndiaChartFragment extends Fragment {
    List<StateWiseModelNew> stateWiseModelNewList;
    AnyChartView any_chart_view_India;

    public AllIndiaChartFragment(List<StateWiseModelNew> stateWiseModelNewList) {
        this.stateWiseModelNewList = stateWiseModelNewList;
    }

    public AllIndiaChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_india_chart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        any_chart_view_India = getView().findViewById(R.id.any_chart_view_India);
        plotChart();

    }

    private void plotChart() {
        Cartesian cartesian = AnyChart.line();
        cartesian.animation(true);
        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.title("Daily Cases");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData = new ArrayList<>();
        List<DataEntry> seriesData2 = new ArrayList<>();
        List<DataEntry> seriesData3 = new ArrayList<>();
        seriesData.clear();
        for (StateWiseModelNew stateWiseModelNew : stateWiseModelNewList) {
            Log.i("ALLINDIA", stateWiseModelNew.getCount());
            if (stateWiseModelNew.getState().equals("Confirmed")) {
                seriesData.add(new HomeFragment.CustomDataEntry(stateWiseModelNew.getDate(),
                        Integer.parseInt(stateWiseModelNew.getCount().equals("") ? "0" : stateWiseModelNew.getCount()),
                        Integer.parseInt("0"),
                        Integer.parseInt("0")
                ));
            } else if (stateWiseModelNew.getState().equals("Recovered")) {
                seriesData2.add(new HomeFragment.CustomDataEntry(stateWiseModelNew.getDate(),
                        Integer.parseInt(stateWiseModelNew.getCount().equals("") ? "0" : stateWiseModelNew.getCount()),
                        Integer.parseInt("0"),
                        Integer.parseInt("0")
                ));
            } else {
                seriesData3.add(new HomeFragment.CustomDataEntry(stateWiseModelNew.getDate(),
                        Integer.parseInt(stateWiseModelNew.getCount().equals("") ? "0" : stateWiseModelNew.getCount()),
                        Integer.parseInt("0"),
                        Integer.parseInt("0")
                ));
            }

        }


        Line series1 = cartesian.line(seriesData);
        series1.name("Confirmed");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series2 = cartesian.line(seriesData2);
        series2.name("Recovered");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series3 = cartesian.line(seriesData3);
        series3.name("Deceased");
        series3.hovered().markers().enabled(true);
        series3.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series3.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        any_chart_view_India.setChart(cartesian);
    }
}
