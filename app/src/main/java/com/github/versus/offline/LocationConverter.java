package com.github.versus.offline;

import com.github.versus.posts.Location;

import java.util.Locale;

public class LocationConverter {
    public static String convertLocation(Location location){
        return  String.format(Locale.ENGLISH,"%s (%.2f, %.2f)", location.getName()
                , location.getLatitude(), location.getLongitude());

    }
    public static Location convertBackLocation(String locationStr){
        int openIndex = locationStr.indexOf("(");
        int closeIndex = locationStr.indexOf(")");
        String name = locationStr.substring(0, openIndex - 1);
        String coordinates = locationStr.substring(openIndex + 1, closeIndex);
        String[] values = coordinates.split(", ");
        double latitude = Double.parseDouble(values[0]);
        double longitude = Double.parseDouble(values[1]);
        return new Location(name,latitude,longitude);
    }
    public static Location GameFragmentLocation(String locationString){
        // Remove any whitespace from the string
        String cleanedString = locationString.replaceAll("\\s+", "");

        // Extract the name and coordinates from the string
        int nameEndIndex = cleanedString.indexOf("(");
        String name = cleanedString.substring(0, nameEndIndex);
        int coordinatesStartIndex = nameEndIndex + 1;
        int coordinatesEndIndex = cleanedString.indexOf(")");
        String coordinatesString = cleanedString.substring(coordinatesStartIndex, coordinatesEndIndex);
        String[] coordinatesArray = coordinatesString.split(",");
        double latitude = Double.parseDouble(coordinatesArray[0]);
        double longitude = Double.parseDouble(coordinatesArray[1]);

        // Create and return a new Location object
        return new Location(name, latitude, longitude);
    }
}
