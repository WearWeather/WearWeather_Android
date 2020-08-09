package com.wearweather.mainTabFragment;

public class hourly_connecting_items {
    private String Days;
    private int Weather_photo;
    private int Low_temp;
    private int High_temp;

    public hourly_connecting_items() {
    }

    public hourly_connecting_items(String days, int weather_photo, int low_temp, int high_temp) {
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

    public int getLow_temp() {
        return Low_temp;
    }

    public int getHigh_temp() {
        return High_temp;
    }

    //Setter

    public void setDays(String days) {
        Days = days;
    }

    public void setWeather_photo(int weather_photo) {
        Weather_photo= weather_photo;
    }

    public void setLow_temp(int low_temp) {
        Low_temp = low_temp;
    }

    public void setHigh_temp(int high_temp) {
        High_temp = high_temp;
    }
}
