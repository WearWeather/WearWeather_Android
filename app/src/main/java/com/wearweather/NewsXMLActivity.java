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
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<NewsData> myDataset = new ArrayList<>();
    private ForecastAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        recyclerView.setHasFixedSize(true);

        adapter = new ForecastAdapter(myDataset, this);
        recyclerView.setAdapter(adapter);

        // layout manager
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        getNews(); // xml 파싱 수행
    }
    public void getNews(){

        try{
            URL url = new URL("https://www.yonhapnewstv.co.kr/category/news/weather/feed/"); // 연합뉴스 RSS 피드 사용(xml 문서 형식)
            RssFeedTask task = new RssFeedTask(); // xml 피드를 파싱하기위해 구현된 비 동기식 RssFeedTask 클래스의 객체
            task.execute(url); //
        }catch(MalformedURLException e){
            e.printStackTrace();
        }
    }

    class RssFeedTask extends AsyncTask<URL, Void, String>{

        @Override
        protected String doInBackground(URL... urls) {

            URL url = urls[0]; // doInBackground메서드에서 받은 url들 중에 첫 번째 값을 선택

            try{
                InputStream is = url.openStream(); // 스트림 열기

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance(); // 받아온 xml피드의 인스턴스 획득
                XmlPullParser xpp = factory.newPullParser(); // xml 피드의

                xpp.setInput(is, "utf-8"); // 날씨 뉴스 xml 인코딩 방식을 utf-8로 설정
                int eventType = xpp.getEventType();

                NewsData item = null; // 한 개의 뉴스 아이템이 저장될  비어있는 item 객체
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
                            if(tagName.equals("item")){ // 마지막 태그인지 확인
                                myDataset.add(item); // NewsData클래스 ArrayList에 아이템 하나 추가
                                item = null; // 한 개의 뉴스를 파싱 완료 후 item에 null처리를 하여 다음 item값이 들어갈 수 있도록 설정
                                publishProgress(); // 간단히 아래에 정의 되어있는 onProgressUpdate 메소드 호출한다고 생각하면 됨
                            }
                            break;
                    }
                    eventType = xpp.next(); // 다음 아이템으로 커서(?) 이동
                }
            }catch(IOException | XmlPullParserException e){
                e.printStackTrace();
            }
            return ""; // 아래 onPostExecute 메소드에 전달해 줄 문자열을 반환하므로 테스트 용도로 활용했음 (파싱에는 영향 X)
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            adapter.notifyItemInserted(myDataset.size()); // 뉴스의 피드 정보가 업데이트 되는 즉시 어댑터에게 알림
        }

        @Override
        protected void onPostExecute(String s) { // 문자열 매개변수 s는 doInBackground 메소드의 리턴값
            super.onPostExecute(s);
            // 파싱이 정상적으로 완료되었다면 결과메세지를 아이템의 갯수와 함께 토스트 메세지로 출력(just 확인작업)

            //adapter.notifyDataSetChanged(); // 리사이클러 뷰의 아이템의 변경 여부를 어댑터에게 전달

            //Toast.makeText(NewsXMLActivity.this, s + myDataset.size(), Toast.LENGTH_SHORT).show();
        }
    }
}