package com.wearweather;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.MyViewHolder> {
    private List<NewsData> mDataset;
    private static View.OnClickListener onClickListener;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView TextView_title;
        public TextView TextView_content;
        public SimpleDraweeView ImageView_title;
        public View rootView;

        public MyViewHolder(View v) {
            super(v);
            TextView_title = v.findViewById(R.id.textView_title);
            TextView_content = v.findViewById(R.id.textView_content);
            ImageView_title = (SimpleDraweeView) v.findViewById(R.id.imageView_title);
            rootView = v;

            v.setClickable(true); // 누를수 있는 상태 여부
            v.setEnabled(true); // 활성화 상태인지 여부
            v.setOnClickListener(onClickListener);
        }
    }
    // Provide a suitable constructor (depends on the kind of dataset)
    public ForecastAdapter(List<NewsData> myDataset, Context context, View.OnClickListener onClick) {
        mDataset = myDataset;
        onClickListener = onClick;
        Fresco.initialize(context);
    }
    // Create new views (invoked by the layout manager)
    @Override
    public ForecastAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LinearLayout v = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_news, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //holder.TextView_title.setText(mDataset[position]);
        NewsData news = mDataset.get(position);
        holder.TextView_title.setText(news.getContent());

        if(news.getContent() != "null"){
            holder.TextView_content.setText(news.getContent());
        }else{
            holder.TextView_content.setText("No contents");
        }
        Uri uri = Uri.parse(news.getUrlToImage());
        holder.ImageView_title.setImageURI(uri);

        holder.rootView.setTag(position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }

    public NewsData getNews(int position){
        return mDataset == null ? null : mDataset.get(position);
    }
}
