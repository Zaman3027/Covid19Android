

package com.mahafuz.covid19tracker.Model;

        import java.util.List;
        import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class InfectedProbabilityModel {

    @SerializedName("ageRange")
    @Expose
    private List<AgeRange> ageRange = null;
    @SerializedName("genderRatio")
    @Expose
    private GenderRatio genderRatio;

    public List<AgeRange> getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(List<AgeRange> ageRange) {
        this.ageRange = ageRange;
    }

    public GenderRatio getGenderRatio() {
        return genderRatio;
    }

    public void setGenderRatio(GenderRatio genderRatio) {
        this.genderRatio = genderRatio;
    }

}