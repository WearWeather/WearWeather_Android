package com.wearweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.tabs.TabLayout;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.android.volley.Request.*;
import static com.android.volley.Request.Method.*;


public class MainWeatherActivity extends AppCompatActivity {
    private TextView dateNow;
    TextView current_temp; //현재기온
    TextView current_location; //현재 위치
//    private TextView description; //날씨 설명(ex: 맑음, 흐림)
    private SwipeRefreshLayout swipeRefreshLayout;
    private DrawerLayout drawerLayout;
    private ImageButton menuButton;
    private ImageButton searchButton;
    private TabLayout tabLayout;
    private WeatherPagerAdpater pagerAdpater;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_weather);

        /* set Background */
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_layout);
        dateNow = (TextView)findViewById(R.id.temp_time);
        setBackgroundByTime();

        /* pull to refresh */
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /* 새로고침 시 수행될 코드 */
                setBackgroundByTime();

                /* 새로고침 완료 */
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        /* Current Temperature */
        current_temp = (TextView)findViewById(R.id.temp_now);

        /* Location */
        current_location = (TextView)findViewById(R.id.region_text);

        /* Weather Description */
//        description = (TextView)findViewById(R.id.textview);




        /* drawer layout */
        drawerLayout = (DrawerLayout)findViewById(R.id.main_drawer_layout);
        menuButton = (ImageButton)findViewById(R.id.main_menu_btn);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!drawerLayout.isDrawerOpen(Gravity.RIGHT)){
                    drawerLayout.openDrawer(Gravity.RIGHT);
                    Toast toast = Toast.makeText(getApplicationContext(),"menu button clicked", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }
            }
        });

        searchButton = (ImageButton)findViewById(R.id.main_search_btn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(),"search button clicked", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        /* tab layout */
        tabLayout = (TabLayout) findViewById(R.id.main_tab_layout);
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        pagerAdpater = new WeatherPagerAdpater(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager = (ViewPager) findViewById(R.id.main_tab_viewpager);
        viewPager.setAdapter(pagerAdpater);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                pagerAdpater.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        find_weather();
    }

    public void find_weather(){
        String url="api.openweathermap.org/data/2.5/forecast?id=1835848&appid=944b4ec7c3a10a1bbb4a432d14e6f979&units=metric";
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject main_object = response.getJSONObject("main");
                    JSONArray array=response.getJSONArray("weather");
                    JSONObject object = array.getJSONObject(0);
                    String temp = String.valueOf(main_object.getDouble("temp"));
                    String city = response.getString("name");

                    current_temp.setText(temp);
                    current_location.setText(city);

//                    Calendar calendar = Calendar.getInstance();
//                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE-MM-dd");
//                    String formatted_date = sdf.format(calendar.getTime());
                    double temp_int = Double.parseDouble(temp);
                    double centi = (temp_int -32) /1.8000;
                    centi = Math.round(centi);
                    int i = (int)centi;
                    current_temp.setText(String.valueOf(i));

                }catch(JSONException e)
                {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jor);


    }

    private void setBackgroundByTime() {
        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis);
        SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String hourText = sdfHour.format(date);

        String nowText = sdfNow.format(date);
        dateNow.setText(nowText);

        int time = Integer.parseInt(hourText);
        if(time >= 0 && time < 6){
            swipeRefreshLayout.setBackgroundResource(R.drawable.sunny_night_background);
        }
        else if(time >= 6 && time < 12){
            swipeRefreshLayout.setBackgroundResource(R.drawable.sunny_morning_background);
        }
        else if(time >= 12 && time < 18){
            swipeRefreshLayout.setBackgroundResource(R.drawable.sunny_afternoon_background);
        }
        else if(time >= 18 && time < 20){
            swipeRefreshLayout.setBackgroundResource(R.drawable.sunny_sunset_background);
        }
        else if(time >= 18 && time < 24){
            swipeRefreshLayout.setBackgroundResource(R.drawable.sunny_night_background);
        }
    }

}