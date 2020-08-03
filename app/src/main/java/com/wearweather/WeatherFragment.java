package com.wearweather;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.viewpager.widget.ViewPager;
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

    /*API 사용시 적어둔 코드는 옆에 api라고 코멘트 써놓았습니다*/

//    private TextView current_temp; //현재기온 //api
//    private TextView current_location; //현재 위치 //api

    /*탭 만들때 사용한 코드는 옆에 tab이라고 코멘트 써놓음 */
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
        View rootView = (View)inflater.inflate(R.layout.fragment_weather, container, false);
        viewPager = rootView.findViewById(R.id.viewPager); //tab
        tabLayout = rootView.findViewById(R.id.tabLayout); //tab



        /*뮤트한거 its for api */

//        setContentView(R.layout.fragment_weather);
        /* Current Temperature */
//        current_temp = (TextView)rootView.findViewById(R.id.temp_now);
//
//        /* Location */
//        current_location = (TextView)rootView.findViewById(R.id.region_text);
//
//        find_weather();


        return rootView;
    }

    /*all muted codes for api */

//    public void find_weather(){
//        String url="api.openweathermap.org/data/2.5/forecast?id=1835848&appid=944b4ec7c3a10a1bbb4a432d14e6f979&units=metric";
//        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try{
//                    JSONObject main_object = response.getJSONObject("main");
//                    JSONArray array=response.getJSONArray("weather");
//                    JSONObject object = array.getJSONObject(0);
//                    String temp = String.valueOf(main_object.getDouble("temp"));
//                    String city = response.getString("name");
//
//                    current_temp.setText(temp);
//                    current_location.setText(city);
//
////                    Calendar calendar = Calendar.getInstance();
////                    SimpleDateFormat sdf = new SimpleDateFormat("EEEE-MM-dd");
////                    String formatted_date = sdf.format(calendar.getTime());
//                    double temp_int = Double.parseDouble(temp);
//                    double centi = (temp_int -32) /1.8000;
//                    centi = Math.round(centi);
//                    int i = (int)centi;
//                    current_temp.setText(String.valueOf(i));
//
//                }catch(JSONException e)
//                {
//                    e.printStackTrace();
//                }
//
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }
//        );
//        RequestQueue queue =  Volley.newRequestQueue(getActivity().getApplicationContext());
//        queue.add(jor);
//
//
//    }
//



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