package com.wearweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

public class ClothesClickWebView extends AppCompatActivity {

    WebView webView;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_click_web_view);

        button = findViewById(R.id.button);
        webView = findViewById(R.id.webView_shoppingMall);
        
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.setWebViewClient(new WebViewClient());
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://store.musinsa.com/app/product/detail/859061/0"));
                startActivity(intent);
            }
        });
    }
}