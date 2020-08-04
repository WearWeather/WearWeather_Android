package com.wearweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class NewsXMLActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<NewsData> myDataset;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)
        queue = Volley.newRequestQueue(this);
        getNews();
    }
    public void getNews(){
        // Instantiate the RequestQueue. 네트워크 통신을 하기 위해서 Queue라는 녀석에 담아서 하나씩 데이터를 빼준다.
        String url =" https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.yonhapnewstv.co.kr%2Fcategory%2Fnews%2Fweather%2Ffeed%2F";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Log.d("NEWS", response);
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            JSONArray arrayArticles = jsonObj.getJSONArray("items");

                            List<NewsData> news = new ArrayList<>();

                            for(int i = 0, j = arrayArticles.length(); i < j; i++){
                                JSONObject obj = arrayArticles.getJSONObject(i);
                                Log.d("NEWS", obj.toString());
                                NewsData newsData = new NewsData();
                                newsData.setTitle(obj.getString("title"));
                                newsData.setUrlToImage(obj.getString("link"));
                                newsData.setContent(obj.getString("description"));
                                news.add(newsData);
                            }
                            mAdapter = new ForecastAdapter(news, NewsXMLActivity.this, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(v.getTag() != null){
                                        int position = (int)v.getTag();
                                        ((ForecastAdapter)mAdapter).getNews(position).getContent();
                                        Intent intent = new Intent(); // 각 리사이클러 뷰를 클릭하면 다른 액티비티에서 뉴스 세부 요소를 제공해주는
                                        // 기능을 구현하려 하였으나 아직까지 고민 중...
                                        intent.putExtra("news", ((ForecastAdapter)mAdapter).getNews(position) );
                                        startActivity(intent);
                                    }
                                }
                            });
                            recyclerView.setAdapter(mAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}