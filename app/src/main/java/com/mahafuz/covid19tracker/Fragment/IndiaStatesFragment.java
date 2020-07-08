package com.mahafuz.covid19tracker.Fragment;

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

import com.mahafuz.covid19tracker.Adapter.AllIndiaStateAdapter;
import com.mahafuz.covid19tracker.BaseAct;
import com.mahafuz.covid19tracker.Interface.FragmentCall;
import com.mahafuz.covid19tracker.Module.AndroidModule;
import com.mahafuz.covid19tracker.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndiaStatesFragment extends Fragment implements FragmentCall {

    public IndiaStatesFragment() {
        // Required empty public constructor
    }

    RecyclerView allIndiaStatesRecyclerView;
    AndroidModule androidModule;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
        return inflater.inflate(R.layout.fragment_india_states_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        androidModule = new AndroidModule(getContext());
        allIndiaStatesRecyclerView = getView().findViewById(R.id.all_india_states_recycler_view);
        AllIndiaStateAdapter adapter = new AllIndiaStateAdapter(this::indiaFragmentCall, getResources().getStringArray(R.array.india_states));
        allIndiaStatesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        allIndiaStatesRecyclerView.setAdapter(adapter);
    }

    @Override
    public void indiaFragmentCall(int position, String stateName) {
        getFragmentManager().beginTransaction().replace(R.id.navigation_drawer_frame, new IndiaStatesChartFragment(stateName.toLowerCase()))
                .addToBackStack("home")
                .commit();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        androidModule.dispose();
    }
}
