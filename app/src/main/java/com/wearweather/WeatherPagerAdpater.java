//package com.wearweather;
//
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.fragment.app.FragmentStatePagerAdapter;
//
//import java.util.ArrayList;
//
//public class WeatherPagerAdpater extends FragmentStatePagerAdapter {
//    public static ArrayList<MainWeatherFragment> weatherFragmentArrayList = new ArrayList<>();
//
//    public WeatherPagerAdpater(@NonNull FragmentManager fm, int behavior) {
//        super(fm, behavior);
//    }
//
//    public void initFragment() {
//        if(getWeatherFragmentSize()==0){
//            addWeatherFragment();
//            addWeatherFragment();
//        }
//    }
//
//    public void addWeatherFragment() {
//        weatherFragmentArrayList.add(new MainWeatherFragment());
//        Log.e("SEULGI NOW FRAGMENT",String.valueOf(weatherFragmentArrayList.size()));
//        notifyDataSetChanged();
//    }
//    public void deleteWeatherFragment(int position){
//        Log.e("SEULGI DELETE FRAGMENT",String.valueOf(position));
//        weatherFragmentArrayList.remove(position);
//        Log.e("SEULGI NOW FRAGMENT",String.valueOf(weatherFragmentArrayList.size()));
//        notifyDataSetChanged();
//    }
//    public int getWeatherFragmentSize() {
//        return weatherFragmentArrayList.size();
//    }
//    @NonNull
//    @Override
//    public Fragment getItem(int position) {
//        return weatherFragmentArrayList.get(position);
//        /*
//        switch (position){
//            case 0:
//                WeatherFragment fragment1 = new WeatherFragment();
//                return fragment1;
//            case 1:
//                WeatherFragment fragment2 = new WeatherFragment();
//                return fragment2;
//            default:
//                return null;
//        }
//        */
//    }
//
//    @Override
//    public int getCount() {
//        return getWeatherFragmentSize();
//    }
//}
