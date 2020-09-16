package com.wearweatherapp.ui.news;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.wearweatherapp.R;

public class NewsClickViewActivity extends AppCompatActivity {

    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_click_view);

        Intent intent = getIntent();
        String link = intent.getStringExtra("Link");

        webView = (WebView) findViewById(R.id.webView_news);

        webView.getSettings().setJavaScriptEnabled(true); // 웹뷰 페이지에서 자바스크립트 기능 활성화

        webView.setWebViewClient(new WebViewClient()); // 웹뷰에서 페이지를 로드하면 안드로이드에서 자동으로 브라우저를 여는 것을 막고 웹뷰로 보여주게 함
        webView.setWebChromeClient(new WebChromeClient()); // 웹뷰 브라우저 속성을 크롬으로 지정
        webView.loadUrl(link); // NewsXMLActivity에서 intent로 전달받은 뉴스 링크 로드
    }
}