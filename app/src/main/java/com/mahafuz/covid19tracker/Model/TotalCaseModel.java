package com.mahafuz.covid19tracker.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotalCaseModel {

    @SerializedName("active")
    @Expose
    private String active;
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

    public String getDate() {
        return date;
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