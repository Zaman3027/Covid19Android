package com.mahafuz.covid19tracker.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateTest {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("value")
    @Expose
    private String value;

    public String getDate() {
        return date.substring(0, 10);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}