package com.github.versus.user;


import com.google.android.gms.maps.model.LatLng;

public class CustomPlace {
    public String name;
    public String address;
    public LatLng latLng;

    public CustomPlace(String name, String address, LatLng latLng) {
        this.name = name;
        this.address = address;
        this.latLng = latLng;
    }
}

