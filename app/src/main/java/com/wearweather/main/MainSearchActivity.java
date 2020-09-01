package com.wearweather.main;


import android.app.SearchManager;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.wearweather.PreferenceManager;
import com.wearweather.R;
import com.wearweather.SettingsActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainSearchActivity extends AppCompatActivity {

    //https://link2me.tistory.com/1377
    private SearchView searchView;
    private RecyclerView recyclerView;
    private SearchResultAdapter adapter;
    private ArrayList<Address> addressList;

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
                adapter.setFilter(filter(query));

                /*addressList = new ArrayList<>();
                Geocoder mGeoCoder = new Geocoder(getApplicationContext());
                try {
                    List<Address> location = null;
                    location = mGeoCoder.getFromLocationName(query,20);

                    Log.e("SEULGI LOCATION SIZE",""+location.size());

                    if(!location.isEmpty()){
                        addressList.clear();
                        for(int i=0;i<location.size();i++){
                            addressList.add(location.get(i));
                        }
                    }
                    else { }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                adapter.setFilter(addressList);
                adapter.notifyDataSetChanged();*/

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                //adapter.setFilter(filter(newText));
                return false;
            }
        });

    }

    private ArrayList<Address> filter(String query) {
        final ArrayList<Address> filteredList = new ArrayList<>();

        Geocoder mGeoCoder = new Geocoder(getApplicationContext());
        try {
            List<Address> location = null;
            location = mGeoCoder.getFromLocationName(query,10);
            Log.e("SEULGI SEARCH SIZE",String.valueOf(location.size()));

            if(!location.isEmpty()){
                for(int i=0;i<location.size();i++){
                    filteredList.add(location.get(i));
                }
            }
            else { }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return filteredList;
    }

    private class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ViewHolder>{
        ArrayList<Address> mlist;

        SearchResultAdapter(ArrayList<Address> list){
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
            Address item = mlist.get(position);
            double lat = item.getLatitude();
            double lon = item.getLongitude();

            String address = getCurrentAddress(lat,lon);
            Log.e("SEULGI SEARCH RESULT",address+" lat:"+lat+" lon:"+lon);
            int first=-1, second=-1, third=-1;
            first = address.indexOf(' ');
            if(first!=-1){
                second=address.indexOf(' ',first+1);
                if(second!=-1){
                    third=address.indexOf(' ',second+1);
                }
            }
            if(first!=-1 && third!=-1){
                address = address.substring(first,third);
            }
            else {
                address=address.substring(0,address.length()-1);
            }

            holder.address.setText(address);
            Log.e("SEULGI SEARCH",""+address);
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
                        PreferenceManager.setFloat(getApplicationContext(),"LATITUDE",(float)lat);
                        PreferenceManager.setFloat(getApplicationContext(),"LONGITUDE",(float)lon);
                        PreferenceManager.setBoolean(getApplicationContext(),"IS_ADDRESS_CHANGED",true);
                        PreferenceManager.setString(getApplicationContext(),"CITY",(String)address.getText());


                        Toast.makeText(getApplicationContext(), "주소가 "+address.getText()+"로 설정되었습니다",Toast.LENGTH_SHORT).show();

                        finish();
                    }
                });
            }
            void setList(ArrayList<Address> mlist){
                mlist = mlist;
            }
        }
        public void setFilter(ArrayList<Address> items) {
            mlist.clear();
            mlist.addAll(items);
            notifyDataSetChanged();
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

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }*/
}