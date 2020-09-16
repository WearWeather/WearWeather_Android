package com.wearweatherapp.ui.clothing;
/* 5°C ~ 9°C 이하 일 경우 추천해주는 액티비티 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wearweatherapp.ui.news.ClothesClickWebView;
import com.wearweatherapp.R;

public class TemperatureClothingActivity2 extends AppCompatActivity {
    TextView tx9;
    ImageView btnToHome;
    ViewGroup tokboki, shoes, thickCoat, neat, scarf, leather;
    String url;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_clothing2);

        btnToHome = (ImageView) findViewById(R.id.btnToHome);
        btnToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tx9 = (TextView)findViewById(R.id.textView9);
        intent = getIntent(); /*데이터 수신*/

        String name = intent.getStringExtra("temperature");

        tx9.setText(name + "°C");

        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.layout_tokboki:
                        url = "https://www.meosidda.com/product/%EC%9D%B4%EC%A4%91%EC%A7%80-%EB%A1%B1%ED%9B%84%EB%93%9C-%EB%96%A1%EB%B3%B6%EC%9D%B4%EC%BD%94%ED%8A%B8/36431/category/29/display/1/";
                        intent = new Intent(getApplicationContext(), ClothesClickWebView.class);
                        intent.putExtra("link", url);
                        startActivity(intent);
                    case R.id.layout_winterShoes:
                        url = "https://www.meosidda.com/product/4cm-%EB%A0%88%EC%9D%B4%EC%8A%A4%EC%97%85-%EC%9B%8C%EC%BB%A4/36109/category/87/display/1/";
                        intent = new Intent(getApplicationContext(), ClothesClickWebView.class);
                        intent.putExtra("link", url);
                        startActivity(intent);
                    case R.id.layout_thickCoat:
                        url = "https://www.meosidda.com/product/%EC%98%86-%EB%B0%91%EB%8B%A8%ED%8A%B8%EC%9E%84-%EC%98%A4%EB%B2%84%ED%95%8F-%EB%A1%B1-%EC%BD%94%ED%8A%B8/36754/category/78/display/1/";
                        intent = new Intent(getApplicationContext(), ClothesClickWebView.class);
                        intent.putExtra("link", url);
                        startActivity(intent);
                    case R.id.layout_neat:
                        url = "https://www.meosidda.com/product/%EB%A0%88%EC%9D%B4-%EB%B0%98%ED%8F%B4%EB%9D%BC-%ED%8B%B0%EC%85%94%EC%B8%A0/36378/category/90/display/1/";
                        intent = new Intent(getApplicationContext(), ClothesClickWebView.class);
                        intent.putExtra("link", url);
                        startActivity(intent);
                    case R.id.layout_scarf:
                        url = "https://www.meosidda.com/product/%EA%B3%A8%EC%A7%80-%EC%9A%B8-%EB%AA%A9%EB%8F%84%EB%A6%AC/34342/category/1560/display/1/";
                        intent = new Intent(getApplicationContext(), ClothesClickWebView.class);
                        intent.putExtra("link", url);
                        startActivity(intent);
                    case R.id.layout_leather:
                        url = "https://www.meosidda.com/product/%EB%A7%8C%EC%A1%B1%EB%8F%84-good-%EA%B0%80%EC%A3%BD%EC%9E%90%EC%BC%93/30616/category/29/display/1/";
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