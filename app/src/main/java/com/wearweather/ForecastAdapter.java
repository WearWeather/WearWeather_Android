package com.wearweather;

import android.content.Context;
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
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter {

    ArrayList<NewsData> items; // NewsData 클래스 형식의 리스트
    Context context; // ForecastAdapter 액티비티의 context

    public ForecastAdapter(ArrayList<NewsData> items, Context context) { // 뉴스 어댑터 생성자
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { // 뷰 홀더
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
    } // xml 링크에서 파싱한 뉴스의 갯수를 리턴

    class VH extends RecyclerView.ViewHolder{ // 커스텀 뷰 홀더 클래스

        TextView txtTitle, txtDiscription, txtDate;
        public View rootView;

        public VH(@NonNull View v) {
            super(v);

            txtTitle = v.findViewById(R.id.textView_title);
            txtDiscription = v.findViewById(R.id.info_text);
            txtDate = v.findViewById(R.id.txtDate);
            rootView = v;
            v.setClickable(true); // 1. 레이아웃을 클릭 가능한 상태로
            v.setEnabled(true); // 2. 활성화
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String link = items.get(getLayoutPosition()).getLink();
//                    if(link == null){ // NULL CHECKING
//                        Log.d("Zero", "뉴스 정보 없음");
//                    }
                    Intent intent = new Intent(context, NewsClickViewActivity.class);
                    intent.putExtra("Link", link);
                    context.startActivity(intent);
                }
            });
        }
    }
}
