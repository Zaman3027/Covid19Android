package com.mahafuz.covid19tracker.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Score {

    @SerializedName("ConfirmedCasesPerMillion")
    @Expose
    private Integer confirmedCasesPerMillion;
    @SerializedName("MortalityRate")
    @Expose
    private Integer mortalityRate;
    @SerializedName("TestPositivityRate")
    @Expose
    private Integer testPositivityRate;
    @SerializedName("TotalTestsPerMillion")
    @Expose
    private Integer totalTestsPerMillion;
    @SerializedName("calculatedScore")
    @Expose
    private Integer calculatedScore;
    @SerializedName("zone")
    @Expose
    private Integer zone;

    public Integer getConfirmedCasesPerMillion() {
        return confirmedCasesPerMillion;
    }

    public void setConfirmedCasesPerMillion(Integer confirmedCasesPerMillion) {
        this.confirmedCasesPerMillion = confirmedCasesPerMillion;
    }

    public Integer getMortalityRate() {
        return mortalityRate;
    }

    public void setMortalityRate(Integer mortalityRate) {
        this.mortalityRate = mortalityRate;
    }

    public Integer getTestPositivityRate() {
        return testPositivityRate;
    }

    public void setTestPositivityRate(Integer testPositivityRate) {
        this.testPositivityRate = testPositivityRate;
    }

    public Integer getTotalTestsPerMillion() {
        return totalTestsPerMillion;
    }

    public void setTotalTestsPerMillion(Integer totalTestsPerMillion) {
        this.totalTestsPerMillion = totalTestsPerMillion;
    }

    public Integer getCalculatedScore() {
        return calculatedScore;
    }

    public void setCalculatedScore(Integer calculatedScore) {
        this.calculatedScore = calculatedScore;
    }

    public Integer getZone() {
        return zone;
    }

    public void setZone(Integer zone) {
        this.zone = zone;
    }

}