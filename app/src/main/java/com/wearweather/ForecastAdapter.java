package com.wearweather;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ForecastAdapter extends RecyclerView.Adapter {

    ArrayList<NewsData> items;
    Context context;
    private AdapterView.OnItemClickListener onItemClickListener = null;
    private static View.OnClickListener onClickListener;

    public ForecastAdapter(ArrayList<NewsData> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.row_news,parent,false);
        VH vh = new VH(itemView);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VH vh = (VH)holder;
        NewsData item = items.get(position);
        vh.txtTitle.setText(item.getTitle());
        vh.txtDate.setText(item.getPubDate());
        vh.txtDiscription.setText(item.getDescription());



    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView txtTitle, txtDiscription, txtDate;
        CardView cardView;

        public VH(@NonNull View v) {
            super(v);

            txtTitle = v.findViewById(R.id.textView_title);
            txtDiscription = v.findViewById(R.id.info_text);
            txtDate = v.findViewById(R.id.txtDate);
            cardView = v.findViewById(R.id.card_view);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    
                }
            });

        }
    }
}
