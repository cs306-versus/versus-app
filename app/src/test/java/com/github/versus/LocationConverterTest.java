package com.github.versus;

import static org.junit.Assert.assertEquals;

import com.github.versus.offline.LocationConverter;
import com.github.versus.posts.Location;

import org.junit.Test;

public final class LocationConverterTest {
    @Test
    public void convertLocationWorks() {
        Location location = new Location("Test Class", 0, 1);
        assertEquals("Test Class (0.00, 1.00)", LocationConverter.convertLocation(location));
    }

    @Test
    public void convertBackLocationWorks() {
        Location location = new Location("Test Class", 0, 1);
        assertEquals(LocationConverter.convertBackLocation("Test Class (0.00, 1.00)"), location);
    }

    @Test
    public void convertAndConvertBackAreInverses() {
        Location location = new Location("Test Class", 0, 1);
        assertEquals(LocationConverter.convertBackLocation(LocationConverter.convertLocation(location)), location);
    }

    @Test
    public void GameFragmentLocationWorks() {
        String locationString = "CHAVANNES (45.6, 4.4)";
        Location location = new Location("CHAVANNES", 45.6, 4.4);
        Location result = LocationConverter.GameFragmentLocation(locationString);
        assertEquals(result, location);
    }

}
