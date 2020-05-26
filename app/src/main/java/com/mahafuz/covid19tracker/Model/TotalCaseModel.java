package com.mahafuz.covid19tracker.Model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotalCaseModel {

    @SerializedName("totalactive")
    @Expose
    private List<Totalactive> totalactive = null;
    @SerializedName("totalconfirmed")
    @Expose
    private List<Totalconfirmed> totalconfirmed = null;
    @SerializedName("totaldeceased")
    @Expose
    private List<Totaldeceased> totaldeceased = null;
    @SerializedName("totalrecovered")
    @Expose
    private List<Totalrecovered> totalrecovered = null;

    public List<Totalactive> getTotalactive() {
        return totalactive;
    }

    public void setTotalactive(List<Totalactive> totalactive) {
        this.totalactive = totalactive;
    }

    public List<Totalconfirmed> getTotalconfirmed() {
        return totalconfirmed;
    }

    public void setTotalconfirmed(List<Totalconfirmed> totalconfirmed) {
        this.totalconfirmed = totalconfirmed;
    }

    public List<Totaldeceased> getTotaldeceased() {
        return totaldeceased;
    }

    public void setTotaldeceased(List<Totaldeceased> totaldeceased) {
        this.totaldeceased = totaldeceased;
    }

    public List<Totalrecovered> getTotalrecovered() {
        return totalrecovered;
    }

    public void setTotalrecovered(List<Totalrecovered> totalrecovered) {
        this.totalrecovered = totalrecovered;
    }

}