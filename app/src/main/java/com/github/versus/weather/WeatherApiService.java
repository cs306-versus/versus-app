package com.github.versus.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherApiService {

    @GET("VisualCrossingWebServices/rest/services/timeline/{location}/{date1}")
    Call<WeatherResponse> getWeatherTimeline(
            @Path("location") String location,
            @Path("date1") String date1,
            @Query("key") String apiKey
    );
}
