package com.wearweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.tabs.TabLayout;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.android.volley.Request.*;
import static com.android.volley.Request.Method.*;


public class MainWeatherActivity extends AppCompatActivity {
    private TextView dateNow;

//    private TextView description; //날씨 설명(ex: 맑음, 흐림)
    private SwipeRefreshLayout swipeRefreshLayout;
    private DrawerLayout drawerLayout;
    private ImageButton menuButton;
    private ImageButton searchButton;
    private TabLayout tabLayout;
    private WeatherPagerAdpater pagerAdpater;
    private ViewPager viewPager;
    private ImageButton delButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_weather);

        /* set Background */
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_layout);
        dateNow = (TextView)findViewById(R.id.temp_time);
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
        drawerLayout = (DrawerLayout)findViewById(R.id.main_drawer_layout);
        menuButton = (ImageButton)findViewById(R.id.main_menu_btn);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!drawerLayout.isDrawerOpen(Gravity.RIGHT)){
                    drawerLayout.openDrawer(Gravity.RIGHT);
                    Toast toast = Toast.makeText(getApplicationContext(),"menu button clicked", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }
            }
        });

        /* tab layout */
        tabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        pagerAdpater = new WeatherPagerAdpater(getSupportFragmentManager(),tabLayout.getTabCount());
        pagerAdpater.initFragment();
        for(int i=0;i<pagerAdpater.getWeatherFragmentSize();i++){
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
                Log.e("SEULGI TAB SELECTED",String.valueOf(tab.getPosition()));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        searchButton = (ImageButton)findViewById(R.id.main_search_btn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(),"search button clicked", Toast.LENGTH_SHORT);
                toast.show();

                /* 검색버튼 누르면 프래그먼트 추가 */
                pagerAdpater.addWeatherFragment();
                tabLayout.addTab(tabLayout.newTab());
                //pagerAdpater.notifyDataSetChanged();
            }
        });

        delButton = (ImageButton)findViewById(R.id.main_del_btn);
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(),"delete button clicked", Toast.LENGTH_SHORT);
                toast.show();

                /* 삭제버튼 누르면 프래그먼트 삭제 */
                Log.e("SEULGI DELETE TAB",String.valueOf(tabLayout.getSelectedTabPosition()));
                pagerAdpater.deleteWeatherFragment(tabLayout.getSelectedTabPosition());
                tabLayout.removeTabAt(tabLayout.getSelectedTabPosition());
                //pagerAdpater.notifyDataSetChanged();
            }
        });
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