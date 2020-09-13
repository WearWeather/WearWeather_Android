package com.wearweatherapp;
/* 24°C ~ 27°C 이하 일 경우 추천해주는 액티비티 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TemperatureClothingActivity7 extends AppCompatActivity {
    TextView tx9;
    ImageView btnToHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_clothing7);

        btnToHome = (ImageView) findViewById(R.id.btnToHome);
        btnToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tx9 = (TextView)findViewById(R.id.textView9);
        Intent intent = getIntent(); /*데이터 수신*/

        String name = intent.getStringExtra("temperature");

        tx9.setText(name + "°C");

        LinearLayout layout1 = (LinearLayout) findViewById(R.id.layout1);
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://meosidda.com/product/%EC%BA%90%EC%A5%AC%EC%96%BC%ED%95%9C-%EC%98%A4%EB%B2%84%ED%95%8F-%ED%8F%AC%EC%BC%93%EB%B0%98%ED%8C%94%ED%8B%B0/28632/category/437/display/2/");
                startActivity(intent);
            }
        });

        LinearLayout layout2 = (LinearLayout) findViewById(R.id.layout2);
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://meosidda.com/product/%EB%A6%B0%EB%84%A8-%ED%8C%8C%EC%9E%90%EB%A7%88-%EB%B0%98%ED%8C%94-%EC%85%94%EC%B8%A0/37132/category/97/display/1/");
                startActivity(intent);
            }
        });

        LinearLayout layout3 = (LinearLayout) findViewById(R.id.layout3);
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "http://lookatmin.com/product/made-baguette-check-ops/1741/category/44/display/1/");
                startActivity(intent);
            }
        });

        LinearLayout layout4 = (LinearLayout) findViewById(R.id.layout4);
        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://meosidda.com/product/%EC%97%AC%EB%A6%84%EB%82%B4%EB%82%B4-%EC%8B%9C%EC%9B%90%ED%95%9C-%EB%A6%B0%EB%84%A8-%EB%B0%B4%EB%94%A9%EB%B0%98%EB%B0%94%EC%A7%80/32590/category/581/display/1/");
                startActivity(intent);
            }
        });

        LinearLayout layout5 = (LinearLayout) findViewById(R.id.layout5);
        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://meosidda.com/product/%EC%AB%80%EC%AB%80-%EC%8B%A0%EC%B6%95%EC%84%B1-%EB%B0%B4%EB%94%A9-%EB%A9%B4%EB%B0%94%EC%A7%80/32927/category/581/display/1/");
                startActivity(intent);
            }
        });

        LinearLayout layout6 = (LinearLayout) findViewById(R.id.layout6);
        layout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://meosidda.com/product/3cm-%EC%A7%80%EA%B8%88%EB%94%B1-%EC%8B%A0%EA%B8%B0-%EC%A2%8B%EC%9D%80-%EB%B0%B0%EC%83%89-%EB%B8%94%EB%A1%9C%ED%8D%BC/33306/category/86/display/1/\n");
                startActivity(intent);
            }
        });
    }
}