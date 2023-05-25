package com.github.versus;

import static org.junit.Assert.assertEquals;

import com.github.versus.posts.Location;
import com.github.versus.posts.Timestamp;
import com.github.versus.weather.Weather;
import com.github.versus.weather.WeatherService;

import org.junit.Assert;

import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public final class WeatherServiceTest {
    private static final Location location = new Location("Lausanne", 46.519962, 6.633597);
    private static final Timestamp timestamp = new Timestamp(2023, Month.MAY, 20, 3, 17, Timestamp.Meridiem.PM);
    private static final Map<String, String> weather_map = WeatherService.getWeather(location, timestamp);

    //@Test
    public void asynchronousYieldsSameResult() throws ExecutionException, InterruptedException {
        assertEquals("2023-05-20", WeatherService.getWeatherAsynchronously(location, timestamp).get().get("day"));
    }

    //@Test
    public void correctDateIsfetched() {
        assertEquals("2023-05-20", weather_map.get("day"));
    }

    //@Test
    public void correctTimeIsFetched() {
        assertEquals("15:00:00", WeatherService.getWeather(location, timestamp).get("time"));
    }

    //@Test
    public void fetchingPastDaysIsPossible() {
        Location location = new Location("Lausanne", 46.519962, 6.633597);
        Timestamp timestamp = new Timestamp(2022, Month.MAY, 3, 6, 21, Timestamp.Meridiem.PM);
        java.util.Map<String, String> fetched = WeatherService.getWeather(location, timestamp);
        Assert.assertTrue(fetched.get("day").equals("2022-05-03")
                && fetched.get("time").equals("18:00:00"));
    }

    //@Test
    public void weatherReturnUnavailableWhenIconIsWrong() {
        Assert.assertSame(Weather.extractWeather(new HashMap<>()), Weather.weather_unavailable);
    }

}