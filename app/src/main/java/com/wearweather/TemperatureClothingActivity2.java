package com.wearweather;
/* 5°C ~ 9°C 이하 일 경우 추천해주는 액티비티 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;

public class TemperatureClothingActivity2 extends AppCompatActivity {
    TextView tx9;
    Button btnToHome;
    ViewGroup tokboki, shoes, thickCoat, neat, scarf, leather;
    String url;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_clothing2);

        btnToHome = (Button) findViewById(R.id.btnToHome);
        btnToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tx9 = (TextView)findViewById(R.id.textView9);
        intent = getIntent(); /*데이터 수신*/

        String name = intent.getStringExtra("temperature");

        tx9.setText(name);

        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.layout_tokboki:
                        url = "https://store.musinsa.com/app/product/detail/1196926/0";
                        intent = new Intent(getApplicationContext(), ClothesClickWebView.class);
                        intent.putExtra("link", url);
                        startActivity(intent);
                    case R.id.layout_winterShoes:
                        url = "https://store.musinsa.com/app/product/detail/1269289/0";
                        intent = new Intent(getApplicationContext(), ClothesClickWebView.class);
                        intent.putExtra("link", url);
                        startActivity(intent);
                    case R.id.layout_thickCoat:
                        url = "https://store.musinsa.com/app/product/detail/1180105/0";
                        intent = new Intent(getApplicationContext(), ClothesClickWebView.class);
                        intent.putExtra("link", url);
                        startActivity(intent);
                    case R.id.layout_neat:
                        url = "https://store.musinsa.com/app/product/detail/1435001/0";
                        intent = new Intent(getApplicationContext(), ClothesClickWebView.class);
                        intent.putExtra("link", url);
                        startActivity(intent);
                    case R.id.layout_scarf:
                        url = "https://store.musinsa.com/app/product/detail/897557/0";
                        intent = new Intent(getApplicationContext(), ClothesClickWebView.class);
                        intent.putExtra("link", url);
                        startActivity(intent);
                    case R.id.layout_leather:
                        url = "https://store.musinsa.com/app/product/detail/475009/0";
                        intent = new Intent(getApplicationContext(), ClothesClickWebView.class);
                        intent.putExtra("link", url);
                        startActivity(intent);
                }
            }
        };
        tokboki = (ViewGroup) findViewById(R.id.layout_tokboki);
        shoes = (ViewGroup) findViewById(R.id.layout_winterShoes);
        thickCoat = (ViewGroup) findViewById(R.id.layout_thickCoat);
        neat = (ViewGroup) findViewById(R.id.layout_neat);
        scarf = (ViewGroup) findViewById(R.id.layout_scarf);
        leather = (ViewGroup) findViewById(R.id.layout_leather);

        tokboki.setOnClickListener(listener);
        shoes.setOnClickListener(listener);
        thickCoat.setOnClickListener(listener);
        neat.setOnClickListener(listener);
        scarf.setOnClickListener(listener);
        leather.setOnClickListener(listener);
    }
}