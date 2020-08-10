package com.wearweather.main;

import android.media.Image;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.wearweather.R;
import com.wearweather.WeatherPagerAdpater;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainWeatherFragment extends Fragment {

    private DrawerLayout drawerLayout;
    private ImageButton menuButton;
    private TextView dateNow;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageButton searchButton;
    private TabLayout tabLayout;
    private WeatherPagerAdpater pagerAdpater;
    private ViewPager viewPager;
    private ImageButton delButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_weather, container, false);

        /* set Background */
        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_layout);
        dateNow = (TextView)rootView.findViewById(R.id.temp_time);
        setBackgroundByTime();

        /* pull to refresh */
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /* 새로고침 시 수행될 코드 */
                setBackgroundByTime();

                /* 새로고침 완료 */
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        /* drawer layout */
        drawerLayout = (DrawerLayout)rootView.findViewById(R.id.main_drawer_layout);
        menuButton = (ImageButton)rootView.findViewById(R.id.main_menu_btn);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!drawerLayout.isDrawerOpen(Gravity.RIGHT)){
                    drawerLayout.openDrawer(Gravity.RIGHT);
                    Toast toast = Toast.makeText(getContext(),"menu button clicked", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }
            }
        });


        return rootView;
    }

    private void setBackgroundByTime() {
        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis);
        SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String hourText = sdfHour.format(date);

        String nowText = sdfNow.format(date);
        dateNow.setText(nowText);

        int time = Integer.parseInt(hourText);
        if(time >= 0 && time < 6){
            swipeRefreshLayout.setBackgroundResource(R.drawable.sunny_night_background);
        }
        else if(time >= 6 && time < 12){
            swipeRefreshLayout.setBackgroundResource(R.drawable.sunny_morning_background);
        }
        else if(time >= 12 && time < 18){
            swipeRefreshLayout.setBackgroundResource(R.drawable.sunny_afternoon_background);
        }
        else if(time >= 18 && time < 20){
            swipeRefreshLayout.setBackgroundResource(R.drawable.sunny_sunset_background);
        }
        else if(time >= 18 && time < 24){
            swipeRefreshLayout.setBackgroundResource(R.drawable.sunny_night_background);
        }
    }
}