package com.github.versus.weather;

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
    private static final String API_KEY= "PXK3D8BHEMG9V5DQ9K3DN93DP";
    private static Retrofit retrofit= new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static WeatherApiService weatherApiService = retrofit.create(WeatherApiService.class);


    public static Map<String,String> getWeather(Location location, Timestamp timestamp){

        Call<WeatherResponse> call= weatherApiService
                .getWeatherTimeline(String.valueOf(location.getLatitude()),
                                    String.valueOf(location.getLongitude()),
                                    formatDate(timestamp),
                                    API_KEY);
        try {
            Response<WeatherResponse> weather_response = call.execute();
            if (weather_response.isSuccessful()) {
                Map<String,String> fetched_weather= new HashMap<>();
                DayWeather day_weather= weather_response.body().getDays().get(0);
                fetched_weather.put("description",day_weather.getDescription());
                fetched_weather.put("day", day_weather.getDatetime());
                fetched_weather.put("tempmax",String.valueOf(toCelsius(day_weather.getTempmax())));
                fetched_weather.put("tempmin",String.valueOf(toCelsius(day_weather.getTempmin())));
                int hour_index= timestamp.getMeridiem() == Timestamp.Meridiem.AM?
                                timestamp.getHour(): timestamp.getHour()+12;
                HourWeather hour_weather= day_weather.getHours().get(hour_index) ;
                fetched_weather.put("time",hour_weather.getDatetime());
                fetched_weather.put("conditions", hour_weather.getConditions());
                fetched_weather.put("temperature", String.valueOf(toCelsius(hour_weather.getTemp())));
                fetched_weather.put("feelslike", String.valueOf(toCelsius(hour_weather.getFeelslike())));
                fetched_weather.put("humidity", String.valueOf(hour_weather.getHumidity()));
                fetched_weather.put("cloudcover",String.valueOf(hour_weather.getCloudcover()));
                fetched_weather.put("precip",String.valueOf(hour_weather.getPrecip()));
                fetched_weather.put("precipprob",String.valueOf(hour_weather.getPrecipprob()));
                fetched_weather.put("snow",String.valueOf(hour_weather.getSnow()));
                fetched_weather.put("snowdepth",String.valueOf(hour_weather.getSnowdepth()));
                fetched_weather.put("pressure", String.valueOf(hour_weather.getPressure()));
                fetched_weather.put("visibility", String.valueOf(hour_weather.getVisibility()));
                fetched_weather.put("windgust", String.valueOf(hour_weather.getWindgust()));
                fetched_weather.put("windspeed", String.valueOf(hour_weather.getWindspeed()));
                fetched_weather.put("winddir",String.valueOf(hour_weather.getWinddir()));
                fetched_weather.put("severerisk", String.valueOf(hour_weather.getSevererisk()));
                fetched_weather.put("solarradiation",String.valueOf(hour_weather.getSolarradiation()));
                fetched_weather.put("uvindex",String.valueOf(hour_weather.getUvindex()));
                fetched_weather.put("icon", hour_weather.getIcon());
                return fetched_weather;
                }
            else {
                Map<String,String> server_error_map= new HashMap<>();
                server_error_map.put("HTTP error",String.valueOf(weather_response.code()));
                return server_error_map;
            }

        } catch (Exception e) {
            Map<String,String> response_error_map= new HashMap<>();
            response_error_map.put(e.getClass().getSimpleName(),e.getMessage());
            return response_error_map;
        }
    }

    public static CompletableFuture<Map<String,String>> getWeatherAsynchronously(Location location, Timestamp timestamp){
        return CompletableFuture.supplyAsync(()->getWeather(location,timestamp));
    }

    private WeatherService(){}

    private static int toCelsius(double tempF){
        return (int)((tempF-32)/1.8);
    }

    private static String formatDate(Timestamp timestamp){
        return String.format(Locale.ENGLISH,"%04d-%02d-%02d",
                timestamp.getYear(), timestamp.getMonth().getValue(),timestamp.getDay());

    }
}
