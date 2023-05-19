package com.github.versus.weather;

public class HourWeather {
    private String datetime;
    private String conditions;
    private double temp;
    private double feelslike;
    private double humidity;
    private double cloudcover;
    private double precip;
    private double precipprob;
    private double snow;
    private double snowdepth;
    private double windgust;
    private double windspeed;
    private double winddir ;
    private double pressure;
    private double visibility;
    private double solarradiation;
    private double uvindex;
    private double severerisk;

    private String icon;

    public String getDatetime() {
        return datetime;
    }

    public double getTemp() {
        return temp;
    }

    public String getConditions(){
        return conditions;
    }

    public double getFeelslike() {
        return feelslike;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getCloudcover() {
        return cloudcover;
    }

    public double getPrecip() {
        return precip;
    }

    public double getPrecipprob() {
        return precipprob;
    }

    public double getSnow() {
        return snow;
    }
    public double getSnowdepth() {
        return snowdepth;
    }

    public double getPressure() {
        return pressure;
    }

    public double getVisibility() {
        return visibility;
    }

    public double getWindgust() {
        return windgust;
    }

    public double getWindspeed() {
        return windspeed;
    }
    public double getWinddir() {
        return winddir;
    }

    public double getSevererisk() {
        return severerisk;
    }

    public double getSolarradiation() {
        return solarradiation;
    }

    public double getUvindex() {
        return uvindex;
    }

    public String getIcon(){return icon;}




}