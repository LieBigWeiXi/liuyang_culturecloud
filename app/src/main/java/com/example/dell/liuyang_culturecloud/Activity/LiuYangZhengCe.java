package com.example.dell.liuyang_culturecloud.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.dell.liuyang_culturecloud.Activity.Adapter.PolicyAdapter;
import com.example.dell.liuyang_culturecloud.Activity.Bean.DoPostBean;
import com.example.dell.liuyang_culturecloud.Activity.Bean.PolicyBean;
import com.example.dell.liuyang_culturecloud.Activity.HttpRequest.doRequest;
import com.example.dell.liuyang_culturecloud.Activity.StaticResources.NetworkInfo;
import com.example.dell.liuyang_culturecloud.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/12/2.
 */

public class LiuYangZhengCe extends BaseActivity {
    private WebView   policy_wv;
    private List<PolicyBean.policy> mPolicys = new ArrayList<>();
    private ListView mListView;
    String  url     = NetworkInfo.IP_ADDRESS+ NetworkInfo.LIU_YANG_ZHENG_CE;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 200:
                    //处理服务器请求数据
                    String response = (String)msg.obj;
                    Gson gson = new Gson();
                    PolicyBean policyBean = gson.fromJson(response,PolicyBean.class);
                    Log.d("LiuYangZhengCe", "handleMessage: policy_count"+policyBean.total);
                    mPolicys.addAll(policyBean.rows);
                    PolicyAdapter policyAdapter = new PolicyAdapter(LiuYangZhengCe.this,
                            R.layout.policy_item_layout,mPolicys);
                    Log.d("count", "onCreate: "+ String.valueOf(policyAdapter.getCount()));
                    mListView.setAdapter(policyAdapter);
                    policy_wv.loadUrl(mPolicys.get(0).getUrl());
                    policyAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liuyangzhengce_layout);
        policy_wv = (WebView)findViewById(R.id.zhengce_webview);
        policy_wv.getSettings().setJavaScriptEnabled(true);
        policy_wv.setWebViewClient(new WebViewClient());

        mDoPostBean.setRows(50);
        http_request = doRequest.getInstance(getApplicationContext());
        http_request.doPost(url,mDoPostBean,handler,200);

        mListView = (ListView)findViewById(R.id.zhengce_listView);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PolicyBean.policy policy = mPolicys.get(i);
               /* Toast.makeText(LiuYangZhengCe.this,policy.getName(),
                        Toast.LENGTH_SHORT).show();*/
                //增加点击事件逻辑
                policy_wv.loadUrl(policy.getUrl());
            }
        });

    }
}
