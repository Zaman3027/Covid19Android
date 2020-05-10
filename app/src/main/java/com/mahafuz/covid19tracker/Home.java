package com.mahafuz.covid19tracker;

import android.os.Bundle;

import com.mahafuz.covid19tracker.Fragment.HomeFragment;

public class Home extends BaseAct {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.navigation_drawer_frame, new HomeFragment())
                .commit();

    }
}
