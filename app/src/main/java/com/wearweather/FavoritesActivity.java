package com.wearweather;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wearweather.main.DailyItem;
import com.wearweather.main.MainWeatherFragment;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView citites_size;
    private ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        citites_size=(TextView)findViewById(R.id.cities_size);
        backBtn=(ImageView)findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.cities_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        ArrayList<String> list = new ArrayList<>();
        list.add("경기도 수원시");
        list.add("서울특별시 강남구");
        list.add("경기도 용인시");

        CityAdapter cityAdapter = new CityAdapter(list);
        recyclerView.setAdapter(cityAdapter);
        cityAdapter.notifyDataSetChanged();

    }

    private class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
        ArrayList<String> mlist;

        public CityAdapter(ArrayList<String> mlist){
            this.mlist = mlist;
        }


        @NonNull
        @Override
        public CityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cities, parent,false);

            return new CityAdapter.ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull CityAdapter.ViewHolder holder, int position) {
            String item = mlist.get(position);

            holder.address.setText(item);
        }

        @Override
        public int getItemCount() {
            return mlist.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder{
            TextView address;
            TextView address_detail;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                address =(TextView) itemView.findViewById(R.id.address);
                address_detail =(TextView) itemView.findViewById(R.id.address_detail);
            }
        }
    }
}