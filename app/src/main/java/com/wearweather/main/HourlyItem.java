package com.wearweather.main;

public class HourlyItem {
    private String Days;
    private int Weather_photo;
    private int Temp_hourly;

    public HourlyItem() {
    }

    public HourlyItem(String days, int weather_photo, int temp_hourly) {
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

    public int getTemp_hourly() {
        return Temp_hourly;
    }

    //setter

    public void setDays(String days) {
        Days = days;
    }

    public void setWeather_photo(int weather_photo) {
        Weather_photo = weather_photo;
    }

    public void setTemp_hourly(int temp_hourly) {
        Temp_hourly = temp_hourly;
    }
}
