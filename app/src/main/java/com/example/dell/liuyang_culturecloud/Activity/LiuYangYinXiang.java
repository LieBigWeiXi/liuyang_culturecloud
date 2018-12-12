package com.example.dell.liuyang_culturecloud.Activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dell.liuyang_culturecloud.Activity.Adapter.DynamicAdapter;
import com.example.dell.liuyang_culturecloud.Activity.Bean.WenTiDongTaiBean;
import com.example.dell.liuyang_culturecloud.Activity.HttpRequest.doRequest;
import com.example.dell.liuyang_culturecloud.Activity.StaticResources.NetworkInfo;
import com.example.dell.liuyang_culturecloud.R;
import com.google.gson.Gson;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class LiuYangYinXiang extends BaseActivity {
    final String URL = NetworkInfo.IP_ADDRESS+NetworkInfo.WEN_HUA_DONG_TAI;
    DynamicAdapter mDynamicAdapter;
    WenTiDongTaiBean            mWenTiDongTaiBean = new WenTiDongTaiBean();
    List<WenTiDongTaiBean.Data> mDataList         = new ArrayList<>();
    ListView mListView;
    WebView  webView;

    Handler hander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 200:
                    Gson gson = new Gson();
                    String response = (String) msg.obj;
                    Log.d("YingXiang", "handleMessage: response:"+response);
                    mWenTiDongTaiBean = gson.fromJson(response,WenTiDongTaiBean.class);
                    mDataList.addAll(mWenTiDongTaiBean.rows);
                    Log.d("YingXiang", "handleMessage:  mDataList:"+ mDataList.size());
                    mDynamicAdapter = new DynamicAdapter(LiuYangYinXiang.this,R.layout.dynamic_item_layout,
                            mDataList);
                    mDynamicAdapter.notifyDataSetChanged();
                    webView.loadUrl(mDataList.get(0).getUrl());
                    Log.d("YingXiang", "onCreate:mDynamicAdapter "+mDynamicAdapter.getCount());
                    mListView.setAdapter(mDynamicAdapter);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liu_yang_yin_xiang);

        http_request = doRequest.getInstance(getApplicationContext());
        mDoPostBean.setType(0);
        http_request.doPost(URL,mDoPostBean,hander,200);

        mListView = (ListView)findViewById(R.id.work_dynamic_listView);
        webView = (WebView)findViewById(R.id.work_dynamic_webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

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

        RefreshLayout refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new MaterialHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this).setSpinnerStyle(SpinnerStyle.Scale));

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mDataList = new ArrayList<>();
                mDoPostBean.setPage(1);
                http_request.doPost(URL,mDoPostBean,hander,200);
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if(mDataList.size()<mWenTiDongTaiBean.getTotal()){
                    mDoPostBean.setPage(mDoPostBean.getPage() + 1 );
                    http_request.doPost(URL,mDoPostBean,hander,200);
                }else {
                    Toast.makeText(LiuYangYinXiang.this,"全部加载完成",Toast.LENGTH_SHORT).show();
                }
                refreshlayout.finishLoadmore(2000);
            }
        });
    }
}
