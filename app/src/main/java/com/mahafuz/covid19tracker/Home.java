package com.mahafuz.covid19tracker;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

import com.mahafuz.covid19tracker.Fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class Home extends BaseAct {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.navigation_drawer_frame, new HomeFragment(), "Home")
                .addToBackStack("Home")
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("COUNT OF STack", "" + getSupportFragmentManager().getBackStackEntryCount());
        if (getSupportFragmentManager().getBackStackEntryCount() > 2) {
            getSupportFragmentManager().popBackStack();
        }
    }

}
