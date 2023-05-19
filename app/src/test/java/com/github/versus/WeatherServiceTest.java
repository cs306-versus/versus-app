

/*package com.github.versus;

import com.github.versus.posts.Location;
import com.github.versus.posts.Timestamp;
import com.github.versus.weather.Weather;
import com.github.versus.weather.WeatherService;

import org.junit.Assert;
import org.junit.Test;

import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class WeatherServiceTest {
    private static final Location location= new Location("Lausanne",46.519962,6.633597);
    private static final Timestamp timestamp= new Timestamp(2023, Month.MAY,20,3,17,   Timestamp.Meridiem.PM);
    private static final Map<String,String> weather_map =WeatherService.getWeather(location,timestamp);
    @Test
    public void AsynchronousYieldsSameResult() throws ExecutionException, InterruptedException {

        Assert.assertTrue(WeatherService.getWeatherAsynchronously(location, timestamp).get().get("day").equals("2023-05-20"));
    }

    @Test
    public void correctDateIsfetched(){
        Assert.assertTrue(weather_map.get("day").equals("2023-05-20"));
    }

    @Test
    public void correctTimeIsFetched(){
        Assert.assertEquals("15:00:00", WeatherService.getWeather(location, timestamp).get("time"));
    }

    @Test
    public void fetchingPastDaysIsPossible(){
        Location location= new Location("Lausanne",46.519962,6.633597);
        Timestamp timestamp= new Timestamp(2022, Month.MAY,3,6,21,   Timestamp.Meridiem.PM);
        java.util.Map<String, String> fetched= WeatherService.getWeather(location,timestamp);
        Assert.assertTrue(fetched.get("day").equals("2022-05-03")
                && fetched.get("time").equals("18:00:00"));
    }

    @Test
    public void weatherReturnUnavailableWhenIconIsWrong(){
        Assert.assertTrue(Weather.extractWeather(new HashMap<>())==Weather.weather_unavailable);
    }

}
*/