package com.wearweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainWeatherActivity extends AppCompatActivity {
    private TextView dateNow;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DrawerLayout drawerLayout;
    private ImageButton menuButton;
    private ImageButton searchButton;

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
                //Toast toast = Toast.makeText(getApplicationContext(),"button clicked", Toast.LENGTH_SHORT);
                //toast.show();
                if(!drawerLayout.isDrawerOpen(Gravity.RIGHT)){
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
                else {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }
            }
        });

        searchButton = (ImageButton)findViewById(R.id.main_search_btn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(),"search button clicked", Toast.LENGTH_SHORT);
                toast.show();
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
        if(time >= 0 && time < 12){
            swipeRefreshLayout.setBackgroundResource(R.drawable.sunny_morning_background);
        }
        else {
            swipeRefreshLayout.setBackgroundResource(R.drawable.sunny_afternoon_background);
        }
    }
}