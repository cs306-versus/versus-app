package com.github.versus.weather;

import com.github.versus.offline.NetworkManager;
import com.github.versus.posts.Location;
import com.github.versus.posts.Timestamp;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class WeatherService {
    private static final String BASE_URL = "https://weather.visualcrossing.com/";
    private static final String API_KEY= "Y8QEGKP8ASQWLATHV8UBM4DJB";
    private static Retrofit retrofit= new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static WeatherApiService weatherApiService = retrofit.create(WeatherApiService.class);

    private static Map<String,String> fetched_weather = new HashMap<>();

    public static Map<String,String> getWeather(Location location, Timestamp timestamp){
        String date = timestamp.getYear() + "-" +
                String.format(Locale.ENGLISH,"%02d", timestamp.getMonth().getValue())
                + "-" +  String.format(Locale.ENGLISH,"%02d", timestamp.getDay());
        String latitude= String.valueOf(location.getLatitude());
        String longitude= String.valueOf(location.getLongitude());
        Call<WeatherResponse> call= weatherApiService.getWeatherTimeline(latitude,longitude, date, API_KEY);
        try {
            Response<WeatherResponse> response = call.execute();
            if (response.isSuccessful()) {
                WeatherResponse weather = response.body();
                fetched_weather.put("description",weather.getDescription());
                double tempF = weather.getDays().get(0).getTemp();
                fetched_weather.put("temperature", String.valueOf(toCelsius(tempF)));
                return new HashMap<>(fetched_weather);
                }
            else {
                Map<String,String> error_map= new HashMap<>();
                error_map.put(response.message(),String.valueOf(response.code()));
                return error_map;
            }

        } catch (Exception e) {
            fetched_weather.clear();
            Map<String,String> error_map= new HashMap<>();
            error_map.put(e.getClass().getSimpleName(),e.getMessage());
            return error_map;
        }
    }

    public static CompletableFuture<Map<String,String>> getWeatherAsynchronously(Location location, Timestamp timestamp){
        return CompletableFuture.supplyAsync(()->getWeather(location,timestamp));
    }

    private WeatherService(){}

    private static int toCelsius(double tempF){
        return (int)((tempF-32)/1.8);
    }
}
