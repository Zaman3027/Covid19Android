package com.mahafuz.covid19tracker.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InfectedProbabilityModel {

    @SerializedName("ageRange")
    @Expose
    private List<AgeRangeModel> ageRange = null;
    @SerializedName("genderRatio")
    @Expose
    private GenderRatio genderRatio;
    @SerializedName("prob")
    @Expose
    private Prob prob;

    public List<AgeRangeModel> getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(List<AgeRangeModel> ageRange) {
        this.ageRange = ageRange;
    }

    public GenderRatio getGenderRatio() {
        return genderRatio;
    }

    public void setGenderRatio(GenderRatio genderRatio) {
        this.genderRatio = genderRatio;
    }

    public Prob getProb() {
        return prob;
    }

    public void setProb(Prob prob) {
        this.prob = prob;
    }
}