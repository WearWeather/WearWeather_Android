package com.wearweather.mainTabFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.wearweather.R;

import org.w3c.dom.Text;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    Context mContext;
    List<hourly_connecting_items> mData;

    public RecyclerViewAdapter(Context mContext, List<hourly_connecting_items> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;
        v= LayoutInflater.from(mContext).inflate(R.layout.item_hourly,parent,false);
        MyViewHolder vHolder = new MyViewHolder(v);
        return vHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tv_days.setText(mData.get(position).getDays());
        holder.tv_image.setImageResource(mData.get(position).getWeather_photo());
        holder.tv_low.setText(mData.get(position).getLow_temp());
        holder.tv_high.setText(mData.get(position).getHigh_temp());

    }

    @Override
    public int getItemCount() {

        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_days;
        private ImageView tv_image;
        private TextView tv_low;
        private TextView tv_high;

        public MyViewHolder(View itemView) {
            super(itemView);

            tv_days = (TextView) itemView.findViewById(R.id.yoil_hourly);
            tv_image = (ImageView) itemView.findViewById(R.id.hourly_image);
            tv_low =(TextView) itemView.findViewById(R.id.hourly_low_temp);
            tv_high =(TextView) itemView.findViewById(R.id.hourly_high_temp);


        }
    }
}
