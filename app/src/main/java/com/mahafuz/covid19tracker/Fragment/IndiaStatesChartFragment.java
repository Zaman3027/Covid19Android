package com.mahafuz.covid19tracker.Fragment;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.anychart.APIlib;
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
import com.mahafuz.covid19tracker.ApiInterface.RetroFitInstance;
import com.mahafuz.covid19tracker.Model.StateChoiceModel;
import com.mahafuz.covid19tracker.R;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndiaStatesChartFragment extends Fragment {
    String stateName;
    TextView stateNameTextView, confirmedTextView, recoveredTextView, deceasedTextView, testTextView;
    AnyChartView any_chart_view_India, any_chart_view_test;
    RetroFitInstance retroFitInstance;

    public IndiaStatesChartFragment(String stateName) {
        this.stateName = stateName;
    }

    public IndiaStatesChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        retroFitInstance = new RetroFitInstance();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        return inflater.inflate(R.layout.fragment_india_states_chart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        stateNameTextView = (TextView) getView().findViewById(R.id.indian_state_name);
        stateNameTextView.setText(capitalize(stateName));
        retroFitInstance.getApi().getStateChoiceList(stateName).enqueue(new Callback<StateChoiceModel>() {
            @Override
            public void onResponse(Call<StateChoiceModel> call, Response<StateChoiceModel> response) {
                plotChart(response.body());
                plotChartTest(response.body());
            }

            @Override
            public void onFailure(Call<StateChoiceModel> call, Throwable t) {

            }
        });


    }

    private String capitalize(String str) {
        String words[]=str.split("\\s");
        String capitalizeWord="";
        for(String w:words){
            String first = w.substring(0,1);
            String afterFirst = w.substring(1);
            capitalizeWord += first.toUpperCase()+afterFirst+" ";
        }
        return capitalizeWord.trim();
    }

    private void plotChart(StateChoiceModel stateChoiceModelList) {
        any_chart_view_India = getView().findViewById(R.id.any_chart_view_India);
        confirmedTextView = (TextView) getView().findViewById(R.id.state_confirmed);
        recoveredTextView = (TextView) getView().findViewById(R.id.state_recovered);
        deceasedTextView = (TextView) getView().findViewById(R.id.state_deceased);
        APIlib.getInstance().setActiveAnyChartView(any_chart_view_India);
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
        int size = stateChoiceModelList.getStateCase().size();
        for (int i = 0; i < size; i++) {
            seriesData.add(new CustomDataEntry(
                    stateChoiceModelList.getStateCase().get(i).getDate(),
                    Integer.parseInt(stateChoiceModelList.getStateCase().get(i).getConfirmed()),
                    Integer.parseInt(stateChoiceModelList.getStateCase().get(i).getRecovered()),
                    Integer.parseInt(stateChoiceModelList.getStateCase().get(i).getDeaths())
            ));

        }
        
        confirmedTextView.setText("Confirmed: " + seriesData.get(size-1).getValue("value").toString());
        recoveredTextView.setText("Recovered: " + seriesData.get(size-1).getValue("value2").toString());
        deceasedTextView.setText("Deceased: " + seriesData.get(size-1).getValue("value3").toString());

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

        any_chart_view_India.setChart(cartesian);
    }

    private void plotChartTest(StateChoiceModel stateChoiceModelList) {
        any_chart_view_test = getView().findViewById(R.id.any_chart_view_test);
        testTextView = (TextView) getView().findViewById(R.id.state_tested);
        APIlib.getInstance().setActiveAnyChartView(any_chart_view_test);
        Cartesian cartesian = AnyChart.line();
        cartesian.animation(true);
        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);
        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.title("Testing");
        cartesian.xAxis(0).labels().padding(5d, 5d, 5d, 5d);

        List<DataEntry> seriesData = new ArrayList<>();
        int i;
        for ( i = 0; i < stateChoiceModelList.getStateTest().size(); i++) {
            seriesData.add(new ValueDataEntry(
                    stateChoiceModelList.getStateTest().get(i).getDate(),
                    (int) Float.parseFloat(stateChoiceModelList.getStateTest().get(i).getValue())
            ));

        }
        String formattedTestString = stateChoiceModelList.getStateTest().get(i-1).getValue();
        testTextView.setText("Tested: " + formattedTestString.substring(0, formattedTestString.length()-2));
        Line series1 = cartesian.line(seriesData);
        series1.name("Test");
        series1.color("#004d40");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        any_chart_view_test.setChart(cartesian);
    }

    static class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }
}
