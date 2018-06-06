package com.example.lenovo.string_color;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Created by Lenovo on 08.04.2018.
 */

public class WebViewActivity extends AppCompatActivity {
    WebView myWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_layout);
        myWebView = (WebView) findViewById(R.id.web_view);
        myWebView.loadUrl("http://www.wykop.pl/ramka/3313015/efekt-stroopa-ciekawy-eksperyment-poznawczy-sprawdz-swoja-zdolnosc/");
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
