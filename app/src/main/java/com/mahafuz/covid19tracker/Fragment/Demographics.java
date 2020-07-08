package com.mahafuz.covid19tracker.Fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.mahafuz.covid19tracker.ApiInterface.RetroFitInstance;
import com.mahafuz.covid19tracker.CustomMarkerView;
import com.mahafuz.covid19tracker.Model.AgeRangeModel;
import com.mahafuz.covid19tracker.Model.GenderModel;
import com.mahafuz.covid19tracker.Model.StateTestingModel;
import com.mahafuz.covid19tracker.Model.TestingModel;
import com.mahafuz.covid19tracker.Module.AndroidModule;
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
    ProgressDialog progressDialog;
    AndroidModule androidModule;
    PieChart mGenderPieChart;
    BarChart mAgeRangeBarChart, mTestBarChart, mStateTestBarChart;

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
        progressDialog = new ProgressDialog(getContext());
        mGenderPieChart = getView().findViewById(R.id.gender_pie_chart);
        mAgeRangeBarChart = getView().findViewById(R.id.age_range_bar_chart);
        mTestBarChart = getView().findViewById(R.id.test_bar_chart);
        mStateTestBarChart = getView().findViewById(R.id.state_test_bar_chart);
        androidModule = AndroidModule.getInstance(getContext());
        androidModule.showLoadingDialogue();

        retroFitInstance.getApi().getGenderModelCall().enqueue(new Callback<GenderModel>() {
            @Override
            public void onResponse(Call<GenderModel> call, Response<GenderModel> response) {
                androidModule.dismissDialogue();
                if (response.isSuccessful()) {
                    ArrayList<PieEntry> genderRatio = new ArrayList<>();
                    genderRatio.add(new PieEntry(response.body().getMale(), "Male"));
                    genderRatio.add(new PieEntry(response.body().getFemale(), "Female"));

                    final int[] MY_COLORS = {Color.rgb(0, 229, 255), Color.rgb(118, 255, 3)};

                    PieDataSet dataSet = new PieDataSet( genderRatio, "Gender Ratio");
                    dataSet.setColors(ColorTemplate.createColors(MY_COLORS));

                    PieData data = new PieData(dataSet);
                    mGenderPieChart.setData(data);
                    mGenderPieChart.setUsePercentValues(true);
                    mGenderPieChart.setExtraOffsets(5, 10, 5, 5);
                    mGenderPieChart.setDrawHoleEnabled(true);
                    mGenderPieChart.setHoleRadius(30f);
                    mGenderPieChart.setTransparentCircleRadius(10f);
                    mGenderPieChart.getDescription().setText("Figures are shown in percentage");
                    mGenderPieChart.getDescription().setTextSize(9f);
                    mGenderPieChart.invalidate();
                    mGenderPieChart.animateXY(3000, 3000);
                    IMarker mv = new CustomMarkerView(getContext(), R.layout.chart_marker);
                    mGenderPieChart.setMarker(mv);
                }
            }

            @Override
            public void onFailure(Call<GenderModel> call, Throwable t) {

            }
        });

        retroFitInstance.getApi().getAgeRangeModelListCall().enqueue(new Callback<List<AgeRangeModel>>() {
            @Override
            public void onResponse(Call<List<AgeRangeModel>> call, Response<List<AgeRangeModel>> response) {
                if (response.isSuccessful()) {
                    List<AgeRangeModel> ageRangeList = response.body();
                    ArrayList<BarEntry> entries = new ArrayList<>();
                    String[] labels = new String[ageRangeList.size()];
                    for (int i=0; i<ageRangeList.size(); i++){
                        entries.add(new BarEntry(i, ageRangeList.get(i).getValue()));
                        labels[i] = ageRangeList.get(i).getRange();
                    }

                    ValueFormatter vf = new ValueFormatter() {
                        @Override
                        public String getAxisLabel(float value, AxisBase axis) {
                            return labels[(int) value];
                        }
                    };

                    BarDataSet dataSet = new BarDataSet(entries, "Age Range");
                    dataSet.setColor(getResources().getColor(R.color.graph_blue));
                    dataSet.setDrawValues(false);

                    BarData data = new BarData();
                    data.addDataSet(dataSet);
                    mAgeRangeBarChart.setData(data);

                    XAxis xAxis = mAgeRangeBarChart.getXAxis();
                    xAxis.setValueFormatter(vf);
                    xAxis.setDrawGridLines(false);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    mAgeRangeBarChart.setGridBackgroundColor(Color.GRAY);
                    mAgeRangeBarChart.getAxisRight().setEnabled(false);
                    mAgeRangeBarChart.getDescription().setText("Zoom In/Out or Tap on dots | X-axis:Age range | Y-axis:Cases count");
                    mAgeRangeBarChart.getDescription().setTextSize(9);
                    mAgeRangeBarChart.invalidate();
                    mAgeRangeBarChart.setTouchEnabled(true);
                    mAgeRangeBarChart.animateXY(3000, 3000);
                    IMarker mv = new CustomMarkerView(getContext(), R.layout.chart_marker);
                    mAgeRangeBarChart.setMarker(mv);
                }
            }

            @Override
            public void onFailure(Call<List<AgeRangeModel>> call, Throwable t) {

            }
        });

        retroFitInstance.getApi().getTestingModelList().enqueue(new Callback<List<TestingModel>>() {
            @Override
            public void onResponse(Call<List<TestingModel>> call, Response<List<TestingModel>> response) {
                if (response.isSuccessful()) {
                    List<TestingModel> testingModelList = response.body();
                    List<BarEntry> entries = new ArrayList<>();
                    String[] labels = new String[testingModelList.size()];
                    for (int i=0; i< testingModelList.size(); i++){
                        entries.add(new BarEntry(i, Math.round(Float.parseFloat(testingModelList.get(i).getTestspermillion()))));
                        labels[i] = testingModelList.get(i).getDate();
                    }

                    ValueFormatter vf = new ValueFormatter() {
                        @Override
                        public String getAxisLabel(float value, AxisBase axis) {
                            return labels[(int) value];
                        }
                    };

                    BarDataSet testDataSet = new BarDataSet(entries, "Total Tests in India");
                    testDataSet.setColor(getResources().getColor(R.color.graph_green));
                    testDataSet.setDrawValues(false);

                    BarData testData = new BarData();
                    testData.addDataSet(testDataSet);
                    mTestBarChart.setData(testData);

                    XAxis xAxis = mTestBarChart.getXAxis();
                    xAxis.setValueFormatter(vf);
                    xAxis.setDrawGridLines(false);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    mTestBarChart.setGridBackgroundColor(Color.GRAY);
                    mTestBarChart.getAxisRight().setEnabled(false);
                    mTestBarChart.getDescription().setText("Zoom In/Out or Tap on dots | X-axis:YYYY-MM-DD | Y-axis:Tests count");
                    mTestBarChart.getDescription().setTextSize(9);
                    mTestBarChart.invalidate();
                    mTestBarChart.setTouchEnabled(true);
                    mTestBarChart.animateXY(3000, 3000);
                    IMarker mv = new CustomMarkerView(getContext(), R.layout.chart_marker);
                    mTestBarChart.setMarker(mv);
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
                    List<StateTestingModel> stateTestingModelList = response.body();
                    List<BarEntry> entries = new ArrayList<>();
                    String[] labels = new String[stateTestingModelList.size()];
                    for (int i=0; i<stateTestingModelList.size(); i++){
                        entries.add(new BarEntry(i, Math.round(Float.parseFloat(stateTestingModelList.get(i).getValue()))));
                        labels[i] = stateTestingModelList.get(i).getState();
                    }

                    ValueFormatter vf = new ValueFormatter() {
                        @Override
                        public String getAxisLabel(float value, AxisBase axis) {
                            return labels[(int) value];
                        }
                    };

                    BarDataSet stateTestDataSet = new BarDataSet(entries, "State wise Total Tests");
                    stateTestDataSet.setColor(getResources().getColor(R.color.graph_blue));
                    stateTestDataSet.setDrawValues(false);

                    BarData stateTestData = new BarData();
                    stateTestData.addDataSet(stateTestDataSet);
                    mStateTestBarChart.setData(stateTestData);

                    XAxis xAxis = mStateTestBarChart.getXAxis();
                    xAxis.setValueFormatter(vf);
                    xAxis.setDrawGridLines(false);
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    mStateTestBarChart.setGridBackgroundColor(Color.GRAY);
                    mStateTestBarChart.getAxisRight().setEnabled(false);
                    mStateTestBarChart.getDescription().setText("Zoom In/Out or Tap on dots | X-axis:State | Y-axis:Tests count");
                    mStateTestBarChart.getDescription().setTextSize(9);
                    mStateTestBarChart.invalidate();
                    mStateTestBarChart.setTouchEnabled(true);
                    IMarker mv = new CustomMarkerView(getContext(), R.layout.chart_marker);
                    mStateTestBarChart.setMarker(mv);
                    mStateTestBarChart.animateXY(3000, 3000);
                }
            }

            @Override
            public void onFailure(Call<List<StateTestingModel>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        androidModule.dispose();
    }
}
