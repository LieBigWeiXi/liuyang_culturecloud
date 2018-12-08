package com.example.culturecloud.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebView;

import com.example.culturecloud.Bean.DoPostBean;
import com.example.culturecloud.Bean.WenGuangJuBean;
import com.example.culturecloud.HttpRequest.doRequest;
import com.example.culturecloud.R;
import com.example.culturecloud.StaticResources.NetworkInfo;
import com.google.gson.Gson;

public class LiuYangWenGuangJuActivity extends BaseActivity {
    WebView mWebView ;
    WenGuangJuBean liuyangWenGuangJuBean;
    doRequest http_request;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Gson gson = new Gson();
            switch (msg.what){
                case 200:
                    String response = (String)msg.obj;
                    Log.d("LiuYangWenGuangJu", "handleMessage: situation"+response);
                    liuyangWenGuangJuBean = gson.fromJson(response,WenGuangJuBean.class);
                    String WEBVIEW_CONTENT = "<html><head></head><body style=\"text-align:justify;margin:20px;font-size:20px;text-indent:2em\">%s</body></html>";
                    mWebView.loadData(String.format(WEBVIEW_CONTENT,liuyangWenGuangJuBean.getSituation()),"text/html;charset=UTF-8",null);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liu_yang_wen_guang_ju);
        liuyangWenGuangJuBean = new WenGuangJuBean();
        mWebView = (WebView)findViewById(R.id.wenguangju_webview);
        mWebView.setBackgroundColor(0);
        String url = NetworkInfo.NEW_IP_ADDRESS+NetworkInfo.WEN_GUANG_JU;
        DoPostBean doPostBean = new DoPostBean();
        http_request = doRequest.getInstance(this);
        http_request.doPost(url,doPostBean,handler,200);
    }
}
