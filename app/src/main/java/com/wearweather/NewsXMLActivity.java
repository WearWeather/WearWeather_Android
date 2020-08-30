package com.wearweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class NewsXMLActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeToRefreshNews;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<NewsData> myDataset = new ArrayList<>();
    private ForecastAdapter adapter;
    private TextView dateDisplay;
    private Calendar calendar;
    private SimpleDateFormat date_format;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        swipeToRefreshNews = (SwipeRefreshLayout) findViewById(R.id.swipeToRefreshNews);
        dateDisplay = (TextView) findViewById(R.id.news_date);
        setBackgroundByTime();

        calendar = Calendar.getInstance();
        date_format = new SimpleDateFormat("M"+"월 "+"d" +"일");
        date = date_format.format(calendar.getTime());
        dateDisplay.setText(date);

        swipeToRefreshNews.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /* 새로고침 시 수행될 코드 */
                setBackgroundByTime();

                /* 새로고침 완료 */
                swipeToRefreshNews.setRefreshing(false);
            }
        });
        recyclerView.setHasFixedSize(true);

        adapter = new ForecastAdapter(myDataset, this);
        recyclerView.setAdapter(adapter);

        // layout manager
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // 스와이프 기능 리스너.
        // 1. 모든 뉴스 아이템들을 clear.
        // 2. ForecastAdapter에게 변경사항 전달.
        // 3. getNews메서드를 다시 호출하여 피드를 새로고쳐준다.
        swipeToRefreshNews.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myDataset.clear();
                adapter.notifyDataSetChanged();
                getNews();
            }
        });

        getNews();
    }
    public void getNews(){

        try{
            URL url = new URL("https://www.yonhapnewstv.co.kr/category/news/weather/feed/"); // 연합뉴스 RSS 피드 사용(xml 문서 형식)
            RssFeedTask task = new RssFeedTask();
            task.execute(url);
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
    }

    class RssFeedTask extends AsyncTask<URL, Void, String>{

        @Override
        protected String doInBackground(URL... urls) {

            URL url = urls[0];

            try{
                InputStream is = url.openStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(is, "utf-8"); // 날씨 뉴스 xml 인코딩 방식을 utf-8 방식으로 설정
                int eventType = xpp.getEventType();

                NewsData item = null;
                String tagName = null;

                while(eventType != XmlPullParser.END_DOCUMENT){ // 반복문으로 반복되는 xml 문서의 구조 파싱
                    switch (eventType){
                        case XmlPullParser.START_DOCUMENT: // xml 문서의 시작을 알림
                            break;
                        case XmlPullParser.START_TAG:
                            tagName = xpp.getName();

                            if(tagName.equals("item")){
                                item = new NewsData();
                            }else if(tagName.equals("title")){ // xml 파일의 제목 부분 파싱
//                                Log.e("YUBIN NEWS Title", tagName);
                                xpp.next();
                                if(item != null) item.setTitle(xpp.getText());
                            }else if(tagName.equals("link")){ // xml 파일의 링크 부분 파싱
                                xpp.next();
                                if(item != null) item.setLink(xpp.getText());
                            }else if(tagName.equals("pubDate")){ // xml 파일의 업데이트 날짜 부분 파싱
                                xpp.next();
//                                Log.e("YUBIN NEWS DATE", tagName);
                                if(item != null) item.setPubDate(xpp.getText());
                            }else if(tagName.equals("description")){ // xml 파일의 설명 부분 파싱
                                xpp.next();
                                if(item != null) item.setDescription(xpp.getText());
                            }else if(tagName.equals("content:encoded")){ // xml 파일의 내용 부분 파싱
                                xpp.next();
                                if(item != null) item.setContent(xpp.getText());
                            }
                            break;
                        case XmlPullParser.TEXT:
                            break;
                        case XmlPullParser.END_TAG:
                            tagName = xpp.getName();
                            if(tagName.equals("item")){
                                myDataset.add(item);
                                item = null; // 한 개의 뉴스를 파싱 완료 후 item에 null처리를 하여 다음 item값이 들어갈 수 있도록 설정
                                publishProgress();
                            }
                            break;
                    }
                    eventType = xpp.next();
                }
            }catch(IOException | XmlPullParserException e){
                e.printStackTrace();
            }
            return ""; // 아래 onPostExecute 메소드에 전달해 줄 문자열을 반환하므로 테스트 용도로 활용했음 (파싱에는 영향 X)
                       // 리턴값이 굳이 필요하지 않아서 메서드 자체를 void형식으로 고쳐봤으나 그렇게 할 시 오류 발생
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            adapter.notifyItemInserted(myDataset.size());
        }

        @Override
        protected void onPostExecute(String s) { // 문자열 매개변수 s는 doInBackground 메소드의 리턴값
            super.onPostExecute(s);

            swipeToRefreshNews.setRefreshing(false);

            // 파싱이 정상적으로 완료되었다면 결과메세지를 아이템의 갯수와 함께 토스트 메세지로 출력(just 확인작업)

            //adapter.notifyDataSetChanged(); // 리사이클러 뷰의 아이템의 변경 여부를 어댑터에게 전달

            //Toast.makeText(NewsXMLActivity.this, s + myDataset.size(), Toast.LENGTH_SHORT).show();
        }
    }
    private void setBackgroundByTime() {
        long currentTimeMillis = System.currentTimeMillis();
        Date date = new Date(currentTimeMillis);
        SimpleDateFormat sdfHour = new SimpleDateFormat("HH");
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String hourText = sdfHour.format(date);

        String nowText = sdfNow.format(date);
        //dateNow.setText(nowText);

        int time = Integer.parseInt(hourText);
        if(time >= 0 && time < 6){
            swipeToRefreshNews.setBackgroundResource(R.drawable.sunny_night_background);
        }
        else if(time >= 6 && time < 15){
            swipeToRefreshNews.setBackgroundResource(R.drawable.sunny_afternoon_background);
        }
        else if(time >= 15 && time < 20){
            swipeToRefreshNews.setBackgroundResource(R.drawable.sunny_sunset_background);
        }
        else if(time >= 20 && time < 24){
            swipeToRefreshNews.setBackgroundResource(R.drawable.sunny_night_background);
        }
    }
}