package com.mahafuz.covid19tracker.Fragment;

import android.content.res.Resources;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.mahafuz.covid19tracker.R;

public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    CardView homeCardActive, homeCardRecovered, homeDeadActive;
    int screenWidth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        homeCardActive = getView().findViewById(R.id.homeCardActive);
        homeCardRecovered = getView().findViewById(R.id.homeCardRecovered);
        homeDeadActive = getView().findViewById(R.id.homeDeadActive);
    }

}
