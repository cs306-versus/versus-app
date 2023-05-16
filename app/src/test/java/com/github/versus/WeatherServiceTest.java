package com.github.versus;

import com.github.versus.posts.Location;
import com.github.versus.posts.Timestamp;
import com.github.versus.weather.WeatherService;

import org.junit.Test;

import java.time.Month;

public class WeatherServiceTest {

    @Test
    public void ConnectionIsNotInterrupted(){
        Location location= new Location("Lausanne",46.519962,6.633597);
        Timestamp timestamp= new Timestamp(2023, Month.MAY,16,5,5,   Timestamp.Meridiem.AM);
        System.out.println("#####################################");
        WeatherService.getWeather(location,timestamp)
                .forEach((key, value) -> System.out.println(key + ":" + value));;
        System.out.println("#####################################");


    }
}
