package com.wearweatherapp.ui.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.wearweatherapp.util.AddressParsingUtil;
import com.wearweatherapp.util.GpsTracker;
import com.wearweatherapp.util.PreferenceManager;
import com.wearweatherapp.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    private FrameLayout address_find;
    private FrameLayout redirect;
    private FrameLayout favorites;
    private String location_name;
    private ImageView backBtn;


    private FrameLayout opensource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        address_find= findViewById(R.id.search_layout);
        address_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });

        redirect= findViewById(R.id.redirect_layout);
        redirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GpsTracker gpsTracker = new GpsTracker(SettingsActivity.this);

                double latitude = gpsTracker.getLatitude();
                double longitude = gpsTracker.getLongitude();

                PreferenceManager.setFloat(SettingsActivity.this,"LATITUDE",(float)latitude);
                PreferenceManager.setFloat(SettingsActivity.this,"LONGITUDE",(float)longitude);

                String address = getCurrentAddress(latitude, longitude);
                address = AddressParsingUtil.getSigunguFromFullAddress(address);

                Toast.makeText(SettingsActivity.this, "주소가 "+address+"로 설정되었습니다",Toast.LENGTH_SHORT).show();

                PreferenceManager.setBoolean(SettingsActivity.this,"IS_ADDRESS_CHANGED",true);
                PreferenceManager.setString(getApplicationContext(),"CITY",address);
            }
        });

        backBtn = (ImageView) findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        opensource = (FrameLayout)findViewById(R.id.opensource_layout);
        opensource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), OpenSourceActivity.class);
                startActivity(intent);
            }
        });
    }

    public String getCurrentAddress( double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        try {
            addresses = geocoder.getFromLocation(latitude,longitude,7);
        } catch (IOException ioException) {
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
        return address.getAddressLine(0).toString()+"\n";
    }

}