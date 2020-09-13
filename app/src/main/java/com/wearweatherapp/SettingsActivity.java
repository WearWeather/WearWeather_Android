package com.wearweatherapp;

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

import com.wearweatherapp.main.MainSearchActivity;

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
                Intent intent = new Intent(getApplicationContext(), MainSearchActivity.class);
                startActivity(intent);


                /*AlertDialog.Builder alertDialog = new AlertDialog.Builder(SettingsActivity.this);
                alertDialog.setTitle("주소 변경하기");
                alertDialog.setMessage("동/읍/면을 입력하세요");

                final EditText editText = new EditText(SettingsActivity.this);
                alertDialog.setView(editText);
                alertDialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        location_name = editText.getText().toString();
                        Geocoder mGeoCoder = new Geocoder(SettingsActivity.this);
                        try {
                            List<Address> location = null;
                            location = mGeoCoder.getFromLocationName(location_name,1);
                            Log.e("SEULGI CHECK",location.toString());
                            if(!location.isEmpty()){
                                double lat = location.get(0).getLatitude();
                                double lon = location.get(0).getLongitude();
                                Toast.makeText(getApplicationContext(),"주소가 "+location_name+"으로 설정되었습니다",Toast.LENGTH_SHORT).show();
                                Log.e("SEULGI GEOCODER",lat+" "+lon);
                                PreferenceManager.setFloat(SettingsActivity.this,"LATITUDE",(float)lat);
                                PreferenceManager.setFloat(SettingsActivity.this,"LONGITUDE",(float)lon);

                                PreferenceManager.setBoolean(SettingsActivity.this,"IS_ADDRESS_CHANGED",true);
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"주소가 잘못되었습니다!",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                alertDialog.setNegativeButton("취소",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });
                alertDialog.create();
                alertDialog.show();*/
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
                PreferenceManager.setString(SettingsActivity.this,"CITY",address);
                Log.e("SEULGI",address);

                int space_cnt=0,s_ind=0,e_ind=0;
                for(int i = 0; i < address.length(); i++){
                    if(address.charAt(i) == ' '){
                        if(space_cnt==0)
                            s_ind= i;
                        if(space_cnt==2)
                            e_ind=i;
                        space_cnt++;
                    }
                    if(space_cnt==3)
                        break;
                }
                address = address.substring(s_ind+1,e_ind);

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

        /*favorites = (FrameLayout)findViewById(R.id.settings_favorites);
        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FavoritesActivity.class);
                startActivity(intent);
            }
        });*/

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

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

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
        return address.getAddressLine(0).toString()+"\n";

    }

}