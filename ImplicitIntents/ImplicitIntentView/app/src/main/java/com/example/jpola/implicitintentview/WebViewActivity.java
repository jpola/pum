package com.example.jpola.implicitintentview;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewActivity extends AppCompatActivity {

    WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();

        String url = "http://ift.uni.wroc.pl";
        if (intent.hasExtra(Intent.EXTRA_TEXT)) {

            String address = intent.getStringExtra(Intent.EXTRA_TEXT);
            if (address != null) {
                url = address;
            }

            Log.d("WebView Activity", "has url: " + url);
        }

        mWebView = (WebView) findViewById(R.id.web_view);


        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient());

        WebSettings settings = mWebView.getSettings();
        settings.setSaveFormData(true);
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(false);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setSupportMultipleWindows(false);

        mWebView.loadUrl(url);


    }
}
