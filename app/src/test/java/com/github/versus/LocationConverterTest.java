package com.github.versus;

import static org.junit.Assert.assertTrue;

import com.github.versus.offline.LocationConverter;
import com.github.versus.posts.Location;

import org.junit.Test;

public class LocationConverterTest {
    @Test
    public void convertLocationWorks(){
        Location location= new Location("Test Class",0,1);
        assertTrue(LocationConverter.convertLocation(location).equals("Test Class (0.00, 1.00)"));
    }
     @Test
    public void convertBackLocationWorks(){
        Location location= new Location("Test Class",0,1);
        assertTrue(LocationConverter.convertBackLocation("Test Class (0.00, 1.00)")
                .equals(location));
     }

     @Test
    public void convertAndConvertBackAreInverses(){
         Location location= new Location("Test Class",0,1);
         assertTrue(LocationConverter
                 .convertBackLocation(LocationConverter.convertLocation(location))
                 .equals(location));
     }
     @Test
    public void GameFragmentLocationWorks(){
        String locationString ="CHAVANNES (200.0, 331.0)";
        Location location= new Location("CHAVANNES", 200.0,331.0);
        Location result= LocationConverter.GameFragmentLocation(locationString);
        assertTrue(result.equals(location));

     }
}
