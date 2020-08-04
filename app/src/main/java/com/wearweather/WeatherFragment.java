package com.wearweather;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.tabs.TabLayout;
import com.wearweather.mainTabFragment.daily_tab;
import com.wearweather.mainTabFragment.hourly_tab;
import com.wearweather.mainTabFragment.weekly_tab;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.core.widget.NestedScrollView;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WeatherFragment extends Fragment {

    private GpsTracker gpsTracker;

    private TextView region;                //도시
    private TextView current_temp;          //현재기온
    private TextView current_location;      //현재 위치
    private TextView current_bodily_temp;   //현재 위치
    private TextView current_rain;          //강우량


    ViewPager viewPager; //tab
    TabLayout tabLayout;//tab

    public WeatherFragment(){ //tab

    }
    public static WeatherFragment getInstance() { return new WeatherFragment(); } //tab

    @Override
    public void onCreate(Bundle savedInstanceState){ //api
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_weather, container, false);
        viewPager = rootView.findViewById(R.id.viewPager); //tab
        tabLayout = rootView.findViewById(R.id.tabLayout); //tab

        /* OpenWeatherMap API */

        region=(TextView)rootView.findViewById(R.id.region_text);
        current_temp = (TextView)rootView.findViewById(R.id.temp_now);
        current_location = (TextView)rootView.findViewById(R.id.region_text);
        current_bodily_temp=(TextView)rootView.findViewById(R.id.bodily_temp);
        current_rain=(TextView)rootView.findViewById(R.id.precipitation_text);


        find_weather(37.5665,126.9780); //서울
        //find_weather(35.7988,128.5935);               //대구

        return rootView;
    }

    public void find_weather(double latitude, double longitude){
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
                    region.setText(city_name);

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




                    /* 유빈 코드
                    String temp = String.valueOf(main_object.getDouble("temp"));
                    String city = response.getString("name");

                    current_location.setText(city);

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE-MM-dd");
                    String formatted_date = sdf.format(calendar.getTime());

                    double temp_int = Double.parseDouble(temp);
                    double centi = (temp_int -32) /1.8000;
                    centi = Math.round(centi);
                    int i = (int)centi;
                    current_temp.setText(String.valueOf(i));
                     */

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





    //Call onActivity Create Method for tab

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) { // all for tab
        super.onActivityCreated(savedInstanceState);

        setUpViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setUpViewPager(ViewPager viewPager) { //all for tab
        SectionPagerAdapter adapter = new SectionPagerAdapter((getChildFragmentManager()));

        adapter.addFragment(new hourly_tab(), "Hourly");
        adapter.addFragment(new daily_tab(), "Daily");
        adapter.addFragment(new weekly_tab(), "Weekly");

        viewPager.setAdapter(adapter);
    }
}