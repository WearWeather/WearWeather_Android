package com.wearweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainWeatherActivity extends AppCompatActivity {
    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat sdfNow = new SimpleDateFormat("HH");
    String timeText = sdfNow.format(date);

    private TextView dateNow;
    private ConstraintLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_weather);

        mainLayout = (ConstraintLayout)findViewById(R.id.main_weather_background);
        dateNow = (TextView)findViewById(R.id.temp_time);
        dateNow.setText(timeText);

        int time = Integer.parseInt(timeText);
        if(time >= 0 && time < 12){
            mainLayout.setBackgroundResource(R.drawable.sunny_morning_background);
        }
        else {
            mainLayout.setBackgroundResource(R.drawable.sunny_afternoon_background);

        }
    }
}