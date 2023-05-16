package com.github.versus.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherApiService {

    @GET("VisualCrossingWebServices/rest/services/timeline/{latitude},{longitude}/{date1}")
     Call<WeatherResponse> getWeatherTimeline(
            @Path("latitude") String latitude,
            @Path("longitude") String longitude,
            @Path("date1") String date,
            @Query("key") String apiKey
    );
}
