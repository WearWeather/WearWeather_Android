package com.wearweather.main;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.wearweather.MainSearchActivity;
import com.wearweather.main.MainActivity;
import com.wearweather.NewsXMLActivity;
import com.wearweather.PreferenceManager;
import com.wearweather.R;
import com.wearweather.SectionPagerAdapter;
import com.wearweather.SettingsActivity;
import com.wearweather.main.MainTabPagerAdapter;
import com.wearweather.mainTabFragment.daily_tab;
import com.wearweather.mainTabFragment.hourly_tab;
import com.wearweather.mainTabFragment.weekly_tab;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainWeatherFragment extends Fragment {
    private int tabPosition;

    private DrawerLayout drawerLayout;
    private ImageButton menuButton;
    private NavigationView navigationView;
    private TextView dateNow;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageButton searchButton;
    private TabLayout tabLayout;
    private SectionPagerAdapter pagerAdpater;
    private ViewPager viewPager;
    private ImageButton delButton;

    private TextView region;                //도시
    private TextView current_temp;          //현재기온
    private TextView current_location;      //현재 위치
    private TextView current_bodily_temp;   //현재 위치
    private TextView current_rain;          //강우량

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ImageButton mainButton = (ImageButton) getView().findViewById(R.id.main_search_btn);
//        mainButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(this, MainSearchActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main_weather, container, false);

        /* set Background */
        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_layout);
        dateNow = (TextView)rootView.findViewById(R.id.temp_time);
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


        /* drawer layout */
        drawerLayout = (DrawerLayout)rootView.findViewById(R.id.main_drawer_layout);
        menuButton = (ImageButton)rootView.findViewById(R.id.main_menu_btn);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!drawerLayout.isDrawerOpen(Gravity.RIGHT)){
                    drawerLayout.openDrawer(Gravity.RIGHT);
                    Toast toast = Toast.makeText(getContext(),"menu button clicked", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }
            }
        });
        navigationView = (NavigationView)rootView.findViewById(R.id.drawer_nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_news:
                        startActivity(new Intent(rootView.getContext(), NewsXMLActivity.class));
                        break;
                    case R.id.nav_settings:
                        startActivity(new Intent(rootView.getContext(), SettingsActivity.class));
                        break;
                }
                return false;
            }
        });

        /*3 Tab layout */
//        viewPager = rootView.findViewById(R.id.viewPager); //tab
//        tabLayout = rootView.findViewById(R.id.tabLayout); //tab

        /* OpenWeatherMap API */

        region=(TextView)rootView.findViewById(R.id.region_text);
        current_temp = (TextView)rootView.findViewById(R.id.temp_now);
        current_location = (TextView)rootView.findViewById(R.id.region_text);
        current_bodily_temp=(TextView)rootView.findViewById(R.id.bodily_temp);
        current_rain=(TextView)rootView.findViewById(R.id.precipitation_text);

        displayWeather(rootView.getContext());

        return rootView;
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

    public void find_weather(float latitude, float longitude){
        String url="http://api.openweathermap.org/data/2.5/forecast?appid=944b4ec7c3a10a1bbb4a432d14e6f979&units=metric&id=1835848";
        url += "&lat="+String.valueOf(latitude)+"&lon="+String.valueOf(longitude);

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray list=response.getJSONArray("list");
                    JSONObject city_object = response.getJSONObject("city");

                    /* city */
                    String city_name=city_object.getString("name");
                    //region.setText(city_name);

                    /* weather */
                    JSONObject list_item = list.getJSONObject(0);
                    JSONObject main_object = list_item.getJSONObject("main");
                    JSONObject rain_object = list_item.getJSONObject("rain");

                    //기온
                    String temperature = main_object.getString("temp");
                    temperature = String.valueOf(Math.round(Double.valueOf(temperature)));
                    current_temp.setText(temperature+getString(R.string.temperature_unit));

                    //체감온도
                    String bodily_temperature = main_object.getString("feels_like");
                    bodily_temperature = String.valueOf(Math.round(Double.valueOf(bodily_temperature)));
                    current_bodily_temp.setText(getString(R.string.bodily_temprature)+" "+bodily_temperature+getString(R.string.temperature_unit));

                    //강우량
                    String rain_3h = rain_object.getString("3h");
                    rain_3h = String.valueOf(Math.round(Double.valueOf(rain_3h)*10));
                    current_rain.setText(getString(R.string.precipitation)+" "+rain_3h+getString(R.string.precipitation_unit));

                }catch(JSONException e)
                {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("SEULGI API RESPONSE",error.toString());
            }
        }
        );
        RequestQueue queue =  Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(jor);
    }


    private void getKoreanAddressByPoint(double latitude, double longitude){
        String url = "http://api.vworld.kr/req/address?service=address&request=getAddress&key=2566C643-E5EC-317E-BBAB-B6064E98ACC2&type=both";
        url += "&point="+String.valueOf(longitude)+","+String.valueOf(latitude);

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject res=response.getJSONObject("response");
                    JSONObject result= res.getJSONArray("result").getJSONObject(0);
                    JSONObject structure = result.getJSONObject("structure");

                    String level1 = structure.getString("level1");
                    String level2 = structure.getString("level2");
                    Log.e("SEULGI ADDRESS API",level2);

                    region.setText(level1+" "+level2);

                }catch(JSONException e)
                {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("SEULGI ADDRESS API",error.toString());
            }
        }
        );
        RequestQueue queue =  Volley.newRequestQueue(getActivity().getApplicationContext());
        queue.add(jor);
    }

    public void displayWeather(Context context) {
        String region_lat = "REGION"+String.valueOf(tabPosition+1)+"_LAT";
        String region_lon = "REGION"+String.valueOf(tabPosition+1)+"_LON";

        float lat = PreferenceManager.getFloat(context,region_lat);
        float lon = PreferenceManager.getFloat(context,region_lon);

        find_weather(lat,lon);
        getKoreanAddressByPoint(lat,lon);
    }
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) { // all for tab
//        super.onActivityCreated(savedInstanceState);
//
//        setUpViewPager(viewPager);
//        tabLayout.setupWithViewPager(viewPager);
//
//        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
////                mSelectedPosition = tabLayout.getSelectedTabPosition();
////                adapter.selectedTab(mSelectedPosition);
//                pagerAdpater.notifyDataSetChanged();
//                viewPager.setCurrentItem(tab.getPosition());
//                tab = tabLayout.getTabAt(0);
//                tab.select();
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//    }

//    private void setUpViewPager(ViewPager viewPager) { //all for tab
//        SectionPagerAdapter adapter = new SectionPagerAdapter((getChildFragmentManager()));
//
//        adapter.addFragment(new hourly_tab(), "Hourly");
//        adapter.addFragment(new daily_tab(), "Daily");
//        adapter.addFragment(new weekly_tab(), "Weekly");
//
//
////        viewPager.setAdapter(adapter);
////        tabLayout.setupWithViewPager(viewPager);
//    }

    public void setTabPosition(int position) { tabPosition=position; }

    public int getTabPosition() { return tabPosition; }
}