package com.example.dell.liuyang_culturecloud.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebView;

import com.example.dell.liuyang_culturecloud.Activity.Bean.DoPostBean;
import com.example.dell.liuyang_culturecloud.Activity.Bean.WenGuangJuBean;
import com.example.dell.liuyang_culturecloud.Activity.HttpRequest.doRequest;
import com.example.dell.liuyang_culturecloud.Activity.StaticResources.NetworkInfo;
import com.example.dell.liuyang_culturecloud.R;
import com.google.gson.Gson;

public class LiuYangWenGuangJuActivity extends BaseActivity {
    WebView        mWebView ;
    WenGuangJuBean liuyangWenGuangJuBean;
    String WEBVIEW_CONTENT = "<html><head></head><body style=\"" +
            "text-align:justify;" +
            "margin:10px;" +
            "font-size:10px;" +
            "text-indent:2em\">%s</body></html>";
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
                    mWebView.loadDataWithBaseURL(NetworkInfo.IP_ADDRESS,
                            String.format(WEBVIEW_CONTENT,liuyangWenGuangJuBean.getSituation()),
                            "text/html;charset=UTF-8",
                            null,null);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wen_guang_ju);
        liuyangWenGuangJuBean = new WenGuangJuBean();
        mWebView = (WebView)findViewById(R.id.wenguangju_webview);
        mWebView.setBackgroundColor(0);
        String url = NetworkInfo.IP_ADDRESS+ NetworkInfo.WEN_GUANG_JU;
        http_request = doRequest.getInstance(getApplicationContext());
        http_request.doPost(url,mDoPostBean,handler,200);
    }
}
