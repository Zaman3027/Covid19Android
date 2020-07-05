package com.mahafuz.covid19tracker.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import com.mahafuz.covid19tracker.Model.Score;
import com.mahafuz.covid19tracker.Model.StateCase;
import com.mahafuz.covid19tracker.Model.StateChoiceModel;
import com.mahafuz.covid19tracker.Model.StateTest;
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
    TextView stateNameTextView, confirmedTextView, recoveredTextView, deceasedTextView, activeTextView;
    TextView positivityScore, mortalityScore, confirmScore, totalScore, zoneScore, cumulativeScore;
    LineChart mStateTotalLineChart, mStateTestLineChart;
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
        mStateTotalLineChart = getView().findViewById(R.id.line_chart_state_total);
        mStateTestLineChart = getView().findViewById(R.id.line_chart_state_test);
        confirmedTextView = getView().findViewById(R.id.state_confirmed);
        recoveredTextView = getView().findViewById(R.id.state_recovered);
        deceasedTextView = getView().findViewById(R.id.state_deceased);
        activeTextView =  getView().findViewById(R.id.state_active);
        stateNameTextView = getView().findViewById(R.id.indian_state_name);
        positivityScore = getView().findViewById(R.id.score_positivity);
        mortalityScore = getView().findViewById(R.id.score_mortality);
        confirmScore = getView().findViewById(R.id.score_confirm_cases);
        totalScore = getView().findViewById(R.id.score_totol_cases);
        zoneScore = getView().findViewById(R.id.score_zones);
        cumulativeScore = getView().findViewById(R.id.score_cumulative);

        stateNameTextView.setText(capitalize(stateName));
        retroFitInstance.getApi().getStateChoiceList(stateName).enqueue(new Callback<StateChoiceModel>() {
            @Override
            public void onResponse(Call<StateChoiceModel> call, Response<StateChoiceModel> response) {
                cardData(response.body());
                plotStateTotalLineChart(response.body());
                plotStateTestLineChart(response.body());
                fillTable(response.body());
            }

            @Override
            public void onFailure(Call<StateChoiceModel> call, Throwable t) {

            }
        });
    }

    private void cardData(StateChoiceModel stateChoiceModelList) {
        confirmedTextView.setText("Confirmed: " + stateChoiceModelList.getStateCasecumsum().get(stateChoiceModelList.getStateCasecumsum().size()-1).getConfirmed());
        recoveredTextView.setText("Recovered: " + stateChoiceModelList.getStateCasecumsum().get(stateChoiceModelList.getStateCasecumsum().size()-1).getRecovered());
        deceasedTextView.setText("Deceased: " + stateChoiceModelList.getStateCasecumsum().get(stateChoiceModelList.getStateCasecumsum().size()-1).getDeaths());
        activeTextView.setText("Active: " + stateChoiceModelList.getStateCasecumsum().get(stateChoiceModelList.getStateCasecumsum().size()-1).getActive());
    }

    private void plotStateTotalLineChart(StateChoiceModel stateChoiceModelList) {

        List<StateCase> stateDailyCaseList = stateChoiceModelList.getStateCase();
        List<StateCase> stateDailyCaseSubList = stateDailyCaseList.subList(stateDailyCaseList.size() - 30, stateDailyCaseList.size());
        for (int j=0; j<stateDailyCaseSubList.size(); j++){
            Log.i("IndiaStates", stateDailyCaseSubList.get(j).getDate() + stateDailyCaseSubList.get(j).getConfirmed() + stateDailyCaseSubList.get(j).getRecovered() + stateDailyCaseSubList.get(j).getDeaths()+"-------------");
        }

        List<Entry> entriesConfirmed = new ArrayList<>();
        List<Entry> entriesRecovered = new ArrayList<>();
        List<Entry> entriesDeceased = new ArrayList<>();
        final String[] labels = new String[stateDailyCaseSubList.size()+1];
        for (int i=0; i<stateDailyCaseSubList.size(); i++){
            entriesConfirmed.add(new Entry(Integer.parseInt(stateDailyCaseSubList.get(i).getDate().substring(8,10)), Integer.parseInt(stateDailyCaseSubList.get(i).getConfirmed())));
            entriesRecovered.add(new Entry(Integer.parseInt(stateDailyCaseSubList.get(i).getDate().substring(8,10)), Integer.parseInt(stateDailyCaseSubList.get(i).getRecovered())));
            entriesDeceased.add(new Entry(Integer.parseInt(stateDailyCaseSubList.get(i).getDate().substring(8,10)), Integer.parseInt(stateDailyCaseSubList.get(i).getDeaths())));
            labels[i+1] = stateDailyCaseSubList.get(i).getDate().substring(8,10) + "/" + stateDailyCaseSubList.get(i).getDate().substring(5,7);
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

        LineDataSet recoveredDataSet = new LineDataSet(entriesRecovered, "Cured");
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
        mStateTotalLineChart.setData(lineData);

        XAxis xAxis = mStateTotalLineChart.getXAxis();
        xAxis.setValueFormatter(valueFormatter);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        mStateTotalLineChart.setGridBackgroundColor(Color.GRAY);
        mStateTotalLineChart.getAxisRight().setEnabled(false);
        mStateTotalLineChart.getDescription().setText("Zoom In/Out or Tap on dots | X-axis:DD/MM | Y-axis:Cases count");
        mStateTotalLineChart.getDescription().setTextSize(10);
        mStateTotalLineChart.invalidate();
        mStateTotalLineChart.setTouchEnabled(true);
        IMarker mv = new CustomMarkerView(getContext(), R.layout.chart_marker);
        mStateTotalLineChart.setMarker(mv);
    }


    private void plotStateTestLineChart(StateChoiceModel stateChoiceModelList) {
        List<StateTest> stateDailyCaseList = stateChoiceModelList.getStateTest();
        List<StateTest> stateDailyCaseSubList = stateDailyCaseList.subList(stateDailyCaseList.size() - 31, stateDailyCaseList.size()-1);

        for (int i=0; i< stateDailyCaseSubList.size(); i++){
            Log.i("IndiaState", stateDailyCaseSubList.get(i).getDate() + "--" + stateDailyCaseSubList.get(i).getValue() + "-----------------------------------");
        }
        List<Entry> entriesTested = new ArrayList<>();
        final String[] labels = new String[stateDailyCaseSubList.size()+1];
        for (int i=0; i<stateDailyCaseSubList.size(); i++){
            entriesTested.add(new Entry(Integer.parseInt(stateDailyCaseSubList.get(i).getDate().substring(8,10)), Math.round(Float.parseFloat(stateDailyCaseSubList.get(i).getValue()))));
            labels[i+1] = stateDailyCaseSubList.get(i).getDate().substring(8,10) + "/" + stateDailyCaseSubList.get(i).getDate().substring(5,7);
            Log.i("Label", "plotStateTestLineChart:------------- " + labels[i+1]);
        }

        ValueFormatter valueFormatter = new ValueFormatter() {
            @Override
            public String getAxisLabel(float value, AxisBase axis) {
                return labels[(int)value];
            }
        };

        LineDataSet testDataSet = new LineDataSet(entriesTested, "Tested");
        testDataSet.setColor(getResources().getColor(R.color.tested_light));
        testDataSet.setCircleColor(getResources().getColor(R.color.tested_dark));
        testDataSet.setCircleHoleColor(getResources().getColor(R.color.tested_dark));
        testDataSet.setCircleRadius(2);
        testDataSet.setDrawValues(false);


        LineData lineData = new LineData();
        lineData.addDataSet(testDataSet);

        mStateTestLineChart.setData(lineData);

        XAxis xAxis = mStateTestLineChart.getXAxis();
        xAxis.setValueFormatter(valueFormatter);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        mStateTestLineChart.setGridBackgroundColor(Color.GRAY);
        mStateTestLineChart.getAxisRight().setEnabled(false);
        mStateTestLineChart.getDescription().setText("Zoom In/Out or Tap on dots | X-axis:DD/MM | Y-axis:Cases count");
        mStateTestLineChart.getDescription().setTextSize(10);
        mStateTestLineChart.invalidate();
        mStateTestLineChart.setTouchEnabled(true);

        IMarker mv = new CustomMarkerView(getContext(), R.layout.chart_marker);
        mStateTestLineChart.setMarkerView(mv);
    }

    private void fillTable(StateChoiceModel stateChoiceModelScore) {
        Score stateScore = stateChoiceModelScore.getScore();
        confirmScore.setText(stateScore.getConfirmedCasesPerMillion() + "/ 10");
        totalScore.setText(stateScore.getTotalTestsPerMillion() + "/ 10");
        positivityScore.setText(stateScore.getTestPositivityRate() + "/ 10");
        mortalityScore.setText(stateScore.getMortalityRate() + "/ 10");
        zoneScore.setText(stateScore.getZone() + "/ 10");
        cumulativeScore.setText(stateScore.getCalculatedScore() + "/50");
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
}
