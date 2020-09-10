// TODO: setProgress,
package com.wearweather;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class DustActivity extends AppCompatActivity {
    public static int value;
    String city;
    ConstraintLayout layout;
    CircleProgressBar pm10ProgressBar;
    TextView tvLocation, tvDate, pm10Grade, pm25Value, pm25Grade, o3Value, o3Grade, coValue, coGrade;
    ImageView back;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dust);

        /*****현재 위치 설정*****/
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        setLocation();

        /*****배경이미지 설정****/
        layout = findViewById(R.id.layout);
        setBackground();

        /*****날짜 설정****/
        tvDate = (TextView) findViewById(R.id.tvDate);
        setDate();

        //back button
        back = (ImageView)findViewById(R.id.dust_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /*****미세먼지, 초미세먼지, 일산화탄소 농도, 오존 농도 설정*****/
        pm10Grade = (TextView) findViewById(R.id.pm10Grade);
        pm25Value = (TextView) findViewById(R.id.pm25Value);
        pm25Grade = (TextView) findViewById(R.id.pm25Grade);
        o3Value = (TextView) findViewById(R.id.o3Value);
        o3Grade = (TextView) findViewById(R.id.o3Grade);
        coValue = (TextView) findViewById(R.id.coValue);
        coGrade = (TextView) findViewById(R.id.coGrade);

        try {
            setAtmosphere(city);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /***** PROGRESSBAR displaying the concentration of fine dust *****/
        pm10ProgressBar=(CircleProgressBar)findViewById(R.id.pm10ProgressBar);
        pm10ProgressBar.setProgress(value);
    }

    private void setLocation() {
        Intent intent = getIntent();
        city = intent.getStringExtra("city");
        Log.e("SEULGI DUST CHECK",city);
        tvLocation.setText(city);

        if(city.substring(0,3).equals("전라북")) city = "전북";
        else if(city.substring(0,3).equals("전라남")) city = "전남";
        else if(city.substring(0,3).equals("경상북")) city = "경북";
        else if(city.substring(0,3).equals("경상남")) city = "경남";
        else if(city.substring(0,3).equals("충청북")) city = "충북";
        else if(city.substring(0,3).equals("충청남")) city = "충남";
        else city = city.substring(0, 2);
        Log.e("SEULGI DUST CHECK",city);

        if (!city.equals("서울") && !city.equals("부산") && !city.equals("대구") && !city.equals("인천") &&
                !city.equals("광주") && !city.equals("대전") && !city.equals("울산") && !city.equals("경기") &&
                !city.equals("강원") && !city.equals("충북") && !city.equals("충남") && !city.equals("전북") &&
                !city.equals("전남") && !city.equals("경북") && !city.equals("경남") && !city.equals("제주") &&
                !city.equals("세종")) {
            tvLocation.setText("서울시 중구");
            city = "서울";
            Toast.makeText(getApplicationContext(), "위치를 찾을 수 없음: set default:서울시", Toast.LENGTH_LONG).show();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setAtmosphere(String address) throws ExecutionException, InterruptedException {
        /*XML Parsing*/
        String url = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty?serviceKey=xhl67Y2J0Pgav3Pia7oYbh%2BBzg1EclA%2BOq4I%2BssvNRp8vFt55cRnMgSnD9t601fwh7QfbpU61dVcnr9RX5Jw6A%3D%3D&numOfRows=10&sidoName="+city+"&ver=1.3&";
        String resultText = null;
        resultText = new XMLParsingTask(DustActivity.this, url).execute().get();
        String[] array = resultText.split("_");

        setPm10(array[2]);
        setPm25(array[3]);
        setO3(array[1]);
        setCo(array[0]);
    }

    private void setPm10(String s) {
        if (s.equals("-")) {
            pm10Grade.setText("-");
        } else {
            value = Integer.parseInt(s);
            if (value >= 0 && value <= 30) {
                pm10Grade.setText("좋음");
            } else if (value > 30 && value <= 80) {
                pm10Grade.setText("보통");
            } else if (value > 80 && value <= 150) {
                pm10Grade.setText("나쁨");
            } else {
                pm10Grade.setText("매우나쁨");
            }
        }
    } //미세먼지

    private void setPm25(String s) {
        pm25Value.setText(s);
        if (s.equals("-")) {
            pm25Grade.setText(s);
        } else {
            double value = Double.parseDouble(s);
            if (value >= 0 && value <= 15) pm25Grade.setText("좋음");
            else if (value > 15 && value <= 35) pm25Grade.setText("보통");
            else if (value > 35 && value <= 75) pm25Grade.setText("나쁨");
            else pm25Grade.setText("매우나쁨");
        } //초미세먼지
    } //초미세먼지

    private void setO3(String s) {

        o3Value.setText(s);
        if (s.equals("-")) o3Grade.setText(s);
        else {
            double value = Double.parseDouble(s);
            if (value >= 0 && value <= 0.030) o3Grade.setText("좋음");
            else if (value > 0.030 && value <= 0.090) o3Grade.setText("보통");
            else if (value > 0.090 && value <= 0.150) o3Grade.setText("나쁨");
            else o3Grade.setText("매우나쁨");
        } //오존
    } //오존

    private void setCo(String s) {
        coValue.setText(s);
    } //일산화탄소

    private void setBackground() {
        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis);
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
        String hour = hourFormat.format(date);
        int time = Integer.parseInt(hour);
        if(time >= 0 && time < 6){
            layout.setBackgroundResource(R.drawable.sunny_night_background);
        }
        else if(time >= 6 && time < 15){
            layout.setBackgroundResource(R.drawable.sunny_afternoon_background);
        }
        else if(time >= 15 && time < 20){
            layout.setBackgroundResource(R.drawable.sunny_sunset_background);
        }
        else if(time >= 20 && time < 24){
            layout.setBackgroundResource(R.drawable.sunny_night_background);
        }
    }

    private void setDate() {
        Date currentTime = Calendar.getInstance().getTime();
        String currentDate = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일", Locale.KOREAN).format(currentTime);
        tvDate.setText(currentDate);
    }
}