package com.mahafuz.covid19tracker.Model;

public class DailyModel {
    private Cases_time_series[] cases_time_series;

    private TestedModel[] tested;

    private SateWiseModel[] statewise;

    public Cases_time_series[] getCases_time_series() {
        return cases_time_series;
    }

    public void setCases_time_series(Cases_time_series[] cases_time_series) {
        this.cases_time_series = cases_time_series;
    }

    public TestedModel[] getTested() {
        return tested;
    }

    public void setTested(TestedModel[] tested) {
        this.tested = tested;
    }

    public SateWiseModel[] getStatewise() {
        return statewise;
    }

    public void setStatewise(SateWiseModel[] statewise) {
        this.statewise = statewise;
    }

    @Override
    public String toString() {
        return "ClassPojo [cases_time_series = " + cases_time_series + ", tested = " + tested + ", statewise = " + statewise + "]";
    }
}