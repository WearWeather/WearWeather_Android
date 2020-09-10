package com.wearweather;
/* 28°C 이상 일 경우 추천해주는 액티비티 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wearweather.main.MainActivity;
import com.wearweather.main.MainWeatherFragment;

public class TemperatureClothingActivity8 extends AppCompatActivity {
    TextView tx9;
    ImageView btnToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_clothing8);

        btnToHome = (ImageView) findViewById(R.id.btnToHome);
        btnToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        LinearLayout layout1 = (LinearLayout) findViewById(R.id.layout1);
        tx9 = (TextView)findViewById(R.id.textView9);
        Intent intent = getIntent(); /* 데이터 수신 */

        String name = intent.getStringExtra("temperature");

        tx9.setText(name + "°C");

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://meosidda.com/product/%EC%BF%A8-%EC%98%A4%ED%94%88-%EC%B9%B4%EB%9D%BC-%EB%B0%98%ED%8C%94-%EC%85%94%EC%B8%A0/37124/category/527/display/1/");
                startActivity(intent);
            }
        });

        LinearLayout layout2 = (LinearLayout) findViewById(R.id.layout2);
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://meosidda.com/product/%EC%A3%BC%EB%A6%84%EC%9B%8C%EC%8B%B1-%EB%B0%98%EB%B0%B4%EB%94%A9-%EC%BF%A8-%EB%B0%98%EB%B0%94%EC%A7%80/37168/category/581/display/1/");
                startActivity(intent);
            }
        });

        LinearLayout layout3 = (LinearLayout) findViewById(R.id.layout3);
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://meosidda.com/product/3cm-%EC%8A%A4%ED%83%80%EC%9D%BC%EB%A6%AC%EC%8B%9C%ED%95%9C-%EC%8A%A4%EC%9B%A8%EC%9D%B4%EB%93%9C-%EC%8A%AC%EB%A6%AC%ED%8D%BC/31835/category/31/display/1/");
                startActivity(intent);
            }
        });

        LinearLayout layout4 = (LinearLayout) findViewById(R.id.layout4);
        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "http://lookatmin.com/product/%ED%94%84%EB%A0%8C%EC%A6%88-%ED%94%84%EB%A6%B4-%EA%B3%A8%EC%A7%80-crop-cd-6color/1656/category/43/display/1/");
                startActivity(intent);
            }
        });

        LinearLayout layout5 = (LinearLayout) findViewById(R.id.layout5);
        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "http://lookatmin.com/product/%EB%A6%B0%EB%84%A8-high-sk-%EC%B9%98%EB%A7%88%EB%B0%94%EC%A7%80-3-color/1705/category/45/display/1/");
                startActivity(intent);
            }
        });

        LinearLayout layout6 = (LinearLayout) findViewById(R.id.layout6);
        layout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "http://lookatmin.com/product/%EC%A7%80%EB%B8%8C%EB%9D%BC-%EB%81%88-ops/1641/category/44/display/1/");
                startActivity(intent);
            }
        });
    }
}