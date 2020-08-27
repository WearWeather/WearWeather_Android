package com.wearweather.main;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.media.Image;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.wearweather.DustActivity;
import com.wearweather.GpsTracker;
import com.wearweather.NewsXMLActivity;
import com.wearweather.PreferenceManager;
import com.wearweather.R;
import com.wearweather.SettingsActivity;
import com.wearweather.TemperatureClothingActivity;
import com.wearweather.TemperatureClothingActivity2;
import com.wearweather.TemperatureClothingActivity3;
import com.wearweather.TemperatureClothingActivity4;
import com.wearweather.TemperatureClothingActivity5;
import com.wearweather.TemperatureClothingActivity6;
import com.wearweather.TemperatureClothingActivity7;
import com.wearweather.TemperatureClothingActivity8;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class MainWeatherFragment extends Fragment {
    private int tabPosition;
    private View rootView;

    private DrawerLayout drawerLayout;
    private ImageButton menuButton;
    private NavigationView navigationView;
    private TextView dateNow;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageButton searchButton;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageButton delButton;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private ImageView weathericon;

    private TextView region;                //도시
    private TextView current_temp;          //현재기온
    private TextView current_location;      //현재 위치
    private TextView current_bodily_temp;   //현재 위치
    private TextView current_rain;          //강우량
    private TextView current_desc;          //설명
    private TextView current_temp_min;
    private TextView current_temp_max;
    private TextView current_pressure;
    private TextView current_humidity;
    private TextView current_sunrise;
    private TextView current_sunset;
    private String temperature;
    private String bodily_temperature;

    private String Daily_image;
    private ImageButton search_button;
    private String dailyLow;
    private String dailyHigh;
    private String temp_f;

    private String rain_1h;
    private String rain_3h;
    private String address_text;
    private String level1;
    private String level2;
    private ArrayList<HourlyItem> hourlyItemList = new ArrayList<>();
    private ArrayList<DailyItem> dailyItemList = new ArrayList<>();
    private String temp_extra;

    private final Class [] clothingClasses = {
            TemperatureClothingActivity.class,TemperatureClothingActivity2.class, TemperatureClothingActivity3.class,
            TemperatureClothingActivity4.class, TemperatureClothingActivity5.class, TemperatureClothingActivity6.class,
            TemperatureClothingActivity7.class, TemperatureClothingActivity8.class};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_weather, container, false);

        /* OpenWeatherMap API */
        region=(TextView)rootView.findViewById(R.id.region_text);
        current_temp = (TextView)rootView.findViewById(R.id.temp_now);
        current_location = (TextView)rootView.findViewById(R.id.region_text);
        current_bodily_temp=(TextView)rootView.findViewById(R.id.bodily_temp);
        current_rain=(TextView)rootView.findViewById(R.id.precipitation_text);
        current_desc=(TextView)rootView.findViewById(R.id.weather_description);
        weathericon = (ImageView)rootView.findViewById(R.id.image_weather);
        current_temp_max =(TextView)rootView.findViewById(R.id.temp_max);
        current_temp_min =(TextView)rootView.findViewById(R.id.temp_min);
        current_pressure =(TextView)rootView.findViewById(R.id.pressure);
        current_humidity =(TextView)rootView.findViewById(R.id.humidity);
        current_sunrise =(TextView)rootView.findViewById(R.id.sunrise);
        current_sunset =(TextView)rootView.findViewById(R.id.sunset);
