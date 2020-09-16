package com.wearweatherapp.ui.clothing;
/* 21°C ~ 23°C 이하 일 경우 추천해주는 액티비티 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wearweatherapp.ui.news.ClothesClickWebView;
import com.wearweatherapp.R;

public class TemperatureClothingActivity6 extends AppCompatActivity {
    TextView tx9;
    ImageView btnToHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_clothing6);

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
                intent.putExtra("link", "https://www.meosidda.com/product/%EB%A0%9B%EC%B8%A0-%EC%9E%90%EC%88%98-%EC%98%A4%EB%B2%84%ED%95%8F-%EB%B0%98%ED%8C%94-%ED%8B%B0%EC%85%94%EC%B8%A0/36991/category/437/display/2/");
                startActivity(intent);
            }
        });

        LinearLayout layout2 = (LinearLayout) findViewById(R.id.layout2);
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://www.meosidda.com/product/8%EC%BB%AC%EB%9F%AC-%EB%B0%94%EC%9D%B4%EC%98%A4-%EC%9B%8C%EC%8B%B1-%ED%97%A8%EB%A6%AC%EB%84%A5-%EC%85%94%EC%B8%A0/37300/category/97/display/2/");
                startActivity(intent);
            }
        });

        LinearLayout layout3 = (LinearLayout) findViewById(R.id.layout3);
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://www.meosidda.com/product/%EC%9A%B0%EB%B8%94%EB%A6%AC%EB%8F%84%EB%B0%98%ED%95%9C-%EB%8C%80%EB%B0%95%ED%95%8F-%EC%9D%B8%EC%83%9D%EC%8A%AC%EB%9E%99%EC%8A%A4/29534/category/585/display/2/");
                startActivity(intent);
            }
        });

        LinearLayout layout4 = (LinearLayout) findViewById(R.id.layout4);
        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://www.meosidda.com/product/%ED%8F%AC%EC%BC%93-%EB%B0%B4%EB%94%A9-%EC%8A%A4%ED%8C%90-%EC%8A%AC%EB%9E%99%EC%8A%A4-%EB%B0%98%EB%B0%94%EC%A7%80/37160/category/88/display/1/");
                startActivity(intent);
            }
        });

        LinearLayout layout5 = (LinearLayout) findViewById(R.id.layout5);
        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://www.meosidda.com/product/%EC%9E%AD%EC%8A%A8-%EC%8A%A4%EB%8B%88%EC%BB%A4%EC%A6%88/21683/category/86/display/2/");
                startActivity(intent);
            }
        });

        LinearLayout layout6 = (LinearLayout) findViewById(R.id.layout6);
        layout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://www.meosidda.com/product/16011-%EC%84%A0%EA%B8%80%EB%9D%BC%EC%8A%A4/28591/category/110/display/1/");
                startActivity(intent);
            }
        });
    }
}