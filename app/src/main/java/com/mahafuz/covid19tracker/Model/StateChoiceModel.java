package com.mahafuz.covid19tracker.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateChoiceModel {

    @SerializedName("Active")
    @Expose
    private String active;
    @SerializedName("Confirmed")
    @Expose
    private String confirmed;
    @SerializedName("Deaths")
    @Expose
    private String deaths;
    @SerializedName("Recovered")
    @Expose
    private String recovered;
    @SerializedName("date")
    @Expose
    private String date;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(String confirmed) {
        this.confirmed = confirmed;
    }

    public String getDeaths() {
        return deaths;
    }

    public void setDeaths(String deaths) {
        this.deaths = deaths;
    }

    public String getRecovered() {
        return recovered;
    }

    public void setRecovered(String recovered) {
        this.recovered = recovered;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}