//        search_button = (ImageButton)rootView.findViewById(R.id.main_search_btn);

        /* 날씨 보여주기 */
        displayWeather(rootView.getContext());

        /* set Background */
        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_layout);
        //dateNow = (TextView)rootView.findViewById(R.id.temp_time);
        setBackgroundByTime();

        /* pull to refresh */
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /* 새로고침 시 수행될 코드 */
                setBackgroundByTime();
                displayWeather(rootView.getContext());

                /* 새로고침 완료 */
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        /*search_button.setOnClickListener(new View.OnClickListener(){
            // When the button is pressed/clicked, it will run the code below
            @Override
            public void onClick(View v){
                // Intent is what you use to start another activity
                Intent intent = new Intent(getActivity(), MainSearchActivity.class);
                startActivity(intent);
            }
        });*/

        /* drawer layout */
        drawerLayout = (DrawerLayout)rootView.findViewById(R.id.main_drawer_layout);
        menuButton = (ImageButton)rootView.findViewById(R.id.main_menu_btn);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!drawerLayout.isDrawerOpen(Gravity.RIGHT)){
                    drawerLayout.openDrawer(Gravity.RIGHT);
                    //Toast toast = Toast.makeText(getContext(),"menu button clicked", Toast.LENGTH_SHORT);
                    //toast.show();
                }
                else {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                }
            }
        });
        navigationView = (NavigationView)rootView.findViewById(R.id.drawer_nav_view);
        navigationView.addHeaderView((View)inflater.inflate(R.layout.drawer_header,container,false));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int temperature = Integer.parseInt(temp_extra);

                int index=0;

                if(temperature<=4) index = 0;
                else if(temperature<=9) index = 1;
                else if(temperature<=13) index = 2;
                else if(temperature<=17) index = 3;
                else if(temperature<=20) index = 4;
                else if(temperature<=23) index = 5;
                else if(temperature<=27) index = 6;
                else index = 7;

                switch (item.getItemId()){
                    case R.id.nav_dust:
                        startActivity(new Intent(rootView.getContext(), DustActivity.class)
                        .putExtra("city",PreferenceManager.getString(rootView.getContext(),"CITY")));
                        break;
                    case R.id.nav_clothing:
                        startActivity(new Intent(rootView.getContext(), clothingClasses[index])
                        .putExtra("temperature",temp_extra)
                        .putExtra("city",level1+" "+level2));
                        Log.e("SEULGI INTENT",temp_extra);
                        break;
                    case R.id.nav_news:
                        startActivity(new Intent(rootView.getContext(), NewsXMLActivity.class));
                        break;
                    case R.id.nav_settings:
                        startActivity(new Intent(rootView.getContext(), SettingsActivity.class));
                        break;
                    default:
                        break;
                }
                drawerLayout.closeDrawer(Gravity.RIGHT);
                return false;
            }
        });


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(PreferenceManager.getBoolean(getContext(),"IS_ADDRESS_CHANGED")==true){
            PreferenceManager.setBoolean(getContext(),"IS_ADDRESS_CHANGED",false);
            displayWeather(getContext());
        }
    }

    private void setBackgroundByTime() {
        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis);
        SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String hourText = sdfHour.format(date);

        String nowText = sdfNow.format(date);
        //dateNow.setText(nowText);

        int time = Integer.parseInt(hourText);
        if(time >= 0 && time < 6){
            swipeRefreshLayout.setBackgroundResource(R.drawable.sunny_night_background);
        }
        else if(time >= 6 && time < 15){
            swipeRefreshLayout.setBackgroundResource(R.drawable.sunny_afternoon_background);
        }
        else if(time >= 15 && time < 20){
            swipeRefreshLayout.setBackgroundResource(R.drawable.sunny_sunset_background);
        }
        else if(time >= 20 && time < 24){
            swipeRefreshLayout.setBackgroundResource(R.drawable.sunny_night_background);
        }
    }

    public void find_weather(float latitude, float longitude){
        String url="http://api.openweathermap.org/data/2.5/weather?appid=944b4ec7c3a10a1bbb4a432d14e6f979&units=metric&id=1835848&lang=kr";
        url += "&lat="+String.valueOf(latitude)+"&lon="+String.valueOf(longitude);
        Log.e("SEULGI WEATHER API URL", url);

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject main_object = response.getJSONObject("main");
                    JSONArray weather_object = response.getJSONArray("weather");
                    JSONObject sys_object = response.getJSONObject("sys");

                    //기온
                    temperature = main_object.getString("temp");
                    temperature = String.valueOf(Math.round(Double.valueOf(temperature)));
                    temp_extra = temperature;
                    current_temp.setText(temperature+getString(R.string.temperature_unit));

                    //체감온도
                    bodily_temperature = main_object.getString("feels_like");
                    bodily_temperature = String.valueOf(Math.round(Double.valueOf(bodily_temperature)));
                    current_bodily_temp.setText(bodily_temperature+getString(R.string.temperature_unit));

                    //최고온도
                    String temp_max = main_object.getString("temp_min");
                    temp_max = String.valueOf(Math.round(Double.valueOf(temp_max)));
                    current_temp_max.setText(getString(R.string.temperature_max)+" "+temp_max+getString(R.string.temperature_unit));

                    //최저온도
                    String temp_min = main_object.getString("temp_min");
                    temp_min = String.valueOf(Math.round(Double.valueOf(temp_min)));
                    current_temp_min.setText(getString(R.string.temperature_min)+" "+temp_min+getString(R.string.temperature_unit));

                    //기압
                    String pressure = main_object.getString("pressure");
                    current_pressure.setText(pressure+getString(R.string.pressure_unit));

                    //습도
                    String humidity = main_object.getString("humidity");
                    current_humidity.setText(humidity+getString(R.string.percent_unit));

                    //일출
                    String sunrise = sys_object.getString("sunrise");
                    long timestamp = Long.parseLong(sunrise);
                    Date date = new java.util.Date(timestamp*1000L);
                    SimpleDateFormat sdf = new java.text.SimpleDateFormat("a hh:mm");
                    sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+9"));
                    sunrise = sdf.format(date);
                    current_sunrise.setText(sunrise);

                    //일몰
                    String sunset = sys_object.getString("sunset");
                    timestamp = Long.parseLong(sunset);
                    date = new java.util.Date(timestamp*1000L);
                    sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+9"));
                    sunset = sdf.format(date);
                    current_sunset.setText(sunset);

                    //강우량
                    JSONObject weather= weather_object.getJSONObject(0);
                    String main = weather.getString("main");
                    if(response.has("rain")){
                        JSONObject rain_object = response.getJSONObject("rain");
                        if(rain_object.has("1h")){
                            rain_1h = rain_object.getString("3h");
                            rain_1h = String.valueOf(Math.round(Double.valueOf(rain_1h)*10));
                            current_rain.setText(rain_1h+getString(R.string.precipitation_unit));

                        }
                        else if(rain_object.has("3h")){
                            rain_3h = rain_object.getString("3h");
                            rain_3h = String.valueOf(Math.round(Double.valueOf(rain_3h)*10));
                            current_rain.setText(rain_3h+getString(R.string.precipitation_unit));
                        }
                        else {
                            current_rain.setText("0"+getString(R.string.precipitation_unit));
                        }
                    }
                    else {
                        current_rain.setText("0"+getString(R.string.precipitation_unit));
                    }

                    String description = weather.getString("description");
                    current_desc.setText(description);

                    //날씨 아이콘
                    String icon = weather.getString("icon");
                    int resID = getResId("icon_"+icon, R.drawable.class);
                    weathericon.setImageResource(resID);

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
    public void find_future_weather(float latitude, float longitude) {

        String url="http://api.openweathermap.org/data/2.5/onecall?appid=944b4ec7c3a10a1bbb4a432d14e6f979&units=metric&id=1835848&lang=kr";
        //http://api.openweathermap.org/data/2.5/onecall?appid=944b4ec7c3a10a1bbb4a432d14e6f979&units=metric&id=1835848&lang=kr&lat=35&lon=127
        url += "&lat="+String.valueOf(latitude)+"&lon="+String.valueOf(longitude);

        if(!hourlyItemList.isEmpty()) hourlyItemList.clear();
        hourlyItemList = new ArrayList<>();

        if(!dailyItemList.isEmpty()) dailyItemList.clear();
        dailyItemList = new ArrayList<>();

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray hourly_object = response.getJSONArray("hourly");
                    JSONArray daily_object = response.getJSONArray("daily");

                    for(int i=0;i<hourly_object.length() && i<36; i+=2){ //2시간 간격 | 18번만 나오게
                        JSONObject rec= hourly_object.getJSONObject(i);

                        //시간
                        String dt = rec.getString("dt");
                        long timestamp = Long.parseLong(dt);
                        Date date = new java.util.Date(timestamp*1000L);
                        SimpleDateFormat sdf = new java.text.SimpleDateFormat("a h" + "시");
                        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+9"));
                        dt = sdf.format(date);

                        //온도
                        temp_f = rec.getString("temp");
                        temp_f = String.valueOf(Math.round(Double.valueOf(temp_f)));

                        JSONArray weather_object = rec.getJSONArray("weather");
                        JSONObject weather = weather_object.getJSONObject(0);
                        String icon = weather.getString("icon");
                        int resID = getResId("icon_"+icon, R.drawable.class);

                        hourlyItemList.add(new HourlyItem(dt,resID,temp_f+getString(R.string.temperature_unit)));
                    }

                    for(int i=1; i<daily_object.length(); i++){
                        JSONObject rec = daily_object.getJSONObject(i);
                        JSONObject get_temp = rec.getJSONObject("temp");

                        //요일
                        String dt = rec.getString("dt");
                        long timestamp = Long.parseLong(dt);
                        Date date = new java.util.Date(timestamp*1000L);
                        SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEEE", Locale.KOREAN);
                        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT+9"));
                        dt = sdf.format(date);

                        //최저기온
                        dailyLow = get_temp.getString("min");
                        dailyLow = String.valueOf(Math.round(Double.valueOf(dailyLow)));

                        //최고기온
                        dailyHigh = get_temp.getString("max");
                        dailyHigh = String.valueOf(Math.round(Double.valueOf(dailyHigh)));

                        //아이콘
                        JSONArray weather_object = rec.getJSONArray("weather");
                        JSONObject weather = weather_object.getJSONObject(0);
                        String icon = weather.getString("icon");
                        int resID = getResId("icon_"+icon, R.drawable.class);
;


                        dailyItemList.add(new DailyItem(dt,dailyLow+getString(R.string.temperature_unit),dailyHigh+getString(R.string.temperature_unit),resID));
                    }

                    /* HOURLY RECYCLERVIEW */
                    recyclerView = (RecyclerView) rootView.findViewById(R.id.hourly_recycler);
                    LinearLayoutManager layoutManager_h= new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
                    recyclerView.setLayoutManager(layoutManager_h);

                    HourlyItemAdapter adapter_h;
                    adapter_h = new HourlyItemAdapter(getActivity(),hourlyItemList);
                    recyclerView.setAdapter(adapter_h);
                    adapter_h.notifyDataSetChanged();

                    /*DAILY RECYCLERVIEW */
                    recyclerView2 = (RecyclerView) rootView.findViewById(R.id.daily_recycler);
                    LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());
                    recyclerView2.setLayoutManager(layoutManager);

                    DailyItemAdapter adapter;
                    adapter = new DailyItemAdapter(getActivity(),dailyItemList);
                    recyclerView2.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

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

        Log.e("SEULGI ADDRESS API URL",url);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONObject res=response.getJSONObject("response");
                    JSONObject result= res.getJSONArray("result").getJSONObject(0);
                    JSONObject structure = result.getJSONObject("structure");

                    level1 = structure.getString("level1");
                    level2 = structure.getString("level2");
                    PreferenceManager.setString(getContext(),"CITY",address_text);

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

    public String getCurrentAddress( double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(getContext(), "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(getContext(), "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }
        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(getContext(), "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";
        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";
    }

    public void displayWeather(Context context) {
        float lat = PreferenceManager.getFloat(context,"LATITUDE");
        float lon = PreferenceManager.getFloat(context,"LONGITUDE");

        find_weather(lat,lon);
        find_future_weather(lat,lon);

        String address = getCurrentAddress(lat, lon);
        if(address!=null){
            PreferenceManager.setString(getContext(),"CITY",address);
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
            address = address.substring(s_ind,e_ind);

            region.setText(address);

        }

        //getKoreanAddressByPoint(lat,lon);
    }

    public void setTabPosition(int position) { tabPosition=position; }

    public int getTabPosition() { return tabPosition; }

    private class TabPagerAdapter extends FragmentStatePagerAdapter {
        List<Fragment> fragmentList;

        public TabPagerAdapter(@NonNull FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }

    //For Hourly Adapter
    private class HourlyItemAdapter extends RecyclerView.Adapter<MainWeatherFragment.HourlyItemAdapter.ViewHolder>{
        List<HourlyItem> hourlyItems;
        Context context;

        public HourlyItemAdapter(Context context, List<HourlyItem> hourlyItems) {
            this.hourlyItems = hourlyItems;
            this.context=context;
        }

        @NonNull
        @Override
        public MainWeatherFragment.HourlyItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hourly, parent,false);

            return new MainWeatherFragment.HourlyItemAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MainWeatherFragment.HourlyItemAdapter.ViewHolder holder, int position) {


            HourlyItem item = hourlyItems.get(position);
            holder.time_h.setText(item.getDays());
            holder.temp_h.setText(item.getTemp_hourly());
            holder.image.setImageResource(item.getWeather_photo());

        }

        @Override
        public int getItemCount() {
            return hourlyItems.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder{
            TextView time_h;
            TextView temp_h;
            ImageView image;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                time_h=(TextView) itemView.findViewById(R.id.time_hourly);
                temp_h=(TextView)itemView.findViewById(R.id.temp_hourly);
                image=(ImageView)itemView.findViewById(R.id.hourly_image);
            }
        }
    }

    //For Daily Adapter
    private class DailyItemAdapter extends RecyclerView.Adapter<MainWeatherFragment.DailyItemAdapter.ViewHolder>{
        List<DailyItem> dailyItems;
        Context context;

        public DailyItemAdapter(Context context, List<DailyItem> dailyItems) {
            this.dailyItems = dailyItems;
            this.context=context;
        }

        @NonNull
        @Override
        public MainWeatherFragment.DailyItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily, parent,false);

            return new MainWeatherFragment.DailyItemAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MainWeatherFragment.DailyItemAdapter.ViewHolder holder, int position) {

            DailyItem item = dailyItems.get(position);
            holder.yoil.setText(item.getDays());
            holder.low.setText(item.getLow_temp());
            holder.high.setText(item.getHigh_temp());
            holder.image.setImageResource(item.getWeather_photo());

        }

        @Override
        public int getItemCount() {
            return dailyItems.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder{
            TextView yoil;
            TextView low;
            TextView high;
            ImageView image;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                yoil=(TextView) itemView.findViewById(R.id.time_daily);
                low=(TextView)itemView.findViewById(R.id.daily_low_temp);
                high=(TextView)itemView.findViewById(R.id.daily_high_temp);
                image=(ImageView)itemView.findViewById(R.id.daily_image);
            }
        }
    }
    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}