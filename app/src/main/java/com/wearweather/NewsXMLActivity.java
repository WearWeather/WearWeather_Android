package com.wearweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class NewsXMLActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    //    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<NewsData> myDataset = new ArrayList<>();
    //    private RequestQueue queue;
    private ForecastAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        adapter = new ForecastAdapter(myDataset, this);
        recyclerView.setAdapter(adapter);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        //queue = Volley.newRequestQueue(this);
        getNews();
    }
    public void getNews(){
        // Instantiate the RequestQueue. 네트워크 통신을 하기 위해서 Queue라는 녀석에 담아서 하나씩 데이터를 빼준다.

        try{
            URL url = new URL("https://www.yonhapnewstv.co.kr/category/news/weather/feed/");
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

                xpp.setInput(is, "UTF-8"); // 날씨 뉴스 xml 인코딩 방식을 utf-8 방식으로 설정
                int eventType = xpp.getEventType();

                NewsData item = null;
                String tagName = null;

                while(eventType != XmlPullParser.END_DOCUMENT){ // 반복문으로 반복되는 xml 문서의 구조 파싱
                    switch (eventType){
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            tagName = xpp.getName();

                            if(tagName.equals("item")){
                                item = new NewsData();
                            }else if(tagName.equals("title")){ // xml 파일의 제목 부분 파싱
                                xpp.next();
                                if(item != null) item.setTitle(xpp.getText());
                            }else if(tagName.equals("link")){ // xml 파일의 링크 부분 파싱
                                xpp.next();
                                if(item != null) item.setLink(xpp.getText());
                            }else if(tagName.equals("pubDate")){ // xml 파일의 업데이트 날짜 부분 파싱
                                xpp.next();
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
                                item = null;
                                publishProgress();
                            }
                            break;
                    }
                    eventType = xpp.next();
                }
            }catch(IOException | XmlPullParserException e){
                e.printStackTrace();
            }
            return "파싱종료";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            adapter.notifyItemInserted(myDataset.size());
        }

        @Override
        protected void onPostExecute(String s) { // 문자열 매개변수 s는 doInBackground 메소드의 리턴값
            super.onPostExecute(s);
            // 파싱이 정상적으로 완료되었다면 결과메세지를 아이템의 갯수와 함께 토스트 메세지로 출력(just 확인작업)

            //adapter.notifyDataSetChanged();

            Toast.makeText(NewsXMLActivity.this, s + myDataset.size(), Toast.LENGTH_SHORT).show();
        }
    }
}