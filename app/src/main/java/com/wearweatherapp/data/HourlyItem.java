package com.wearweatherapp.data;

public class HourlyItem {
    private String Days;
    private int Weather_photo;
    private String Temp_hourly;

    public HourlyItem() {
    }

    public HourlyItem(String days, int weather_photo, String temp_hourly) {
        Days = days;
        Weather_photo = weather_photo;
        Temp_hourly = temp_hourly;
    }
    //getter

    public String getDays() {
        return Days;
    }

    public int getWeather_photo() {
        return Weather_photo;
    }

    public String getTemp_hourly() {
        return Temp_hourly;
    }

    //setter

    public void setDays(String days) {
        Days = days;
    }

    public void setWeather_photo(int weather_photo) {
        Weather_photo = weather_photo;
    }

    public void setTemp_hourly(String temp_hourly) {
        Temp_hourly = temp_hourly;
    }
}
