package com.mahafuz.covid19tracker.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateChoiceModel {

    @SerializedName("score")
    @Expose
    private Score score;
    @SerializedName("stateCase")
    @Expose
    private List<StateCase> stateCase = null;
    @SerializedName("stateCasecumsum")
    @Expose
    private List<StateCase> stateCasecumsum = null;
    @SerializedName("stateTest")
    @Expose
    private List<StateTest> stateTest = null;

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public List<StateCase> getStateCase() {
        return stateCase;
    }

    public void setStateCase(List<StateCase> stateCase) {
        this.stateCase = stateCase;
    }

    public List<StateCase> getStateCasecumsum() {
        return stateCasecumsum;
    }

    public void setStateCasecumsum(List<StateCase> stateCasecumsum) {
        this.stateCasecumsum = stateCasecumsum;
    }

    public List<StateTest> getStateTest() {
        return stateTest;
    }

    public void setStateTest(List<StateTest> stateTest) {
        this.stateTest = stateTest;
    }

}