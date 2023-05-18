package com.github.versus.weather;

import java.util.Map;

public enum Weather {
    rainy, snowy, cloudy, weather_unavailable, clear_day, clear_night, foggy, windy, cloudy_day, cloudy_night;
    public static Weather extractWeather(Map<String,String> forecast){
        try{
            String icon = forecast.get("icon");
            switch (icon) {
                case "rain":
                    return rainy;
                case "clear-day":
                    return clear_day;
                case "clear-night":
                    return clear_night;
                case "snow":
                    return snowy;
                case "fog":
                    return foggy;
                case "wind":
                    return windy;
                case "cloudy":
                    return cloudy;
                case "partly-cloudy-day":
                    return cloudy_day;
                case "partly-cloudy-night":
                    return cloudy_night;
                default:
                    throw  new EnumConstantNotPresentException(Weather.class,icon);
            }

        }
        catch(Exception e){
            return weather_unavailable;
        }
    }
}
