package com.ibt.nirmal.fpproandroid_source.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

import com.ibt.nirmal.fpproandroid_source.R;

public class info_activity extends AppCompatActivity
{
    private WebView mWebview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_layout);

        mWebview = (WebView)findViewById(R.id.info_webview);
        mWebview.loadUrl("https://www.stoneproductions.com.au/FatiguePredictor2/info.html");
    }
}
