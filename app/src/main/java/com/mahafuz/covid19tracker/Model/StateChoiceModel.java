package com.mahafuz.covid19tracker.Model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StateChoiceModel {

    @SerializedName("stateCase")
    @Expose
    private List<StateCase> stateCase = null;
    @SerializedName("stateTest")
    @Expose
    private List<StateTest> stateTest = null;

    public List<StateCase> getStateCase() {
        return stateCase;
    }

    public void setStateCase(List<StateCase> stateCase) {
        this.stateCase = stateCase;
    }

    public List<StateTest> getStateTest() {
        return stateTest;
    }

    public void setStateTest(List<StateTest> stateTest) {
        this.stateTest = stateTest;
    }

}