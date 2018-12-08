package com.example.dell.liuyang_culturecloud.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.dell.liuyang_culturecloud.R;


/**
 * Created by DELL on 2018/7/14.
 */

public class WebActivity extends BaseActivity {
    WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_view_layout);

        webView = (WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());//目标网页仍在当前WebView中
        Intent intent = getIntent();//获取启动该活动的Intent
        String url = intent.getStringExtra("url");

        webView.loadUrl(url);
        //在webView中设置返回
        findViewById(R.id.return_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
            }
        });
    }
}