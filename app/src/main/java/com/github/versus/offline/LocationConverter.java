package com.github.versus.offline;

import com.github.versus.posts.Location;

public class LocationConverter {
    public static String convertLocation(Location location){
        return  String.format("%s (%.2f, %.2f)", location.getName()
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
}
