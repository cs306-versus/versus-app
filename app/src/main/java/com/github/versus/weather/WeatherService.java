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


    public static Map<String,String> getWeather(Location location, Timestamp timestamp){

        String latitude= String.valueOf(location.getLatitude());
        String longitude= String.valueOf(location.getLongitude());
        Call<WeatherResponse> call= weatherApiService
                .getWeatherTimeline(latitude,longitude, formatDate(timestamp),API_KEY);
        try {
            Response<WeatherResponse> response = call.execute();
            if (response.isSuccessful()) {
                Map<String,String> fetched_weather= new HashMap<>();
                DayWeather weather= response.body().getDays().get(0);
                fetched_weather.put("description",weather.getDescription());
                fetched_weather.put("day_time", weather.getDatetime());
                int hour_index= timestamp.getMeridiem() == Timestamp.Meridiem.AM?
                                timestamp.getHour(): timestamp.getHour()+12;
                HourWeather hour= weather.getHours().get(hour_index) ;
                fetched_weather.put("datetime",hour.getDatetime());
                fetched_weather.put("conditions", hour.getConditions());
                fetched_weather.put("temperature", String.valueOf(toCelsius(hour.getTemp())));
                fetched_weather.put("feelslike", String.valueOf(toCelsius(hour.getFeelslike())));
                fetched_weather.put("humidity", String.valueOf(hour.getHumidity()));
                fetched_weather.put("cloudcover",String.valueOf(hour.getCloudcover()));
                fetched_weather.put("precip",String.valueOf(hour.getPrecip()));
                fetched_weather.put("precipprob",String.valueOf(hour.getPrecipprob()));
                fetched_weather.put("snow",String.valueOf(hour.getSnow()));
                fetched_weather.put("snowdepth",String.valueOf(hour.getSnowdepth()));
                fetched_weather.put("pressure", String.valueOf(hour.getPressure()));
                fetched_weather.put("visibility", String.valueOf(hour.getVisibility()));
                fetched_weather.put("windgust", String.valueOf(hour.getWindgust()));
                fetched_weather.put("windspeed", String.valueOf(hour.getWindspeed()));
                fetched_weather.put("winddir",String.valueOf(hour.getWinddir()));
                fetched_weather.put("severerisk", String.valueOf(hour.getSevererisk()));
                fetched_weather.put("solarradiation",String.valueOf(hour.getSolarradiation()));
                fetched_weather.put("uvindex",String.valueOf(hour.getUvindex()));




                return fetched_weather;
                }
            else {
                Map<String,String> server_error_map= new HashMap<>();
                server_error_map.put(response.message(),String.valueOf(response.code()));
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
