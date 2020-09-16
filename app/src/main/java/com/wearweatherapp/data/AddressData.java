package com.wearweatherapp.data;

public class AddressData {
    private String address;
    private double lat;
    private double lon;

    public AddressData(String address,double lat,double lon) {
        this.address=address;
        this.lat=lat;
        this.lon=lon;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getAddress() {
        return address;
    }
}