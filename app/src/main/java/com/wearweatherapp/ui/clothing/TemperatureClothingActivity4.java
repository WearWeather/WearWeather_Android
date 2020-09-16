package com.wearweatherapp.ui.clothing;
/* 14°C ~ 17°C 이하 일 경우 추천해주는 액티비티 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wearweatherapp.ui.news.ClothesClickWebView;
import com.wearweatherapp.R;

public class TemperatureClothingActivity4 extends AppCompatActivity {
    TextView tx9;
    ImageView btnToHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_clothing4);

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
                intent.putExtra("link", "https://meosidda.com/product/%EB%B9%88%ED%8B%B0%EC%A7%80-%ED%9B%84%EB%93%9C-%EB%A0%88%EC%9D%B4%EC%96%B4%EB%93%9C-%EC%95%BC%EC%83%81/36723/category/29/display/1/");
                startActivity(intent);
            }
        });

        LinearLayout layout2 = (LinearLayout) findViewById(R.id.layout2);
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://meosidda.com/product/%EC%98%A4%EB%B2%84%ED%95%8F-%EB%AC%B4%EC%A7%80-%EA%B0%80%EB%94%94%EA%B1%B4/36752/category/29/display/1/");
                startActivity(intent);
            }
        });

        LinearLayout layout3 = (LinearLayout) findViewById(R.id.layout3);
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://meosidda.com/product/%EB%AA%A8%ED%81%AC%EB%84%A5-%EB%AF%B8%EB%8B%88%EB%A9%80-%EB%9D%BC%EC%9A%B4%EB%93%9C-%EB%8B%88%ED%8A%B8/36515/category/30/display/1/");
                startActivity(intent);
            }
        });

        LinearLayout layout4 = (LinearLayout) findViewById(R.id.layout4);
        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://meosidda.com/product/%EB%8D%B0%EC%9D%BC%EB%A6%AC-7%EC%BB%AC%EB%9F%AC-%EB%A0%88%ED%84%B0%EB%A7%81-%ED%9B%84%EB%93%9C%ED%8B%B0/33697/category/30/display/1/");
                startActivity(intent);
            }
        });

        LinearLayout layout5 = (LinearLayout) findViewById(R.id.layout5);
        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://meosidda.com/product/5cm-%EC%9C%84%EB%93%9C-%EC%BA%90%EC%A5%AC%EC%96%BC-%EC%8A%A4%EB%8B%88%EC%BB%A4%EC%A6%88/33878/category/86/display/1/");
                startActivity(intent);
            }
        });

        LinearLayout layout6 = (LinearLayout) findViewById(R.id.layout6);
        layout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://meosidda.com/product/%EB%AC%B4%EC%A7%80-%EC%A1%B0%EA%B1%B0-%ED%8C%AC%EC%B8%A0/36798/category/33/display/1/");
                startActivity(intent);
            }
        });
    }
}