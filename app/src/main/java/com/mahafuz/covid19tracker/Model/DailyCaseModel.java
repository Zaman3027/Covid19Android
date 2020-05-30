package com.mahafuz.covid19tracker.Model;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class DailyCaseModel {

    @SerializedName("confirmed")
    @Expose
    private String confirmed;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("deceased")
    @Expose
    private String deceased;
    @SerializedName("recovered")
    @Expose
    private String recovered;

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getDate() {
        return date.substring(0, 10);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDeceased() {
        return deceased;
    }

    public void setDeceased(String deceased) {
        this.deceased = deceased;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

}