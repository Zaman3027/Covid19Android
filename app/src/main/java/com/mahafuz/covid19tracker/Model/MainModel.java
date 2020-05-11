package com.mahafuz.covid19tracker.Model;

public class MainModel {
    private RawDataModel[] raw_data;

    public RawDataModel[] getRaw_data() {
        return raw_data;
    }

    public void setRaw_data(RawDataModel[] raw_data) {
        this.raw_data = raw_data;
    }

    @Override
    public String toString() {
        return "ClassPojo [raw_data = " + raw_data + "]";
    }
}
