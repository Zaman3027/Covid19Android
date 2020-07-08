package com.mahafuz.covid19tracker.Fragment;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.IMarker;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.mahafuz.covid19tracker.ApiInterface.RetroFitInstance;
import com.mahafuz.covid19tracker.CustomMarkerView;
import com.mahafuz.covid19tracker.Model.PredictionModel;
import com.mahafuz.covid19tracker.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PredictionActivity extends AppCompatActivity {
    LineChart mPredictionChart;
    TextView day1, day2, day3, day4, day5;
    TextView day1LL, day1UL, day2LL, day2UL, day3LL, day3UL, day4LL, day4UL, day5LL, day5UL;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(
                new ColorDrawable(Color.argb(0, 255, 255, 255)));
        actionBar.setElevation(0);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_toolbar, null));

        mPredictionChart = findViewById(R.id.prediction_chart);
        day1 = (TextView) findViewById(R.id.day1);
        day2 = (TextView) findViewById(R.id.day2);
        day3 = (TextView) findViewById(R.id.day3);
        day4 = (TextView) findViewById(R.id.day4);
        day5 = (TextView) findViewById(R.id.day5);
        day1LL = (TextView) findViewById(R.id.day1_ll);
        day1UL = (TextView) findViewById(R.id.day1_ul);
        day2LL = (TextView) findViewById(R.id.day2_ll);
        day2UL = (TextView) findViewById(R.id.day2_ul);
        day3LL = (TextView) findViewById(R.id.day3_ll);
        day3UL = (TextView) findViewById(R.id.day3_ul);
        day4LL = (TextView) findViewById(R.id.day4_ll);
        day4UL = (TextView) findViewById(R.id.day4_ul);
        day5LL = (TextView) findViewById(R.id.day5_ll);
        day5UL = (TextView) findViewById(R.id.day5_ul);

        new RetroFitInstance().getApi().getPredictionModel().enqueue(new Callback<List<PredictionModel>>() {
            @Override
            public void onResponse(Call<List<PredictionModel>> call, Response<List<PredictionModel>> response) {
                if (response.isSuccessful())
                    plotPredictionGraph(response.body());
            }

            @Override
            public void onFailure(Call<List<PredictionModel>> call, Throwable t) {

            }
        });
    }

    private void plotPredictionGraph(List<PredictionModel> body) {
        List<PredictionModel> predictModelList = body.subList(body.size()-30, body.size());
        List<PredictionModel> knownTotalCases = predictModelList.subList(0, predictModelList.size()-5);
        List<PredictionModel> predictedTotalCases = predictModelList.subList(predictModelList.size()-5, predictModelList.size());
        List<Entry> entriesKnown = new ArrayList<>();
        final String[] labels = new String[predictModelList.size()];
        int labelIndex=0;
        for (PredictionModel predictionModel:knownTotalCases){
            entriesKnown.add(new Entry(labelIndex, Math.round(predictionModel.getYhat())));
            labels[labelIndex] = predictionModel.getDs().substring(8, 10) + "/" + predictionModel.getDs().substring(5, 7);
            labelIndex++;
        }

        List<Entry> entriesPredicted = new ArrayList<>();
        for (PredictionModel predictionModel: predictedTotalCases){
            entriesPredicted.add(new Entry(labelIndex, Math.round(predictionModel.getYhat())));
            labels[labelIndex] = predictionModel.getDs().substring(8, 10) + "/" + predictionModel.getDs().substring(5, 7);
            labelIndex++;
        }

        ValueFormatter valueFormatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return labels[(int)value];
            }
        };

        LineDataSet knownDataSet = new LineDataSet(entriesKnown, "Known Total Cases");
        knownDataSet.setColor(getResources().getColor(R.color.confirm_light));
        knownDataSet.setCircleColor(getResources().getColor(R.color.confirm_dark));
        knownDataSet.setCircleHoleColor(getResources().getColor(R.color.confirm_dark));
        knownDataSet.setCircleRadius(2);
        knownDataSet.setDrawValues(false);

        LineDataSet predictedDataSet = new LineDataSet(entriesPredicted, "Predicted Total Cases");
        predictedDataSet.setColor(getResources().getColor(R.color.recovered_light));
        predictedDataSet.setCircleColor(getResources().getColor(R.color.recovered_dark));
        predictedDataSet.setCircleHoleColor(getResources().getColor(R.color.recovered_dark));
        predictedDataSet.setCircleRadius(2);
        predictedDataSet.setDrawValues(false);

        LineData lineData = new LineData();
        lineData.addDataSet(knownDataSet);
        lineData.addDataSet(predictedDataSet);
        mPredictionChart.setData(lineData);
        XAxis xAxis = mPredictionChart.getXAxis();
        xAxis.setValueFormatter(valueFormatter);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        mPredictionChart.setGridBackgroundColor(Color.GRAY);
        mPredictionChart.getAxisRight().setEnabled(false);
        mPredictionChart.getDescription().setText("Zoom In/Out or Tap on dots | X-axis:DD/MM | Y-axis:Cases count");
        mPredictionChart.getDescription().setTextSize(10);
        mPredictionChart.invalidate();
        mPredictionChart.setTouchEnabled(true);
        IMarker mv = new CustomMarkerView(PredictionActivity.this, R.layout.chart_marker);
        mPredictionChart.setMarkerView(mv);
        mPredictionChart.animateXY(3000, 3000);
        fillTable(predictedTotalCases);
    }

    private void fillTable(List<PredictionModel> predictedTotalCases) {
        day1.setText(predictedTotalCases.get(0).getDs().substring(8,10) + "/" + predictedTotalCases.get(0).getDs().substring(5,7));
        day2.setText(predictedTotalCases.get(1).getDs().substring(8,10) + "/" + predictedTotalCases.get(1).getDs().substring(5,7));
        day3.setText(predictedTotalCases.get(2).getDs().substring(8,10) + "/" + predictedTotalCases.get(2).getDs().substring(5,7));
        day4.setText(predictedTotalCases.get(3).getDs().substring(8,10) + "/" + predictedTotalCases.get(3).getDs().substring(5,7));
        day5.setText(predictedTotalCases.get(4).getDs().substring(8,10) + "/" + predictedTotalCases.get(4).getDs().substring(5,7));

        day1LL.setText(String.valueOf(Math.round(predictedTotalCases.get(0).getYhatLower())));
        day2LL.setText(String.valueOf(Math.round(predictedTotalCases.get(1).getYhatLower())));
        day3LL.setText(String.valueOf(Math.round(predictedTotalCases.get(2).getYhatLower())));
        day4LL.setText(String.valueOf(Math.round(predictedTotalCases.get(3).getYhatLower())));
        day5LL.setText(String.valueOf(Math.round(predictedTotalCases.get(4).getYhatLower())));

        day1UL.setText(String.valueOf(Math.round(predictedTotalCases.get(0).getYhatUpper())));
        day2UL.setText(String.valueOf(Math.round(predictedTotalCases.get(1).getYhatUpper())));
        day3UL.setText(String.valueOf(Math.round(predictedTotalCases.get(2).getYhatUpper())));
        day4UL.setText(String.valueOf(Math.round(predictedTotalCases.get(3).getYhatUpper())));
        day5UL.setText(String.valueOf(Math.round(predictedTotalCases.get(4).getYhatUpper())));
    }
}