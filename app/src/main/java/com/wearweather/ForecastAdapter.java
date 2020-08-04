package com.wearweather;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter {

    ArrayList<NewsData> items;
    Context context;

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

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        TextView txtTitle, txtDiscription, txtDate;

        public VH(@NonNull View v) {
            super(v);

            txtTitle = v.findViewById(R.id.textView_title);
            txtDiscription = v.findViewById(R.id.info_text);
            txtDate = v.findViewById(R.id.txtDate);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String link= items.get(getLayoutPosition()).getLink();

                    Intent intent= new Intent(context, NewsClickViewActivity.class);
                    intent.putExtra("Link", link);
                    context.startActivity(intent);
                }
            });


        }
    }
}
