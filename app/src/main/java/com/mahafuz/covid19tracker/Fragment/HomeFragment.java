package com.mahafuz.covid19tracker.Fragment;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.mahafuz.covid19tracker.ApiInterface.RetroFitInstance;
import com.mahafuz.covid19tracker.Interface.FragmentCall;
import com.mahafuz.covid19tracker.Model.DailyCaseModel;
import com.mahafuz.covid19tracker.Module.AndroidModule;
import com.mahafuz.covid19tracker.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    CardView homeCardActive, homeCardRecovered, homeDeadActive;
    int screenWidth;
    AnyChartView anyChartView;
    TextView cardActive, cardRecovered, cardDeceased;
    CardView cardAllIndia, cardDemographic;
    FragmentCall fragmentCall;
    ProgressDialog progressDialog;
    AndroidModule androidModule;
    RetroFitInstance retroFitInstance;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        progressDialog = new ProgressDialog(getContext());
        homeCardActive = getView().findViewById(R.id.homeCardActive);
        homeCardRecovered = getView().findViewById(R.id.homeCardRecovered);
        homeDeadActive = getView().findViewById(R.id.homeDeadActive);
        anyChartView = getView().findViewById(R.id.any_chart_view);
        cardActive = getView().findViewById(R.id.cardActive);
        cardRecovered = getView().findViewById(R.id.cardRecovered);
        cardDeceased = getView().findViewById(R.id.cardDeceased);
        cardAllIndia = getView().findViewById(R.id.cardAllIndia);
        cardDemographic = getView().findViewById(R.id.cardDemographic);
        retroFitInstance = new RetroFitInstance();
        androidModule = AndroidModule.getInstance(getContext());
        androidModule.showLoadingDialogue();

        cardAllIndia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.navigation_drawer_frame, new AllIndiaFragment(), "All India")
                        .addToBackStack("All India")
                        .commit();
            }
        });

        cardDemographic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.navigation_drawer_frame, new Demographics(), "All India")
                        .addToBackStack("All India")
                        .commit();
            }
        });

        retroFitInstance.getApi().getCaseModelCall().enqueue(new Callback<DailyCaseModel>() {
            @Override
            public void onResponse(Call<DailyCaseModel> call, Response<DailyCaseModel> response) {
                androidModule.dismissDialogue();
                if (response.isSuccessful()) {
                    plotChart(response.body());
                }
            }

            @Override
            public void onFailure(Call<DailyCaseModel> call, Throwable t) {

            }
        });

    }

    private void plotChart(DailyCaseModel dailyCaseModel) {
        cardDeceased.setText(dailyCaseModel.getDailydeceased().get(dailyCaseModel.getDailyconfirmed().size() - 1).getValue());
        cardRecovered.setText(dailyCaseModel.getDailyrecovered().get(dailyCaseModel.getDailyconfirmed().size() - 1).getValue());
        cardActive.setText(dailyCaseModel.getDailyconfirmed().get(dailyCaseModel.getDailyconfirmed().size() - 1).getValue());
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
        for (int i = 0; i < dailyCaseModel.getDailydeceased().size(); i++) {
            seriesData.add(new CustomDataEntry(
                    dailyCaseModel.getDailyconfirmed().get(i).getDate(),
                    Integer.parseInt(dailyCaseModel.getDailyconfirmed().get(i).getValue()),
                    Integer.parseInt(dailyCaseModel.getDailyrecovered().get(i).getValue()),
                    Integer.parseInt(dailyCaseModel.getDailydeceased().get(i).getValue())
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

        anyChartView.setChart(cartesian);
    }

    static class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        androidModule.dispose();
    }
}
