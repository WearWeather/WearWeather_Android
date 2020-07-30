package com.wearweather;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class WeatherPagerAdpater extends FragmentStatePagerAdapter {

    public WeatherPagerAdpater(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                WeatherFragment fragment1 = new WeatherFragment();
                return fragment1;
            case 1:
                WeatherFragment fragment2 = new WeatherFragment();
                return fragment2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
