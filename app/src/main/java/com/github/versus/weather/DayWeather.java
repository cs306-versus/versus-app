package com.github.versus.weather;

import java.util.List;

public class DayWeather {
    private String datetime;
    private String description;
    private List<HourWeather> hours;
    private double tempmax;
    private double tempmin;


    public String getDatetime() {
        return datetime;
    }
    public String getDescription(){
        return description;
    }
    public List<HourWeather> getHours(){
        return hours;
    }

    public double getTempmax() {
        return tempmax;
    }

    public double getTempmin() {
        return tempmin;
    }
}
