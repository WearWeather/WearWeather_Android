package com.wearweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainWeatherActivity extends AppCompatActivity {


    private TextView dateNow;
    private ConstraintLayout mainLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_weather);

        /* set Background */
        mainLayout = (ConstraintLayout)findViewById(R.id.main_weather_background);
        dateNow = (TextView)findViewById(R.id.temp_time);
        setBackgroundByTime();

        // pull to refresh
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /* 새로고침 시 수행될 코드 */
                setBackgroundByTime();

                /* 새로고침 완료 */
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void setBackgroundByTime() {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfhour = new SimpleDateFormat("HH");
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String hourText = sdfhour.format(date);
        String nowText = sdfNow.format(date);

        dateNow.setText(nowText);

        int time = Integer.parseInt(hourText);
        if(time >= 0 && time < 12){
            mainLayout.setBackgroundResource(R.drawable.sunny_morning_background);
        }
        else {
            mainLayout.setBackgroundResource(R.drawable.sunny_afternoon_background);
        }
    }
}