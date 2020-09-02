package com.wearweather.main;


import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.wearweather.PreferenceManager;
import com.wearweather.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainSearchActivity extends AppCompatActivity {

    //https://link2me.tistory.com/1377
    private SearchView searchView;
    private RecyclerView recyclerView;
    private SearchResultAdapter adapter;
    private ArrayList<MyAddress> addressList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        recyclerView = (RecyclerView) findViewById(R.id.search_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        addressList = new ArrayList<>();
        adapter = new SearchResultAdapter(addressList);
        recyclerView.setAdapter(adapter);

        searchView = (SearchView)findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //adapter.setFilter(filter(query));
                adapter.setFilter(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //adapter.setFilter(filter(newText));
                return false;
            }
        });

    }

    private ArrayList<MyAddress> filter(String query) {
        final ArrayList<MyAddress> filteredList = new ArrayList<>();

        String url="http://api.vworld.kr/req/search?key=2566C643-E5EC-317E-BBAB-B6064E98ACC2&service=search&request=search&type=address&query=";
        url += query+"&category=road&size=100";
        Log.e("SEULGI SEARCH API URL", url);

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject res = response.getJSONObject("response");
                    Log.e("SEULGI SEARCH STATUS",res.getString("status"));
                    JSONObject result = res.getJSONObject("result");
                    JSONArray items = result.getJSONArray("items");
                    for(int i=0;i< items.length();i++){
                        String address = items.getJSONObject(0).getJSONObject("address").getString("road");
                        double lat = items.getJSONObject(0).getJSONObject("point").getDouble("x");
                        double lon = items.getJSONObject(0).getJSONObject("point").getDouble("y");
                        addressList.add(new MyAddress(address,lat,lon));
                        filteredList.add(new MyAddress(address,lat,lon));
                        Log.e("SEULGI addressList",String.valueOf(addressList.size()));
                        Log.e("SEULGI FILTERED",String.valueOf(filteredList.size()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("SEULGI SEARCH API ERROR",error.toString());
            }
        });

        RequestQueue queue =  Volley.newRequestQueue(getApplicationContext());
        queue.add(jor);

        return filteredList;
    }

    private class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder>{
        ArrayList<MyAddress> mlist;

        SearchResultAdapter(ArrayList<MyAddress> list){
            mlist = list;
        }

        @NonNull
        @Override
        public SearchResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_result, parent,false);

            return new SearchResultAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull SearchResultAdapter.ViewHolder holder, int position) {
            MyAddress item = mlist.get(position);
            double lat = item.getLat();
            double lon = item.getLon();


            holder.address.setText(item.getAddress());
            holder.lat = lat;
            holder.lon = lon;
        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder{
            TextView address;
            double lat;
            double lon;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                address =(TextView) itemView.findViewById(R.id.address);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String temp = (String)address.getText();
                        int first=-1, second=-1;
                        first = temp.indexOf(' ');
                        if(first!=-1){
                            second=temp.indexOf(' ',first+1);
                        }

                        temp = temp.substring(0,second);

                        PreferenceManager.setFloat(getApplicationContext(),"LATITUDE",(float)lat);
                        PreferenceManager.setFloat(getApplicationContext(),"LONGITUDE",(float)lon);
                        PreferenceManager.setBoolean(getApplicationContext(),"IS_ADDRESS_CHANGED",true);
                        PreferenceManager.setString(getApplicationContext(),"CITY",temp);
                        Log.e("SEULGI CHECK",temp+" lat:"+lat+" lon:"+lon);


                        Toast.makeText(getApplicationContext(), "주소가 "+temp+"로 설정되었습니다",Toast.LENGTH_SHORT).show();

                        finish();
                    }
                });
            }
            void setList(ArrayList<MyAddress> mlist){
                mlist = mlist;
            }
        }
        public void setFilter(ArrayList<MyAddress> items) {
            mlist.clear();
            mlist.addAll(items);
            notifyDataSetChanged();
        }

        public void setFilter(String query){
            mlist.clear();
            String url="http://api.vworld.kr/req/search?key=2566C643-E5EC-317E-BBAB-B6064E98ACC2&service=search&request=search&type=address&query=";
            url += query+"&category=road&size=100";
            Log.e("SEULGI SEARCH API URL", url);

            JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject res = response.getJSONObject("response");
                        Log.e("SEULGI SEARCH STATUS",res.getString("status"));
                        if(res.getString("status").equals("OK")){
                            JSONObject result = res.getJSONObject("result");
                            JSONArray items = result.getJSONArray("items");
                            for(int i=0;i< items.length();i++){
                                String address = items.getJSONObject(i).getJSONObject("address").getString("road");
                                double lat = items.getJSONObject(i).getJSONObject("point").getDouble("y");
                                double lon = items.getJSONObject(i).getJSONObject("point").getDouble("x");
                                mlist.add(new MyAddress(address,lat,lon));
                            }
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"주소가 잘못되었습니다.",Toast.LENGTH_SHORT).show();
                        }

                        notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("SEULGI SEARCH API ERROR",error.toString());
                }
            });

            RequestQueue queue =  Volley.newRequestQueue(getApplicationContext());
            queue.add(jor);
        }

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


    private class MyAddress {
        String address;
        double lat;
        double lon;
        public MyAddress(String address,double lat,double lon) {
            this.address=address;
            this.lat=lat;
            this.lon=lon;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public void setLon(double lon) {
            this.lon = lon;
        }

        public double getLat() {
            return lat;
        }

        public double getLon() {
            return lon;
        }

        public String getAddress() {
            return address;
        }
    }
}