package com.github.versus;

import com.github.versus.posts.Location;
import com.github.versus.posts.Timestamp;
import com.github.versus.weather.WeatherService;

import org.junit.Assert;
import org.junit.Test;

import java.time.Month;
import java.util.concurrent.ExecutionException;

public class WeatherServiceTest {
    @Test
    public void AsynchronousYieldsSameResult() throws ExecutionException, InterruptedException {
        Location location= new Location("Lausanne",46.519962,6.633597);
        Timestamp timestamp= new Timestamp(2023, Month.MAY,16,3,0,   Timestamp.Meridiem.AM);
        WeatherService.getWeather(location,timestamp)
                        .equals(WeatherService.getWeatherAsynchronously(location,timestamp).get());


    }

    @Test
    public void correctDateIsfetched(){
        Location location= new Location("Lausanne",46.519962,6.633597);
        Timestamp timestamp= new Timestamp(2023, Month.MAY,16,3,0,   Timestamp.Meridiem.AM);
        Assert.assertTrue(WeatherService.getWeather(location,timestamp).get("day").equals("2023-05-16"));
    }

    @Test
    public void correctTimeIsfetched(){
        Location location= new Location("Lausanne",46.519962,6.633597);
        Timestamp timestamp= new Timestamp(2023, Month.MAY,16,3,17,   Timestamp.Meridiem.PM);
        Assert.assertTrue(WeatherService.getWeather(location,timestamp).get("time").equals("15:00:00"));
    }

    @Test
    public void fetchingPastDaysIsPossible(){
        Location location= new Location("Lausanne",46.519962,6.633597);
        Timestamp timestamp= new Timestamp(2022, Month.MAY,3,6,21,   Timestamp.Meridiem.PM);
        java.util.Map<String, String> fetched= WeatherService.getWeather(location,timestamp);
        Assert.assertTrue(fetched.get("day").equals("2022-05-03")
                                   && fetched.get("time").equals("18:00:00"));
    }
}
