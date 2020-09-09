package com.wearweather;
/* 4°C 이하 일 경우 추천해주는 액티비티 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.wearweather.main.MainActivity;

public class TemperatureClothingActivity extends AppCompatActivity {
    TextView tx9;
    Button btnToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_clothing);

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
                intent.putExtra("link", "https://www.meosidda.com/product/%EA%B5%BF%EC%B4%88%EC%9D%B4%EC%8A%A4-%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C-%EB%A1%B1%ED%8C%A8%EB%94%A9/30369/category/76/display/1/");
                startActivity(intent);
            }
        });

        LinearLayout layout2 = (LinearLayout) findViewById(R.id.layout2);
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://www.meosidda.com/product/%EC%B6%94%EC%9C%84%EC%95%BC-%EB%AC%BC%EB%9F%AC%EB%82%98%EB%9D%BC-%EA%B8%B0%EB%AA%A8%ED%9B%84%EB%93%9C%ED%8B%B0/29974/category/30/display/1/");
                startActivity(intent);
            }
        });

        LinearLayout layout3 = (LinearLayout) findViewById(R.id.layout3);
        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://www.meosidda.com/product/%EC%98%86-%EB%B0%91%EB%8B%A8%ED%8A%B8%EC%9E%84-%EC%98%A4%EB%B2%84%ED%95%8F-%EB%A1%B1-%EC%BD%94%ED%8A%B8/36754/category/78/display/1/");
                startActivity(intent);
            }
        });

        LinearLayout layout4 = (LinearLayout) findViewById(R.id.layout4);
        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://www.meosidda.com/product/%EB%A0%88%EC%9D%B4-%EB%B0%98%ED%8F%B4%EB%9D%BC-%ED%8B%B0%EC%85%94%EC%B8%A0/36378/category/90/display/1/");
                startActivity(intent);
            }
        });

        LinearLayout layout5 = (LinearLayout) findViewById(R.id.layout5);
        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://www.meosidda.com/product/4cm-%EB%A0%88%EC%9D%B4%EC%8A%A4%EC%97%85-%EC%9B%8C%EC%BB%A4/36109/category/87/display/1/");
                startActivity(intent);
            }
        });

        LinearLayout layout6 = (LinearLayout) findViewById(R.id.layout6);
        layout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent  = new Intent(getApplicationContext(), ClothesClickWebView.class);
                intent.putExtra("link", "https://www.meosidda.com/product/%EA%B3%A8%EC%A7%80-%EC%9A%B8-%EB%AA%A9%EB%8F%84%EB%A6%AC/34342/category/1560/display/1/");
                startActivity(intent);
            }
        });


    }
}