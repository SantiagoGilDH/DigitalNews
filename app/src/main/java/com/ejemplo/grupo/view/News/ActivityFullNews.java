package com.ejemplo.grupo.view.News;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.ejemplo.grupo.R;

public class ActivityFullNews extends AppCompatActivity {

    private WebView webView;
    private String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_news_layout);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        link = bundle.getString(FragmentNewsList.LINK);

        webView = (WebView) findViewById(R.id.WebViewFullNews);
        webView.loadUrl(link);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

    }
}
