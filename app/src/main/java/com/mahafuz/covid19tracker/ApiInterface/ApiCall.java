package com.mahafuz.covid19tracker.ApiInterface;

import retrofit2.Retrofit;

public class ApiCall {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API.BASE_URL)
            .build();
}
