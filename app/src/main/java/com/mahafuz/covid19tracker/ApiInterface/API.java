package com.mahafuz.covid19tracker.ApiInterface;

import com.mahafuz.covid19tracker.Model.RawDataModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {
    String BASE_URL = "https://api.covid19india.org/raw_data3.json";

    @GET("/")
    Call<RawDataModel> getRawData();
}
