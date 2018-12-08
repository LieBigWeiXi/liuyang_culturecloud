package com.example.dell.liuyang_culturecloud.Activity;

import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.dell.liuyang_culturecloud.Activity.Adapter.DynamicAdapter;
import com.example.dell.liuyang_culturecloud.Activity.Bean.WenTiDongTaiBean;
import com.example.dell.liuyang_culturecloud.Activity.StaticResources.NetworkInfo;
import com.example.dell.liuyang_culturecloud.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class WenTiDongTai extends BaseActivity implements View.OnClickListener{

    final String URL = NetworkInfo.IP_ADDRESS+NetworkInfo.WEN_HUA_DONG_TAI;
    LinearLayout layout1,layout2,layout3;
    RelativeLayout mLinearLayout;
    DynamicAdapter mDynamicAdapter;
    WenTiDongTaiBean mWenTiDongTaiBean = new WenTiDongTaiBean();
    List<WenTiDongTaiBean.Data> mDataList = new ArrayList<>();
    ListView mListView;
    WebView webView;
    ImageView mImageView;

    Handler hander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 200:
                    Gson gson = new Gson();
                    String response = (String) msg.obj;
                    Log.d("WenTiDongTai", "handleMessage: response:"+response);
                    mWenTiDongTaiBean = gson.fromJson(response,WenTiDongTaiBean.class);
                    mDataList.addAll(mWenTiDongTaiBean.rows);
                    Log.d("WenTiDongTai", "handleMessage:  mDataList:"+ mDataList.size());
                    mDynamicAdapter = new DynamicAdapter(WenTiDongTai.this,R.layout.dynamic_item_layout,
                            mDataList);
                    mDynamicAdapter.notifyDataSetChanged();
                    webView.loadUrl(mDataList.get(0).getUrl());
                    Log.d("WenTi", "onCreate:mDynamicAdapter "+mDynamicAdapter.getCount());
                    mListView.setAdapter(mDynamicAdapter);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wen_ti_dong_tai);
        http_request.doPost(URL,mDoPostBean,hander,200);
        layout1 = (LinearLayout)findViewById(R.id.work_dynamic);
        layout2 = (LinearLayout)findViewById(R.id.famer_activity);
        layout3 = (LinearLayout)findViewById(R.id.people_things);
        mLinearLayout = (RelativeLayout) findViewById(R.id.first_layout);
        mImageView = (ImageView)findViewById(R.id.people_things_img);
        mListView = (ListView)findViewById(R.id.work_dynamic_listView);
        webView = (WebView)findViewById(R.id.work_dynamic_webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        webView.setInitialScale(50);//缩小50%
        layout1.setOnClickListener(this);
        layout2.setOnClickListener(this);
        layout3.setOnClickListener(this);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(mDataList.get(i).getUrl()!=null){
                    webView.loadUrl(mDataList.get(i).getUrl());
                }else{
                    final String css_style =  "<html><head></head><body style=\"" +
                            "text-align:justify;" +
                            "margin:20px;" +
                            "font-size:12px;" +
                            "text-indent:2em\">%s</body></html>";;
                    webView.loadDataWithBaseURL(NetworkInfo.IP_ADDRESS,
                            String.format(css_style,mDataList.get(i).getInfo()),
                            "text/html;charset=UTF-8",null,null);
                }

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.work_dynamic:
                mDoPostBean.setType(3);
                findViewById(R.id.work_dynamic).setBackgroundResource(R.drawable.chg_bkg);
                findViewById(R.id.famer_activity).setBackgroundResource(0);
                findViewById(R.id.people_things).setBackgroundResource(0);
                mDataList = new ArrayList<>();
                http_request.doPost(URL,mDoPostBean,hander,200);
                mImageView.setVisibility(View.GONE);
                mLinearLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.famer_activity:
                mDoPostBean.setType(0);
                findViewById(R.id.famer_activity).setBackgroundResource(R.drawable.chg_bkg);
                findViewById(R.id.work_dynamic).setBackgroundResource(0);
                findViewById(R.id.people_things).setBackgroundResource(0);
                mDataList = new ArrayList<>();
                http_request.doPost(URL,mDoPostBean,hander,200);
                mLinearLayout.setVisibility(View.VISIBLE);
                mImageView.setVisibility(View.GONE);
                break;
            case R.id.people_things:
                /*mDoPostBean.setType(2);
                mDataList = new ArrayList<>();
                http_request.doPost(URL,mDoPostBean,hander,200);*/
                findViewById(R.id.people_things).setBackgroundResource(R.drawable.chg_bkg);
                findViewById(R.id.famer_activity).setBackgroundResource(0);
                findViewById(R.id.work_dynamic).setBackgroundResource(0);
                mImageView.setVisibility(View.VISIBLE);
                mLinearLayout.setVisibility(View.GONE);
                mImageView.setImageResource(R.drawable.people_things);
                break;
        }
    }
}
