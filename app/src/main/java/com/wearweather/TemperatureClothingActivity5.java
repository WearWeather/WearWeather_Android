package com.wearweather;
/* 18°C ~ 20°C 이하 일 경우 추천해주는 액티비티 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TemperatureClothingActivity5 extends AppCompatActivity {
    TextView tx9;
    Button btnToHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_clothing5);

        btnToHome = (Button) findViewById(R.id.btnToHome);
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
                intent.putExtra("link", "https://www.meosidda.com/product/%EB%A1%9C%EC%8A%A4%EC%95%A4%EC%A0%A4%EB%A1%9C%EC%8A%A4-%EC%9E%90%EC%88%98-%EB%A7%A8%ED%88%AC%EB%A7%A8/36689/category/30/display/1/");
                startActivity(intent);
            }
        });

        LinearLayout layout2 = (LinearLayout) findViewById(R.id.layout2);
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://www.meosidda.com/product/%EC%82%AC%EA%B0%81%EC%B2%B4%ED%81%AC-%EC%98%A4%EB%B2%84%ED%95%8F-%EA%B0%80%EB%94%94%EA%B1%B4/36858/category/29/display/1/");
                startActivity(intent);
            }
        });

        LinearLayout layout3 = (LinearLayout) findViewById(R.id.layout3);
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://www.meosidda.com/product/%EB%B0%91%EB%8B%A8-%ED%8A%B8%EC%9E%84-%EB%9D%BC%EC%9A%B4%EB%93%9C-%EB%8B%88%ED%8A%B8/36602/category/30/display/1/");
                startActivity(intent);
            }
        });

        LinearLayout layout4 = (LinearLayout) findViewById(R.id.layout4);
        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://www.meosidda.com/product/up-jean%EC%BB%B7%ED%8C%85%EC%9B%8C%EC%8B%B1%EC%B2%AD%EB%B0%94%EC%A7%80/36904/category/33/display/1/");
                startActivity(intent);
            }
        });

        LinearLayout layout5 = (LinearLayout) findViewById(R.id.layout5);
        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://www.meosidda.com/product/7cm-%EB%A7%88%EB%A6%AC-%EC%8A%88%EC%A6%88/27429/category/85/display/1/");
                startActivity(intent);
            }
        });

        LinearLayout layout6 = (LinearLayout) findViewById(R.id.layout6);
        layout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://www.meosidda.com/product/%ED%8A%B8%EB%A0%8C%EB%94%94-%EC%85%94%EB%A7%81-%ED%95%AD%EA%B3%B5%EC%A0%90%ED%8D%BC/33670/category/1754/display/1/");
                startActivity(intent);
            }
        });
    }
}