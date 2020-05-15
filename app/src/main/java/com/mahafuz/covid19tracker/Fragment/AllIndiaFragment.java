package com.mahafuz.covid19tracker.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
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
import com.mahafuz.covid19tracker.Module.AndroidModule;
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
    List<SateWiseModel> sateWiseModelList;
    ProgressDialog progressDialog;
    TextView allIndiaConfirm, allIndiaActive, allIndiaDesc, allIndiaSate;
    JSONArray stateWiseData;
    List<StateWiseModelNew> stateWiseModelNewList;
    FrameLayout allIndiaFrame;
    AndroidModule androidModule;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_all_india, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        all_india_recycler_view = getView().findViewById(R.id.all_india_recycler_view);
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        all_india_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        allIndiaFrame = getView().findViewById(R.id.allIndiaFrame);
        detailsAllIndia = getView().findViewById(R.id.detailsAllIndia);
        sateWiseModelList = new ArrayList<>();
        progressDialog = new ProgressDialog(getContext());
        allIndiaDesc = getView().findViewById(R.id.allIndiaDesc);
        allIndiaActive = getView().findViewById(R.id.allIndiaActive);
        allIndiaConfirm = getView().findViewById(R.id.allIndiaConfirm);
        allIndiaSate = getView().findViewById(R.id.allIndiaSate);
        stateWiseModelNewList = new ArrayList<>();
        androidModule = AndroidModule.getInstance(getContext());

//        progressDialog.setTitle("Please Wait");
//        progressDialog.setMessage("Loading Data");
//        progressDialog.show();
        androidModule.showLoadingDialogue();
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
            allIndiaFrame.setVisibility(View.VISIBLE);
            detailsAllIndia.setVisibility(View.VISIBLE);
            isDetailShowing = !isDetailShowing;
        }

        allIndiaConfirm.setText(sateWiseModelList.get(position).getConfirmed());
        allIndiaActive.setText(sateWiseModelList.get(position).getActive());
        allIndiaDesc.setText(sateWiseModelList.get(position).getDeaths());
        allIndiaSate.setText(sateWiseModelList.get(position).getState());
        stateWiseModelNewList.clear();
        Log.i("ALLINDIA", stateCode);
        for (int i = 0; i < stateWiseData.length(); i++) {
            try {
                JSONObject jsonObject = stateWiseData.getJSONObject(i);
                stateWiseModelNewList.add(new StateWiseModelNew(
                        jsonObject.getString(stateCode.toLowerCase()),
                        jsonObject.getString("date"),
                        jsonObject.getString("status")
                ));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        getFragmentManager().beginTransaction().replace(R.id.allIndiaFrame,new AllIndiaChartFragment(stateWiseModelNewList)).commit();

    }

    @Override
    public void getData(JSONArray data) {
        this.stateWiseData = data;
        all_india_recycler_view.setAdapter(new AllIndiaStateAdapter(AllIndiaFragment.this::indiaFragmentCall, sateWiseModelList));
        androidModule.dismissDialogue();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        androidModule.dispose();
    }
}
