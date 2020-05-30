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
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mahafuz.covid19tracker.Adapter.AllIndiaStateAdapter;
import com.mahafuz.covid19tracker.ApiInterface.FetchData;
import com.mahafuz.covid19tracker.ApiInterface.FetchStateWise;
import com.mahafuz.covid19tracker.ApiInterface.GetJSONString;
import com.mahafuz.covid19tracker.ApiInterface.GetStateJSON;
import com.mahafuz.covid19tracker.ApiInterface.RetroFitInstance;
import com.mahafuz.covid19tracker.Interface.FragmentCall;
import com.mahafuz.covid19tracker.Model.DailyCaseModel;
import com.mahafuz.covid19tracker.Module.AndroidModule;
import com.mahafuz.covid19tracker.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllIndiaFragment extends Fragment implements FragmentCall {
    boolean isDetailShowing = false;

    public AllIndiaFragment() {
        // Required empty public constructor
    }

    RecyclerView all_india_recycler_view;
    AndroidModule androidModule;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_all_india, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        androidModule = new AndroidModule(getContext());
        all_india_recycler_view = getView().findViewById(R.id.all_india_recycler_view);
        all_india_recycler_view.setLayoutManager(new LinearLayoutManager(getContext()));
        all_india_recycler_view.setAdapter(new AllIndiaStateAdapter(this::indiaFragmentCall, getResources().getStringArray(R.array.india_states)));
    }

    @Override
    public void indiaFragmentCall(int position, String stateCode) {
        getFragmentManager().beginTransaction().replace(R.id.navigation_drawer_frame, new AllIndiaChartFragment(stateCode.toLowerCase()))
                .addToBackStack("home")
                .commit();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        androidModule.dispose();
    }
}
