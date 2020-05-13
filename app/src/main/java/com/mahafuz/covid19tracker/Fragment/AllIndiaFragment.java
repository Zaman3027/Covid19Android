package com.mahafuz.covid19tracker.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.google.gson.Gson;
import com.mahafuz.covid19tracker.Adapter.AllIndiaStateAdapter;
import com.mahafuz.covid19tracker.ApiInterface.FetchData;
import com.mahafuz.covid19tracker.ApiInterface.FetchStateWise;
import com.mahafuz.covid19tracker.ApiInterface.GetJSONString;
import com.mahafuz.covid19tracker.ApiInterface.GetStateJSON;
import com.mahafuz.covid19tracker.BaseAct;
import com.mahafuz.covid19tracker.Home;
import com.mahafuz.covid19tracker.Interface.FragmentCall;
import com.mahafuz.covid19tracker.Model.Cases_time_series;
import com.mahafuz.covid19tracker.Model.SateWiseModel;
import com.mahafuz.covid19tracker.Model.StateWiseModelNew;
import com.mahafuz.covid19tracker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllIndiaFragment extends Fragment implements FragmentCall, GetStateJSON {
    boolean isDetailShowing = false;

    public AllIndiaFragment() {
        // Required empty public constructor
    }

    RecyclerView all_india_recycler_view;
    RelativeLayout detailsAllIndia;
    AnyChartView any_chart_view_India;
    List<SateWiseModel> sateWiseModelList;
    ProgressDialog progressDialog;
    TextView allIndiaConfirm, allIndiaActive, allIndiaDesc, allIndiaSate;
    JSONArray stateWiseData;
    List<StateWiseModelNew> stateWiseModelNewList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_india, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        all_india_recycler_view = getView().findViewById(R.id.all_india_recycler_view);
        all_india_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        any_chart_view_India = getView().findViewById(R.id.any_chart_view_India);
        detailsAllIndia = getView().findViewById(R.id.detailsAllIndia);
        sateWiseModelList = new ArrayList<>();
        progressDialog = new ProgressDialog(getContext());
        allIndiaDesc = getView().findViewById(R.id.allIndiaDesc);
        allIndiaActive = getView().findViewById(R.id.allIndiaActive);
        allIndiaConfirm = getView().findViewById(R.id.allIndiaConfirm);
        allIndiaSate = getView().findViewById(R.id.allIndiaSate);
        stateWiseModelNewList = new ArrayList<>();


        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading Data");
        progressDialog.show();
        FetchData fetchData = new FetchData(new GetJSONString() {
            @Override
            public void getData(String data) {

                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray jsonArray = jsonObject.getJSONArray("cases_time_series");
                    JSONArray stateWiseJsonArray = jsonObject.getJSONArray("statewise");
                    Gson gson = new Gson();
                    sateWiseModelList.clear();
                    for (int i = 0; i < stateWiseJsonArray.length(); i++) {
                        sateWiseModelList.add(gson.fromJson(stateWiseJsonArray.getString(i), SateWiseModel.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (sateWiseModelList.size() > 0) {
                    FetchStateWise fetchStateWise = new FetchStateWise(AllIndiaFragment.this);
                    fetchStateWise.execute();
                }

            }
        });
        fetchData.execute();
    }

    @Override
    public void indiaFragmentCall(int position, String stateCode) {
        if (!isDetailShowing) {
            any_chart_view_India.setVisibility(View.VISIBLE);
            detailsAllIndia.setVisibility(View.VISIBLE);
            isDetailShowing = !isDetailShowing;
        }
        allIndiaConfirm.setText(sateWiseModelList.get(position).getConfirmed());
        allIndiaActive.setText(sateWiseModelList.get(position).getActive());
        allIndiaDesc.setText(sateWiseModelList.get(position).getDeaths());
        allIndiaSate.setText(sateWiseModelList.get(position).getState());
        for (int i = 0; i < stateWiseData.length(); i++) {
            try {
                JSONObject jsonObject = stateWiseData.getJSONObject(i);
                stateWiseModelNewList.add(new StateWiseModelNew(
                        jsonObject.getString(stateCode),
                        jsonObject.getString("date"),
                        jsonObject.getString("status")
                ));

                Log.i("ALLINDIA", jsonObject.getString("status"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void getData(JSONArray data) {
        this.stateWiseData = data;
        all_india_recycler_view.setAdapter(new AllIndiaStateAdapter(AllIndiaFragment.this::indiaFragmentCall, sateWiseModelList));
        progressDialog.dismiss();


    }
}
