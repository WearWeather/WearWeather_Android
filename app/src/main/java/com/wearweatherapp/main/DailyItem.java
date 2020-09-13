package com.wearweatherapp.main;

public class DailyItem {
    private String Days;
    private int Weather_photo;
    private String Low_temp;
    private String High_temp;

    public DailyItem() {
    }

    public DailyItem(String days, String low_temp, String high_temp, int weather_photo) {
        Days = days;
        Weather_photo = weather_photo;
        Low_temp = low_temp;
        High_temp = high_temp;
    }
    //getter

    public String getDays() {
        return Days;
    }

    public int getWeather_photo() {
        return Weather_photo;
    }

    public String getLow_temp() {
        return Low_temp;
    }

    public String getHigh_temp() {
        return High_temp;
    }

    //Setter

    public void setDays(String days) {
        Days = days;
    }

    public void setWeather_photo(int weather_photo) {
        Weather_photo= weather_photo;
    }

    public void setLow_temp(String low_temp) {
        Low_temp = low_temp;
    }

    public void setHigh_temp(String high_temp) {
        High_temp = high_temp;
    }
}
