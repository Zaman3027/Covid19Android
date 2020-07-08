package com.mahafuz.covid19tracker.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
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

import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.mahafuz.covid19tracker.ApiInterface.RetroFitInstance;
import com.mahafuz.covid19tracker.Model.DailyCaseModel;
import com.mahafuz.covid19tracker.Model.TotalCaseModel;
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
    TextView cardActive, cardRecovered, cardDeceased;
    CardView cardIndiaStates, cardDemographic, cardInfectedProbability, cardModelPrediction, cardAboutUs;
    ProgressDialog progressDialog;
    AndroidModule androidModule;
    RetroFitInstance retroFitInstance;
    List<DailyCaseModel> dailyCaseModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dailyCaseModel = new ArrayList<>();
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
        cardActive = getView().findViewById(R.id.cardActive);
        cardRecovered = getView().findViewById(R.id.cardRecovered);
        cardDeceased = getView().findViewById(R.id.cardDeceased);
        cardIndiaStates = getView().findViewById(R.id.card_all_india);
        cardDemographic = getView().findViewById(R.id.card_demographic);
        cardInfectedProbability = getView().findViewById(R.id.card_infected_probability);
        cardModelPrediction = getView().findViewById(R.id.card_model_prediction);
        cardAboutUs = getView().findViewById(R.id.card_about_us);
        retroFitInstance = new RetroFitInstance();
        androidModule = AndroidModule.getInstance(getContext());
        androidModule.showLoadingDialogue();

        // Show confirmed, cured, deceased cards
        retroFitInstance.getApi().getTotalCaseModel().enqueue(new Callback<List<TotalCaseModel>>() {
            @Override
            public void onResponse(Call<List<TotalCaseModel>> call, Response<List<TotalCaseModel>> response) {
                if (response.isSuccessful()) {
                    cardDeceased.setText(response.body().get(response.body().size() - 1).getDeceased());
                    cardRecovered.setText(response.body().get(response.body().size() - 1).getRecovered());
                    cardActive.setText(response.body().get(response.body().size() - 1).getConfirmed());
                }
            }

            @Override
            public void onFailure(Call<List<TotalCaseModel>> call, Throwable t) {

            }
        });

        //  To plot daily cases chart
        retroFitInstance.getApi().getCaseModelCall().enqueue(new Callback<List<DailyCaseModel>>() {
            @Override
            public void onResponse(Call<List<DailyCaseModel>> call, Response<List<DailyCaseModel>> response) {
                androidModule.dismissDialogue();
                if (response.isSuccessful()) {
                    dailyCaseModel = response.body();
                    getFragmentManager().beginTransaction()
                            .replace(R.id.mp_chart_view_fragment, new HomeChartFragment(dailyCaseModel))
                            .commit();
                }
            }

            @Override
            public void onFailure(Call<List<DailyCaseModel>> call, Throwable t) {

            }
        });

        cardIndiaStates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.navigation_drawer_frame, new IndiaStatesFragment(), "All India")
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

        cardInfectedProbability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.navigation_drawer_frame, new InfectedProbFragment(), "Infected Probability")
                        .addToBackStack("Infected Probability")
                        .commit();
            }
        });

        cardModelPrediction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PredictionActivity.class));
            }
        });

        cardAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.navigation_drawer_frame, new AboutUs(), "About Us")
                        .addToBackStack("About Us")
                        .commit();
            }
        });
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
