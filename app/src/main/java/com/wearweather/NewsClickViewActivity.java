package com.wearweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NewsClickViewActivity extends AppCompatActivity {

    WebView webView;
    String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_click_view);

        Intent intent = getIntent();
        link = intent.getExtras().getString("Link");

        webView = (WebView) findViewById(R.id.webView_news);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient()); // 웹뷰에서 페이지를 로드하면 안드로이드에서 자동으로 브라우저를 여는 것을 막고 웹뷰로 보여주게 함
        webView.loadUrl(link);
    }
}