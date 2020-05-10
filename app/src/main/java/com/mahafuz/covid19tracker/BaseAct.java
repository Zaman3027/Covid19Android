package com.mahafuz.covid19tracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;

public class BaseAct extends AppCompatActivity {
    ProgressDialog progressDialog;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(
                new ColorDrawable(Color.argb(0, 255, 255, 255)));
        actionBar.setElevation(0);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_toolbar, null));
    }

    void showDialog(String title, String message) {
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    void dismissDialog() {
        progressDialog.dismiss();
    }
}
