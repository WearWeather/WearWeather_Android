package com.wearweather.main;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.media.Image;
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
import com.wearweather.WeatherPagerAdpater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
    private WeatherPagerAdpater pagerAdpater;
    private ViewPager viewPager;
    private ImageButton delButton;
    private RecyclerView recyclerView;
    private ImageView weathericon;


    private TextView region;                //도시
    private TextView current_temp;          //현재기온
    private TextView current_location;      //현재 위치
    private TextView current_bodily_temp;   //현재 위치
    private TextView current_rain;          //강우량
    private TextView current_desc;          //설명
    private String temperature;
    private String bodily_temperature;
    private TextView lowTempDaily;
    private TextView highTempDaily;
    private ImageView imageDaily;
    private String dailyLow;
    private String dailyHigh;
    private String rain_3h;
    private String level1;
    private String level2;

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
        lowTempDaily =(TextView)rootView.findViewById(R.id.daily_low_temp);
        highTempDaily =(TextView)rootView.findViewById(R.id.daily_high_temp);
        imageDaily = (ImageView)rootView.findViewById(R.id.daily_image);

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
                        startActivity(new Intent(rootView.getContext(), DustActivity.class));
                        break;
                    case R.id.nav_clothing:
                        startActivity(new Intent(rootView.getContext(), clothingClasses[index])
                        .putExtra("temperature",temperature)
                        .putExtra("city",level1+" "+level2));
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




        /* Hourly recyclerview */

        Calendar calendar_h = Calendar.getInstance();
        int num_h = 6;
        int i_h=0;
        List<HourlyItem> hourlyItemList = new ArrayList<>();
        int isAMorPM = calendar_h.get(Calendar.AM_PM);

        //Calling the time after the current time
        while(i_h<num_h){
            calendar_h.add(Calendar.HOUR_OF_DAY, 2);
            Date day = calendar_h.getTime();
            if(isAMorPM == Calendar.AM){
                hourlyItemList.add(new HourlyItem("오전 "+ new SimpleDateFormat("hh", Locale.KOREAN).format(day.getTime())+"시",1,"27"));
            }
            else if(isAMorPM == Calendar.PM){
                hourlyItemList.add(new HourlyItem("오후 "+ new SimpleDateFormat("hh", Locale.KOREAN).format(day.getTime())+"시",1,"27"));
            }
            i_h++;
        }

        recyclerView = (RecyclerView) rootView.findViewById(R.id.hourly_recycler);
        LinearLayoutManager layoutManager_h= new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager_h);

        HourlyItemAdapter adapter_h;
        adapter_h = new HourlyItemAdapter(getActivity(),hourlyItemList);
        recyclerView.setAdapter(adapter_h);
        adapter_h.notifyDataSetChanged();


        /* Daily recyclerview */

        Calendar calendar = Calendar.getInstance();
        int num = 6;
        int i=0;
        List<DailyItem> dailyItemList = new ArrayList<>();
        //Calling the day of week after the current day
        while(i<num){
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            Date day = calendar.getTime();
            dailyItemList.add(new DailyItem(new SimpleDateFormat("EEEE", Locale.KOREAN).format(day.getTime()),1,"27","27"));
            i++;
        }

        recyclerView = (RecyclerView) rootView.findViewById(R.id.daily_recycler);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        DailyItemAdapter adapter;
        adapter = new DailyItemAdapter(getActivity(),dailyItemList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        displayWeather(rootView.getContext());

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
        String url="http://api.openweathermap.org/data/2.5/forecast?appid=944b4ec7c3a10a1bbb4a432d14e6f979&units=metric&id=1835848&lang=kr";
        url += "&lat="+String.valueOf(latitude)+"&lon="+String.valueOf(longitude);
        Log.e("SEULGI WEATHER API URL", url);

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try{
                    JSONArray list=response.getJSONArray("list");
                    JSONObject city_object = response.getJSONObject("city");

                    /* weather */
                    JSONObject list_item = list.getJSONObject(0);
                    JSONObject main_object = list_item.getJSONObject("main");
                    JSONArray weather_object = list_item.getJSONArray("weather");


                    //기온
                    temperature = main_object.getString("temp");
                    temperature = String.valueOf(Math.round(Double.valueOf(temperature)));
                    temp_extra = temperature;
                    current_temp.setText(temperature+getString(R.string.temperature_unit));

                    //체감온도
                    bodily_temperature = main_object.getString("feels_like");
                    bodily_temperature = String.valueOf(Math.round(Double.valueOf(bodily_temperature)));
                    current_bodily_temp.setText(getString(R.string.bodily_temprature)+" "+bodily_temperature+getString(R.string.temperature_unit));

//                    //Daily
//                    dailyLow= main_object.getString("temp_min");
//                    dailyLow = String.valueOf(Math.round(Double.valueOf(dailyLow)));
//                    lowTempDaily.setText(dailyLow+getString(R.string.temperature_unit));
//
//                    dailyHigh= main_object.getString("temp_max");
//                    dailyHigh = String.valueOf(Math.round(Double.valueOf(dailyHigh)));
//                    highTempDaily.setText(dailyHigh+getString(R.string.temperature_unit));

                    //강우량
                    JSONObject weather= weather_object.getJSONObject(0);
                    String main = weather.getString("main");
                    if(main.equals("Rain")){
                        JSONObject rain_object = list_item.getJSONObject("rain");
                        rain_3h = rain_object.getString("3h");
                        rain_3h = String.valueOf(Math.round(Double.valueOf(rain_3h)*10));
                        current_rain.setText(getString(R.string.precipitation)+" "+rain_3h+getString(R.string.precipitation_unit));
                    }
                    else {
                        current_rain.setText(getString(R.string.precipitation)+" "+"0"+getString(R.string.precipitation_unit));
                    }

                    String description = weather.getString("description");
                    current_desc.setText(description);

                    //날씨 아이콘
                    String icon = weather.getString("icon");
                    String iconurl = "http://openweathermap.org/img/wn/"+icon+"@2x.png";
                    Log.e("SEULGI ICON URL",iconurl);
                    ImageRequest iconjor = new ImageRequest(iconurl, new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap response) {
                            weathericon.setImageBitmap(response);
//                            imageDaily.setImageBitmap(response);

                        }
                    }, 0, 0, null,
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("SEULGI ICON API",error.toString());
                                }
                            });

                    RequestQueue queue2 =  Volley.newRequestQueue(getActivity().getApplicationContext());
                    queue2.add(iconjor);


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
//    public void find_future_weather(float latitude, float longitude) {
//        int i;
//        String url="http://api.openweathermap.org/data/2.5/forecast?appid=944b4ec7c3a10a1bbb4a432d14e6f979&units=metric&id=1835848&lang=kr";
//        url += "&lat="+String.valueOf(latitude)+"&lon="+String.valueOf(longitude);
//
//        try {
//            JSONObject reader = new JSONObject(result);
//
//            final String code = reader.optString("cod");
//            if ("404".equals(code)) {
//                if (longTermWeather == null) {
//                    longTermWeather = new ArrayList<>();
//                    longTermTodayWeather = new ArrayList<>();
//                    longTermTomorrowWeather = new ArrayList<>();
//                }
//                return ParseResult.CITY_NOT_FOUND;
//            }
//
//            longTermWeather = new ArrayList<>();
//            longTermTodayWeather = new ArrayList<>();
//            longTermTomorrowWeather = new ArrayList<>();
//
//            JSONArray list = reader.getJSONArray("list");
//            for (i = 0; i < list.length(); i++) {
//                Weather weather = new Weather();
//
//                JSONObject listItem = list.getJSONObject(i);
//                JSONObject main = listItem.getJSONObject("main");
//
//                weather.setDate(listItem.getString("dt"));
//                weather.setTemperature(main.getString("temp"));
//                weather.setDescription(listItem.optJSONArray("weather").getJSONObject(0).getString("description"));
//                JSONObject windObj = listItem.optJSONObject("wind");
//                if (windObj != null) {
//                    weather.setWind(windObj.getString("speed"));
//                    weather.setWindDirectionDegree(windObj.getDouble("deg"));
//                }
//                weather.setPressure(main.getString("pressure"));
//                weather.setHumidity(main.getString("humidity"));
//
//                JSONObject rainObj = listItem.optJSONObject("rain");
//                String rain = "";
//                if (rainObj != null) {
//                    rain = getRainString(rainObj);
//                } else {
//                    JSONObject snowObj = listItem.optJSONObject("snow");
//                    if (snowObj != null) {
//                        rain = getRainString(snowObj);
//                    } else {
//                        rain = "0";
//                    }
//                }
//                weather.setRain(rain);
//
//                final String idString = listItem.optJSONArray("weather").getJSONObject(0).getString("id");
//                weather.setId(idString);
//
//                final String dateMsString = listItem.getString("dt") + "000";
//                Calendar cal = Calendar.getInstance();
//                cal.setTimeInMillis(Long.parseLong(dateMsString));
//                weather.setIcon(setWeatherIcon(Integer.parseInt(idString), cal.get(Calendar.HOUR_OF_DAY)));
//
//                Calendar today = Calendar.getInstance();
//                if (cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
//                    longTermTodayWeather.add(weather);
//                } else if (cal.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) + 1) {
//                    longTermTomorrowWeather.add(weather);
//                } else {
//                    longTermWeather.add(weather);
//                }
//            }
//            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit();
//            editor.putString("lastLongterm", result);
//            editor.commit();
//        } catch (JSONException e) {
//            Log.e("JSONException Data", result);
//            e.printStackTrace();
//            return ParseResult.JSON_EXCEPTION;
//        }
//
//        return ParseResult.OK;
//    }
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

                    level1 = structure.getString("level1");
                    level2 = structure.getString("level2");
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
        //String region_lat = "REGION"+String.valueOf(tabPosition+1)+"_LAT";
        //String region_lon = "REGION"+String.valueOf(tabPosition+1)+"_LON";

        float lat = PreferenceManager.getFloat(context,"LATITUDE");
        float lon = PreferenceManager.getFloat(context,"LONGITUDE");

        find_weather(lat,lon);
        getKoreanAddressByPoint(lat,lon);
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

}