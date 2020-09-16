package com.wearweatherapp.ui.main;

import java.util.ArrayList;

public class WeatherFragmentList {
    public static ArrayList<MainWeatherFragment> list;
    public static ArrayList<String> cities;

    private WeatherFragmentList(){

    }

    public static ArrayList<MainWeatherFragment> getFragmentInstance() {
        if(list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    public static void add() {
        list.add(new MainWeatherFragment());
        list.get(list.size()-1).setTabPosition(list.size()-1);
    }
}
