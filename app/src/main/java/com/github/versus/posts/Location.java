package com.github.versus.posts;


import androidx.annotation.Nullable;

public class Location {
    private String name;
    private double longitude;
    private double latitude;

    /**
     * Create a location
     *
     * @param name      the name of the location
     * @param latitude  latitude of the location
     * @param longitude longitude of the location
     */
    public Location(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * @return String representation of the location
     */
    public String toString() {
        return name + " (" + latitude + ", " + longitude + ")";
    }

    public String getName() {
        return name;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }

    public boolean equals(Location that) {
        return name.equals(that.getName()) &&
                latitude== that.getLatitude() &&
                longitude == that.getLongitude();
    }
}
