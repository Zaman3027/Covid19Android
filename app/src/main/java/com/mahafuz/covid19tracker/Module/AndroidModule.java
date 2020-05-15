package com.mahafuz.covid19tracker.Module;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.mahafuz.covid19tracker.R;

public class AndroidModule {
    private Context context;
    private AlertDialog alertDialog;

    public static AndroidModule getInstance(Context context) {
        return new AndroidModule(context);
    }

    public AndroidModule(Context context) {
        this.context = context;


    }

    public void showLoadingDialogue() {
        View loadingView = LayoutInflater.from(context).inflate(R.layout.loading_screen, null);
        alertDialog = new AlertDialog.Builder(context, R.style.customDialog)
                .setView(loadingView)
                .create();
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setLayout(100, 100);
        alertDialog.show();
    }

    public void dismissDialogue() {
        if (alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    public void dispose() {
        alertDialog = null;
    }
}
