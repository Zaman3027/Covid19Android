
package com.mahafuz.covid19tracker.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TestingModel {

    @SerializedName("Date")
    @Expose
    private String date;
    @SerializedName("testspermillion")
    @Expose
    private String testspermillion;
    @SerializedName("totalsamplestested")
    @Expose
    private String totalsamplestested;

    public String getDate() {
        return date.substring(0, 10);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTestspermillion() {
        if (testspermillion.equals("nan"))
            return "0";
        return testspermillion;
    }

    public void setTestspermillion(String testspermillion) {

        this.testspermillion = testspermillion;
    }

    public String getTotalsamplestested() {
        if (totalsamplestested.equals("nan"))
            return "0";
        return totalsamplestested;
    }

    public void setTotalsamplestested(String totalsamplestested) {
        this.totalsamplestested = totalsamplestested;
    }

}