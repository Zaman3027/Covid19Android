package com.mahafuz.covid19tracker.ApiInterface;

import com.mahafuz.covid19tracker.Model.AgeRangeModel;
import com.mahafuz.covid19tracker.Model.DailyCaseModel;
import com.mahafuz.covid19tracker.Model.GenderModel;
import com.mahafuz.covid19tracker.Model.InfectedProbabilityModel;
import com.mahafuz.covid19tracker.Model.PredictionModel;
import com.mahafuz.covid19tracker.Model.StateChoiceModel;
import com.mahafuz.covid19tracker.Model.StateTestingModel;
import com.mahafuz.covid19tracker.Model.TestingModel;
import com.mahafuz.covid19tracker.Model.TotalCaseModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface RetrofitApi {
    String BASE_URL = "https://agile-dawn-81630.herokuapp.com/";

    @Headers("Content-Type: application/json")
    @GET("/getdailycase")
    Call<List<DailyCaseModel>> getCaseModelCall();

    @GET("/gender_ratio")
    Call<GenderModel> getGenderModelCall();

//    @GET("/getAgeSample")
//    Call<List<AgeRangeModel>> getAgeRangeModelListCall();

    @GET("/gettotalcase")
    Call<List<TotalCaseModel>> getTotalCaseModel();

    @GET("/getTestPerMil")
    Call<List<TestingModel>> getTestingModelList();

    @GET("/statepermill")
    Call<List<StateTestingModel>> getStateTestingModel();

    @GET("/getstatedata/{statename}")
    Call<StateChoiceModel> getStateChoiceList(@Path("statename") String stateName);

    @GET("/infected_probability/{age}/{gender}/{state}")
    Call<InfectedProbabilityModel> getInfectedProbabilityModel(
            @Path("age") String age,
            @Path("gender") String gender,
            @Path("state") String state
    );

    @GET("/prediction")
    Call<List<PredictionModel>> getPredictionModel();
}
