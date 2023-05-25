package com.github.versus;

import static org.junit.Assert.assertEquals;

import com.github.versus.posts.Location;
import com.github.versus.posts.Timestamp;
import com.github.versus.weather.Weather;
import com.github.versus.weather.WeatherService;

import org.junit.Assert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class WeatherServiceTest {
    private static final Location location= new Location("Lausanne",46.519962,6.633597);
    private static final Timestamp timestamp= getTomorrowTimestamp();
    private static final Map<String,String> weather_map =WeatherService.getWeather(location,timestamp);
    @Test
    public void AsynchronousYieldsSameResult() throws ExecutionException, InterruptedException {
        Assert.assertEquals(getTomorrowStringFormat(), WeatherService.getWeatherAsynchronously(location, timestamp).get().get("day"));
    }

    @Test
    public void correctDateIsFetched(){
        Assert.assertEquals(getTomorrowStringFormat(), weather_map.get("day"));
    }

    @Test
    public void correctTimeIsFetched(){
        Assert.assertEquals("15:00:00", WeatherService.getWeather(location, timestamp).get("time"));
    }


    @Test
    public void weatherReturnUnavailableWhenIconNull(){
        Assert.assertSame(Weather.weather_unavailable,Weather.extractWeather(Map.of()));
    }

    @Test
    public void weatherReturnRainyIconWhenNeeded(){
        Assert.assertSame(Weather.rainy,Weather.extractWeather(Map.of("icon","rain")));
    }

    @Test
    public void weatherReturnClearDayIconWhenNeeded(){
        Assert.assertSame(Weather.clear_day,Weather.extractWeather(Map.of("icon","clear-day")));
    }

    @Test
    public void weatherReturnClearNighIconWhenNeeded(){
        Assert.assertSame(Weather.clear_night,Weather.extractWeather(Map.of("icon","clear-night")));
    }

    @Test
    public void weatherReturnSnowyIconWhenNeeded(){
        Assert.assertSame(Weather.snowy,Weather.extractWeather(Map.of("icon","snow")));
    }

    @Test
    public void weatherReturnFoggyIconWhenNeeded(){
        Assert.assertSame(Weather.foggy,Weather.extractWeather(Map.of("icon","fog")));
    }

    @Test
    public void weatherReturnWindyIconWhenNeeded(){
        Assert.assertSame(Weather.windy,Weather.extractWeather(Map.of("icon","wind")));
    }

    @Test
    public void weatherReturnCloudyIconWhenNeeded(){
        Assert.assertSame(Weather.cloudy,Weather.extractWeather(Map.of("icon","cloudy")));
    }

    @Test
    public void weatherReturnCloudyDayIconWhenNeeded(){
        Assert.assertSame(Weather.cloudy_day,Weather.extractWeather(Map.of("icon","partly-cloudy-day")));
    }

    @Test
    public void weatherReturnCloudyNightIconWhenNeeded(){
        Assert.assertSame(Weather.cloudy_night,Weather.extractWeather(Map.of("icon","partly-cloudy-night")));
    }

    @Test
    public void weatherReturnUnavailableWhenIconWrong(){
        Assert.assertSame(Weather.weather_unavailable,Weather.extractWeather(Map.of("icon","sand-tempest")));
    }



    private static String getTomorrowStringFormat(){
        LocalDate today = LocalDate.now();
        LocalDate tomorrow = today.plusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return tomorrow.format(formatter);
    }


    private static Timestamp getTomorrowTimestamp() {
        LocalDate currentDate = LocalDate.now();
        LocalDate tomorrowDate = currentDate.plusDays(1);
        return new Timestamp(tomorrowDate.getYear(),
                tomorrowDate.getMonth(),tomorrowDate.getDayOfMonth(),3,17
                , Timestamp.Meridiem.PM);

    }

}