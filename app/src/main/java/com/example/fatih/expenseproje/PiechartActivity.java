package com.example.fatih.expenseproje;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.JavascriptInterface;

@SuppressLint("SetJavaScriptEnabled")
public class PiechartActivity extends AppCompatActivity {
    WebView webView;
    int gelir,gider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechart);
        Intent intent=getIntent();
        gelir=intent.getIntExtra("GELIR",0);
        gider=intent.getIntExtra("GIDER",0);
        webView=(WebView)findViewById(R.id.web);
        webView.addJavascriptInterface(new WebAppInterface(),"Android");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/charts.html");



    }
    public class WebAppInterface {

        @JavascriptInterface
        public int getGelir() {
            return gelir;
        }

        @JavascriptInterface
        public int getGider() {
            return gider;
        }
    }
}
