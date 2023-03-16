package com.github.versus.posts;
import java.io.Serializable;

public class Location implements Serializable {

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
    private Location(){
        this.name = null;
        this.latitude = 0;
        this.longitude = 0;
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
    @Override
    public String toString(){
        return name + " " + "(" +longitude+","+ latitude + ")";
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Location)) {
            return false;
        }
        Location other = (Location) obj;
        return this.name.equals(other.name)
                && Double.compare(this.latitude, other.latitude) == 0
                && Double.compare(this.longitude, other.longitude) == 0;
    }



}
