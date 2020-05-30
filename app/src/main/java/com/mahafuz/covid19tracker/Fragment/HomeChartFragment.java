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
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.mahafuz.covid19tracker.Model.DailyCaseModel;
import com.mahafuz.covid19tracker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeChartFragment extends Fragment {
    AnyChartView any_chart_view;
    List<DailyCaseModel> dailyCaseModelList;

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
        any_chart_view = getView().findViewById(R.id.any_chart_view);
        plotChart(this.dailyCaseModelList);


    }

    private void plotChart(List<DailyCaseModel> dailyCaseModel) {
        Log.i("HOMEFRAGMENT", "" + dailyCaseModel.size());
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
        for (int i = 0; i < dailyCaseModel.size(); i++) {
            Log.i("HOMEFRAGMENT", dailyCaseModel.get(i).getDate());
            seriesData.add(new HomeFragment.CustomDataEntry(
                    dailyCaseModel.get(i).getDate(),
                    Integer.parseInt(dailyCaseModel.get(i).getConfirmed()),
                    Integer.parseInt(dailyCaseModel.get(i).getRecovered()),
                    Integer.parseInt(dailyCaseModel.get(i).getDeceased())
            ));

        }


        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");

        Line series1 = cartesian.line(series1Mapping);
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

        Line series2 = cartesian.line(series2Mapping);
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

        Line series3 = cartesian.line(series3Mapping);
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
        any_chart_view.setChart(cartesian);
    }
}
