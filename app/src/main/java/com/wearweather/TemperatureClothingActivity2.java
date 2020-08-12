package com.wearweather;
/* 5°C ~ 9°C 이하 일 경우 추천해주는 액티비티 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.net.MalformedURLException;
import java.net.URL;

public class TemperatureClothingActivity2 extends AppCompatActivity {

    ViewGroup tokboki, shoes, thickCoat, neat, scarf, leather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_clothing2);

        tokboki = (ViewGroup) findViewById(R.id.layout_tokboki);

        tokboki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "http://store.musinsa.com/app/product/detail/1196926/0";

                Intent intent = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("LinkTokboki", url);
                startActivity(intent);
            }
        });
    }
}