package com.mahafuz.covid19tracker.Fragment;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.google.gson.Gson;
import com.mahafuz.covid19tracker.ApiInterface.FetchData;
import com.mahafuz.covid19tracker.ApiInterface.GetJSONString;
import com.mahafuz.covid19tracker.BaseAct;
import com.mahafuz.covid19tracker.Home;
import com.mahafuz.covid19tracker.Interface.FragmentCall;
import com.mahafuz.covid19tracker.Model.Cases_time_series;
import com.mahafuz.covid19tracker.Model.DailyStateModel;
import com.mahafuz.covid19tracker.Model.SateWiseModel;
import com.mahafuz.covid19tracker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    List<Cases_time_series> list;

    public HomeFragment() {
        // Required empty public constructor
    }

    CardView homeCardActive, homeCardRecovered, homeDeadActive;
    int screenWidth;
    AnyChartView anyChartView;
    TextView cardActive, cardRecovered, cardDeceased;
    CardView cardAllIndia;
    FragmentCall fragmentCall;
    ProgressDialog progressDialog;
    List<SateWiseModel> sateWiseModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        homeCardActive = getView().findViewById(R.id.homeCardActive);
        homeCardRecovered = getView().findViewById(R.id.homeCardRecovered);
        homeDeadActive = getView().findViewById(R.id.homeDeadActive);
        anyChartView = getView().findViewById(R.id.any_chart_view);
        cardActive = getView().findViewById(R.id.cardActive);
        cardRecovered = getView().findViewById(R.id.cardRecovered);
        cardDeceased = getView().findViewById(R.id.cardDeceased);
        cardAllIndia = getView().findViewById(R.id.cardAllIndia);
        list = new ArrayList<>();
        sateWiseModelList = new ArrayList<>();

        FetchData fetchData = new FetchData(new GetJSONString() {
            @Override
            public void getData(String data) {

                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray jsonArray = jsonObject.getJSONArray("cases_time_series");
                    JSONArray stateWiseJsonArray = jsonObject.getJSONArray("statewise");
                    Gson gson = new Gson();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        list.add(gson.fromJson(jsonArray.getString(i), Cases_time_series.class));
                    }

                    for (int i = 0; i < stateWiseJsonArray.length(); i++) {
                        sateWiseModelList.add(gson.fromJson(stateWiseJsonArray.getString(i), SateWiseModel.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (list.size() > 0) {
                    cardDeceased.setText(list.get(list.size() - 1).getTotaldeceased());
                    cardRecovered.setText(list.get(list.size() - 1).getTotalrecovered());
                    cardActive.setText(list.get(list.size() - 1).getTotalconfirmed());
                    plotChart();
                    progressDialog.dismiss();
                }

            }
        });
        fetchData.execute();


        cardAllIndia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.navigation_drawer_frame, new AllIndiaFragment(), "All India")
                        .addToBackStack("All India")
                        .commit();
            }
        });

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
        for (Cases_time_series cases_time_series : list) {
            seriesData.add(new CustomDataEntry(cases_time_series.getDate(),
                    Integer.parseInt(cases_time_series.getDailyconfirmed()),
                    Integer.parseInt(cases_time_series.getDailyrecovered()),
                    Integer.parseInt(cases_time_series.getDailydeceased())
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

    class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }

}
