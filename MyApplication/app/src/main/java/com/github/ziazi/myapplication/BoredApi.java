package com.github.ziazi.myapplication;
import retrofit2.Call;
import retrofit2.http.GET;

public interface BoredApi {
    String URL= "https://www.boredapi.com/api/";
    @GET("activity")
    Call<BoredActivity> getActivity();
}
