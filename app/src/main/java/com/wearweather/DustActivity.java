package com.wearweather;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class DustActivity extends AppCompatActivity {

    LinearLayout layout;
    CircleProgressBar progressBar;
    TextView tvLocation, tvDate, dustPercent, dustPhase, ulDustPercent, ulDustPhase, coPoint, coPhase, o3Percent, o3Phase;


    private GpsTracker gpsTracker;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dust);


        /*현재 위치 설정*/
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        setLocation();

        /*배경이미지 설정*/
        layout = findViewById(R.id.layout);
        setBackground();

        /*현재 날짜 설정*/
        tvDate = (TextView) findViewById(R.id.tvDate);
        setDate();

        /*미세먼지, 초미세먼지, 일산화탄소 농도, 오존 농도 설정*/
//        dustPercent = (TextView) findViewById(R.id.dustPercent);
        dustPhase = (TextView) findViewById(R.id.dustPhase);
        ulDustPercent = (TextView) findViewById(R.id.ulDustPercent);
        ulDustPhase = (TextView) findViewById(R.id.ulDustPhase);
        o3Percent = (TextView) findViewById(R.id.o3Percent);
        o3Phase = (TextView) findViewById(R.id.o3Phase);
        coPoint = (TextView) findViewById(R.id.coPoint);
        coPhase = (TextView) findViewById(R.id.coPhase);

        setAtmosphere("종로구");
//        progressBar=(ProgressBar)findViewById(R.id.progressBar);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setAtmosphere(String address) {

        //현재 url 종로구
        String url = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?stationName=%EC%A2%85%EB%A1%9C%EA%B5%AC&dataTerm=month&pageNo=1&numOfRows=10&ServiceKey=xhl67Y2J0Pgav3Pia7oYbh%2BBzg1EclA%2BOq4I%2BssvNRp8vFt55cRnMgSnD9t601fwh7QfbpU61dVcnr9RX5Jw6A%3D%3D&ver=1.3";
        String resultText = null;
        try {
            resultText = new XMLParsingTask(DustActivity.this, url).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String[] array = resultText.split("_");


        progressBar = new CircleProgressBar(DustActivity.this);
//        int progress=Integer.parseInt(array[2]);
//        progressBar.setProgress(progress,true);
        setPhase(array[2], "dust");
        ulDustPercent.setText(array[3] + "㎍/m³");
        setPhase(array[3], "co");
        o3Percent.setText(array[1]);
        setPhase(array[1], "o3");
        coPoint.setText(array[4]);

    }

    private void setPhase(String point, String field) {
        double percent = Double.parseDouble(point);

        if (field.equals("dust")) {
            if (percent >= 0 && percent <= 30) point = "좋음";
            else if (percent > 30 && percent <= 80) point = "보통";
            else if (percent > 80 && percent <= 150) point = "나쁨";
            else point = "매우나쁨";

            dustPhase.setText(point);
        } else if (field.equals("co")) {
            if (percent >= 0 && percent <= 15) point = "좋음";
            else if (percent > 15 && percent <= 35) point = "보통";
            else if (percent > 35 && percent <= 75) point = "나쁨";
            else point = "매우나쁨";

            ulDustPhase.setText(point);
        } else if (field.equals("o3")) {
            if (percent >= 0 && percent <= 0.030) point = "좋음";
            else if (percent > 0.030 && percent <= 0.090) point = "보통";
            else if (percent > 0.090 && percent <= 0.150) point = "나쁨";
            else point = "매우나쁨";


            o3Phase.setText(point);
        }
    }

    private void setLocation() {

        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        } else {
            checkRunTimePermission();
        }
        gpsTracker = new GpsTracker(getApplicationContext());
        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();
        String address = getCurrentAddress(latitude, longitude);
        tvLocation.setText(address);

    }

    private void setBackground() {
        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis);
        SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
        String hour = hourFormat.format(date);
        int time = Integer.parseInt(hour);
        if (time >= 0 && time < 6) {
            layout.setBackgroundResource(R.drawable.sunny_night_background);

        } else if (time >= 6 && time < 18) {
            layout.setBackgroundResource(R.drawable.sunny_afternoon_background);
        } else if (time >= 18 && time < 20) {
            layout.setBackgroundResource(R.drawable.sunny_sunset_background);
        } else if (time >= 18 && time < 24) {
            layout.setBackgroundResource(R.drawable.sunny_night_background);
        }
    }

    private void setDate() {
        Date currentTime = Calendar.getInstance().getTime();
        String currentDate = new SimpleDateFormat("yyyy년 MM월 dd일 EE요일", Locale.KOREAN).format(currentTime);
        tvDate.setText(currentDate);
    }

    public String getCurrentAddress(double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }

        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";
        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString() + "\n";
    }

    //GPS 활성화를 위한 메소드
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    void checkRunTimePermission() {

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION);

        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)

            // 3.  위치 값을 가져올 수 있음

        } else {
            //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(DustActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(getApplicationContext(), "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(DustActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(DustActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}
