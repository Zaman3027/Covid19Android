package com.mahafuz.covid19tracker.Model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyCaseModel {

    @SerializedName("dailyconfirmed")
    @Expose
    private List<Dailyconfirmed> dailyconfirmed = null;
    @SerializedName("dailydeceased")
    @Expose
    private List<Dailydeceased> dailydeceased = null;
    @SerializedName("dailyrecovered")
    @Expose
    private List<Dailyrecovered> dailyrecovered = null;

    public List<Dailyconfirmed> getDailyconfirmed() {
        return dailyconfirmed;
    }

    public void setDailyconfirmed(List<Dailyconfirmed> dailyconfirmed) {
        this.dailyconfirmed = dailyconfirmed;
    }

    public List<Dailydeceased> getDailydeceased() {
        return dailydeceased;
    }

    public void setDailydeceased(List<Dailydeceased> dailydeceased) {
        this.dailydeceased = dailydeceased;
    }

    public List<Dailyrecovered> getDailyrecovered() {
        return dailyrecovered;
    }

    public void setDailyrecovered(List<Dailyrecovered> dailyrecovered) {
        this.dailyrecovered = dailyrecovered;
    }

}