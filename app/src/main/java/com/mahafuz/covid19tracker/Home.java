package com.mahafuz.covid19tracker;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.mahafuz.covid19tracker.ApiInterface.FetchData;
import com.mahafuz.covid19tracker.ApiInterface.GetJSONString;
import com.mahafuz.covid19tracker.Fragment.HomeFragment;
import com.mahafuz.covid19tracker.Model.Cases_time_series;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Home extends BaseAct {
    List<Cases_time_series> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        showDialog("Pelase Wait", "Loading Data");

        FetchData fetchData = new FetchData(new GetJSONString() {
            @Override
            public void getData(String data) {

                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray jsonArray = jsonObject.getJSONArray("cases_time_series");
                    Gson gson = new Gson();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.i("DATA", gson.fromJson(jsonArray.getString(i), Cases_time_series.class).getDate());
                        list.add(gson.fromJson(jsonArray.getString(i), Cases_time_series.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissDialog();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.navigation_drawer_frame, new HomeFragment(list))
                        .commit();
            }
        });
        fetchData.execute();


    }
}
