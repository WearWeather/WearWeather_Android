package com.wearweather.main;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.wearweather.WeatherFragment;

import java.util.ArrayList;

public class MainTabPagerAdapter extends FragmentStatePagerAdapter {
    public static ArrayList<MainWeatherFragment> weatherFragmentArrayList = new ArrayList<>();

    public MainTabPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void initFragment(int num) {
        Log.e("SEULGI",String.valueOf(num));
        if(getWeatherFragmentSize()!=num) {
            for (int i = 0; i < num; i++)
                addWeatherFragment();
        }
    }

    public void addWeatherFragment() {
        weatherFragmentArrayList.add(new MainWeatherFragment());
        weatherFragmentArrayList.get(weatherFragmentArrayList.size()-1).setTabPosition(weatherFragmentArrayList.size()-1);
        Log.e("SEULGI ADD FRAGMENT",String.valueOf(weatherFragmentArrayList.size()));
        notifyDataSetChanged();
    }

    public void deleteWeatherFragment(int position){
        weatherFragmentArrayList.remove(position);
        Log.e("SEULGI DELETE FRAGMENT",String.valueOf(weatherFragmentArrayList.size()));
        notifyDataSetChanged();
    }

    public int getWeatherFragmentSize() { return weatherFragmentArrayList.size(); }

    public MainWeatherFragment getWeatherFragment(int position) { return weatherFragmentArrayList.get(position); }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return weatherFragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return getWeatherFragmentSize();
    }
}
