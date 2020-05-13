package com.mahafuz.covid19tracker.Model;

public class StateWiseModelNew {
    String count;
    String date;
    String state;

    public StateWiseModelNew(String count, String date, String state) {
        this.count = count;
        this.date = date;
        this.state = state;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
