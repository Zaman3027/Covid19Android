package com.mahafuz.covid19tracker.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Column;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.mahafuz.covid19tracker.ApiInterface.RetroFitInstance;
import com.mahafuz.covid19tracker.Model.AgeRangeModel;
import com.mahafuz.covid19tracker.Model.DailyCaseModel;
import com.mahafuz.covid19tracker.Model.GenderModel;
import com.mahafuz.covid19tracker.Model.StateTestingModel;
import com.mahafuz.covid19tracker.Model.TestingModel;
import com.mahafuz.covid19tracker.Model.TotalCaseModel;
import com.mahafuz.covid19tracker.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class Demographics extends Fragment {
    RetroFitInstance retroFitInstance;
    AnyChartView genderPieChart, ageRange, totalCaseChart, testingChart, stateTestingChart;

    public Demographics() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        retroFitInstance = new RetroFitInstance();
        return inflater.inflate(R.layout.fragment_demographics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        genderPieChart = getView().findViewById(R.id.genderPieChart);
        ageRange = getView().findViewById(R.id.ageRange);
        totalCaseChart = getView().findViewById(R.id.totalCaseChart);
        testingChart = getView().findViewById(R.id.testingChart);
        stateTestingChart = getView().findViewById(R.id.stateTestingChart);

        retroFitInstance.getApi().getGenderModelCall().enqueue(new Callback<GenderModel>() {
            @Override
            public void onResponse(Call<GenderModel> call, Response<GenderModel> response) {
                if (response.isSuccessful()) {
                    APIlib.getInstance().setActiveAnyChartView(genderPieChart);
                    Pie pieChart = AnyChart.pie();
                    pieChart.title("Gender Ratio");
                    pieChart.animation(true);
                    List<DataEntry> entryList = new ArrayList<>();
                    entryList.add(new ValueDataEntry("Male", response.body().getMale()));
                    entryList.add(new ValueDataEntry("Female", response.body().getFemale()));
                    pieChart.data(entryList);
                    genderPieChart.setChart(pieChart);

                }
            }

            @Override
            public void onFailure(Call<GenderModel> call, Throwable t) {

            }
        });

//        retroFitInstance.getApi().getAgeRangeModelListCall().enqueue(new Callback<List<AgeRangeModel>>() {
//            @Override
//            public void onResponse(Call<List<AgeRangeModel>> call, Response<List<AgeRangeModel>> response) {
//                if (response.isSuccessful()) {
//                    APIlib.getInstance().setActiveAnyChartView(ageRange);
//                    Cartesian cartesian = AnyChart.column();
//                    cartesian.title("Age Range Sample");
//                    cartesian.animation(true);
//                    List<DataEntry> entryList = new ArrayList<>();
//                    for (AgeRangeModel ageRangeModel : response.body()) {
//                        entryList.add(new ValueDataEntry(ageRangeModel.getRange(), ageRangeModel.getValue()));
//                    }
//
//                    cartesian.column(entryList).color("#00c853");
//                    cartesian.xAxis(0).title("Age");
//                    cartesian.yAxis(0).title("Infected");
//                    ageRange.setChart(cartesian);
//
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<AgeRangeModel>> call, Throwable t) {
//
//            }
//        });

        retroFitInstance.getApi().getTotalCaseModel().enqueue(new Callback<List<TotalCaseModel>>() {
            @Override
            public void onResponse(Call<List<TotalCaseModel>> call, Response<List<TotalCaseModel>> response) {
                if (response.isSuccessful()) {
                    APIlib.getInstance().setActiveAnyChartView(totalCaseChart);
                    plotChart(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<TotalCaseModel>> call, Throwable t) {

            }
        });

        retroFitInstance.getApi().getTestingModelList().enqueue(new Callback<List<TestingModel>>() {
            @Override
            public void onResponse(Call<List<TestingModel>> call, Response<List<TestingModel>> response) {
                if (response.isSuccessful()) {
                    APIlib.getInstance().setActiveAnyChartView(testingChart);
                    Cartesian chart = AnyChart.column();
                    chart.fullScreen(true);
                    chart.animation(true);
                    chart.title("Analysis of Testing over Time ");
                    List<DataEntry> entryList = new ArrayList<>();
                    for (TestingModel testingModel : response.body()) {
                        entryList.add(new ValueDataEntry(testingModel.getDate(),
                                Float.parseFloat(testingModel.getTestspermillion())));
                    }
                    chart.column(entryList).color("#dd2c00");
                    testingChart.setChart(chart);

                }
            }

            @Override
            public void onFailure(Call<List<TestingModel>> call, Throwable t) {

            }
        });

        retroFitInstance.getApi().getStateTestingModel().enqueue(new Callback<List<StateTestingModel>>() {
            @Override
            public void onResponse(Call<List<StateTestingModel>> call, Response<List<StateTestingModel>> response) {
                if (response.isSuccessful()) {
                    APIlib.getInstance().setActiveAnyChartView(stateTestingChart);
                    Cartesian chart = AnyChart.column();
                    chart.fullScreen(true);
                    chart.animation(true);
                    chart.title("Analysis of Testing over Time ");
                    List<DataEntry> entryList = new ArrayList<>();
                    for (StateTestingModel testingModel : response.body()) {
                        entryList.add(new ValueDataEntry(testingModel.getState(), Float.parseFloat(testingModel.getValue())));
                    }
                    Column column = chart.column(entryList);
                    column.color("#e65100");
                    stateTestingChart.setChart(chart);
                }
            }

            @Override
            public void onFailure(Call<List<StateTestingModel>> call, Throwable t) {

            }
        });

    }

    private void plotChart(List<TotalCaseModel> dailyCaseModel) {
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
        totalCaseChart.setChart(cartesian);
    }


}
