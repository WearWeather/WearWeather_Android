package com.wearweatherapp.ui.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wearweatherapp.R;
import com.wearweatherapp.data.DailyItem;

import java.util.List;

public class DailyItemAdapter extends RecyclerView.Adapter<DailyItemAdapter.ViewHolder> {
    List<DailyItem> dailyItems;
    Context context;

    public DailyItemAdapter(Context context, List<DailyItem> dailyItems) {
        this.dailyItems = dailyItems;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily, parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

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

    class ViewHolder extends RecyclerView.ViewHolder{
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
