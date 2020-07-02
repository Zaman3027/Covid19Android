package com.mahafuz.covid19tracker.Fragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.core.cartesian.series.RangeColumn;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.mahafuz.covid19tracker.ApiInterface.RetroFitInstance;
import com.mahafuz.covid19tracker.Model.PredictionModel;
import com.mahafuz.covid19tracker.R;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PredictionActivity extends AppCompatActivity {
    AnyChartView predictionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        predictionView = findViewById(R.id.predictionView);

        new RetroFitInstance().getApi().getPredictionModel().enqueue(new Callback<List<PredictionModel>>() {
            @Override
            public void onResponse(Call<List<PredictionModel>> call, Response<List<PredictionModel>> response) {
                plotGraph(response.body());
            }

            @Override
            public void onFailure(Call<List<PredictionModel>> call, Throwable t) {

            }
        });
    }

    private void plotGraph(List<PredictionModel> body) {
        Cartesian cartesian = AnyChart.line();
        cartesian.animation(true);
        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        List<DataEntry> seriesData = new ArrayList<>();
        for (PredictionModel predictionModel : body) {
            seriesData.add(new CustomDataEntry(
                    predictionModel.getDs(),
                    predictionModel.getYhat(),
                    predictionModel.getYhatLower(),
                    predictionModel.getYhatUpper()
            ));
        }

        Set set = Set.instantiate();
        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping upperLower = set.mapAs("{ x: 'x', high: 'value3', low: 'value2' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Yhat");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        RangeColumn upperLowerChart = cartesian.rangeColumn(upperLower);
        upperLowerChart.name("Range");
        upperLowerChart.color("979797");


        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);
        cartesian.xZoom(true);
        predictionView.setChart(cartesian);
        predictionView.setZoomEnabled(true);
    }

    static class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Double value, Double value2, Double value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }

}