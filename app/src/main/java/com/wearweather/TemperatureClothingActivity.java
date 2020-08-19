package com.wearweather;
/* 4°C 이하 일 경우 추천해주는 액티비티 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class TemperatureClothingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_clothing);

        //TextView tx9 = (TextView)findViewById(R.id.textView9);
        //Intent intent = getIntent(); /*데이터 수신*/

        //String name = intent.getStringExtra("temperature");

        //tx9.setText(name);

        LinearLayout layout1 = (LinearLayout) findViewById(R.id.layout1);
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://store.musinsa.com/app/product/detail/1515905/0");
                startActivity(intent);
            }
        });

        LinearLayout layout2 = (LinearLayout) findViewById(R.id.layout2);
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://store.musinsa.com/app/product/detail/1139473/0");
                startActivity(intent);
            }
        });

        LinearLayout layout3 = (LinearLayout) findViewById(R.id.layout3);
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://store.musinsa.com/app/product/detail/1180105/0");
                startActivity(intent);
            }
        });

        LinearLayout layout4 = (LinearLayout) findViewById(R.id.layout4);
        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://store.musinsa.com/app/product/detail/1435001/0");
                startActivity(intent);
            }
        });

        LinearLayout layout5 = (LinearLayout) findViewById(R.id.layout5);
        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://store.musinsa.com/app/product/detail/1269289/0");
                startActivity(intent);
            }
        });

        LinearLayout layout6 = (LinearLayout) findViewById(R.id.layout6);
        layout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://store.musinsa.com/app/product/detail/897557/0");
                startActivity(intent);
            }
        });


    }
}