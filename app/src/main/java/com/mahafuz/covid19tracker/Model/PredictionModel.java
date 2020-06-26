
package com.mahafuz.covid19tracker.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PredictionModel {

    @SerializedName("ds")
    @Expose
    private String ds;
    @SerializedName("yhat")
    @Expose
    private Double yhat;
    @SerializedName("yhat_lower")
    @Expose
    private Double yhatLower;
    @SerializedName("yhat_upper")
    @Expose
    private Double yhatUpper;

    public String getDs() {
        return ds;
    }

    public void setDs(String ds) {
        this.ds = ds;
    }

    public Double getYhat() {
        return yhat;
    }

    public void setYhat(Double yhat) {
        this.yhat = yhat;
    }

    public Double getYhatLower() {
        return yhatLower;
    }

    public void setYhatLower(Double yhatLower) {
        this.yhatLower = yhatLower;
    }

    public Double getYhatUpper() {
        return yhatUpper;
    }

    public void setYhatUpper(Double yhatUpper) {
        this.yhatUpper = yhatUpper;
    }

}