package com.github.versus.weather;

import java.util.List;

public class DayWeather {
    private String datetime;
    private String description;
    private List<HourWeather> hours;


    public String getDatetime() {
        return datetime;
    }
    public String getDescription(){
        return description;
    }
    public List<HourWeather> getHours(){
        return hours;
    }
}
