package com.wearweather;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

public class SettingsActivity extends AppCompatActivity {
    private FrameLayout address_find;
    private String location_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        address_find= findViewById(R.id.redirect_layout);
        address_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SettingsActivity.this);
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
                alertDialog.show();
            }
        });


    }


}