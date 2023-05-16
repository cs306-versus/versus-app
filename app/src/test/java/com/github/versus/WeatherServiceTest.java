package com.github.versus;

import com.github.versus.posts.Location;
import com.github.versus.posts.Timestamp;
import com.github.versus.weather.WeatherService;

import org.junit.Test;

import java.time.Month;
import java.util.concurrent.ExecutionException;

public class WeatherServiceTest {

    @Test
    public void ConnectionIsNotInterrupted(){
        Location location= new Location("Lausanne",46.519962,6.633597);
        Timestamp timestamp= new Timestamp(2023, Month.MAY,16,3,0,   Timestamp.Meridiem.AM);
        System.out.println("#####################################");
        WeatherService.getWeather(location,timestamp)
                .forEach((key, value) -> System.out.println(key + ":" + value));;
        System.out.println("#####################################");


    }
    @Test
    public void AsynchronousYieldsSameResult() throws ExecutionException, InterruptedException {
        Location location= new Location("Lausanne",46.519962,6.633597);
        Timestamp timestamp= new Timestamp(2023, Month.MAY,16,3,0,   Timestamp.Meridiem.AM);
        WeatherService.getWeather(location,timestamp)
                        .equals(WeatherService.getWeatherAsynchronously(location,timestamp).get());


    }
}
