package com.github.versus.weather;

import java.util.List;

public class WeatherResponse {

    private String description;
    private List<DayWeather> days;

    public String getDescription() {
        return description;
    }
    public List<DayWeather> getDays() {
        return days;
    }
}
