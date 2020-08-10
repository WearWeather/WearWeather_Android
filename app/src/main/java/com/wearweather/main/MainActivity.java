package com.wearweather.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.wearweather.PreferenceManager;
import com.wearweather.R;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private MainTabPagerAdapter pagerAdpater;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Initiate Shared Preference */
        initSharedPreference();
        /*
        Log.e("SEULGI CHECK",String.valueOf(PreferenceManager.getFloat(this,"REGION1_LAT")));
        Log.e("SEULGI CHECK",String.valueOf(PreferenceManager.getFloat(this,"REGION1_LON")));
        Log.e("SEULGI CHECK",String.valueOf(PreferenceManager.getFloat(this,"REGION2_LAT")));
        Log.e("SEULGI CHECK",String.valueOf(PreferenceManager.getFloat(this,"REGION2_LON")));*/

        /* tab layout */
        tabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        pagerAdpater = new MainTabPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());

        int region_number=PreferenceManager.getInt(this,"REGION_NUMBER");
        Log.e("SEULGI NUMBER OF REGION",String.valueOf(region_number));
        pagerAdpater.initFragment(region_number);
        for(int i=0;i<region_number;i++){
            tabLayout.addTab(tabLayout.newTab());
        }

        viewPager = (ViewPager) findViewById(R.id.main_tab_viewpager);
        viewPager.setAdapter(pagerAdpater);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pagerAdpater.notifyDataSetChanged();
                viewPager.setCurrentItem(tab.getPosition());
                //Log.e("SEULGI TAB SELECTED",String.valueOf(tab.getPosition()));
                //Log.e("SEULGI FRAG POSITION",String.valueOf(pagerAdpater.getWeatherFragment(tab.getPosition()).getTabPosition()));
                pagerAdpater.getWeatherFragment(tab.getPosition()).displayWeather(getApplicationContext());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    private void initSharedPreference(){
        if(PreferenceManager.getInt(this, "REGION_NUMBER")==-1){
            PreferenceManager.setInt(this, "REGION_NUMBER",2);
        }
        if(PreferenceManager.getFloat(this, "REGION1_LAT")==-1F){
            PreferenceManager.setFloat(this,"REGION1_LAT",(float)37.5665);
        }
        if(PreferenceManager.getFloat(this, "REGION1_LON")==-1F){
            PreferenceManager.setFloat(this,"REGION1_LON",(float)126.9780);
        }
        if(PreferenceManager.getFloat(this, "REGION2_LAT")==-1F){
            PreferenceManager.setFloat(this,"REGION2_LAT",(float)35.7988);
        }
        if(PreferenceManager.getFloat(this, "REGION2_LON")==-1F){
            PreferenceManager.setFloat(this,"REGION2_LON",(float)128.5935);
        }
    }
}