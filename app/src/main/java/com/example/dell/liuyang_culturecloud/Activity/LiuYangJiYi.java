package com.example.dell.liuyang_culturecloud.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.dell.liuyang_culturecloud.Activity.Adapter.MemoryAdapter;
import com.example.dell.liuyang_culturecloud.Activity.Bean.MemoryBean;
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

/**
 * Created by DELL on 2018/12/2.
 */

public class LiuYangJiYi extends BaseActivity {
    private MemoryBean                mMemoryBean      = new MemoryBean();
    private List<MemoryBean.Pictures> picturesBeanList = new ArrayList<>();
    private GridView      oldpic_gridview;
    private RefreshLayout refreshLayout;
    private MemoryAdapter mMemoryAdapter;

    final String URL = NetworkInfo.IP_ADDRESS+ NetworkInfo.LIU_YANG_JI_YI;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Gson gson = new Gson();
            switch (msg.what){
                case 500://加载右侧图片
                    String picturesData = (String)msg.obj;
                    Log.d("",picturesData);
                    try {
                        mMemoryBean = gson.fromJson(picturesData,MemoryBean.class);
                        Log.d("all", String.valueOf(mMemoryBean.getTotal()));
                        picturesBeanList.addAll(mMemoryBean.getRows());
                        Log.d("size", String.valueOf(picturesBeanList.size()));
                        mMemoryAdapter.setArrayList(picturesBeanList);
                        mMemoryAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }

        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liuyangjiyi_layout);
        refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
        picturesBeanList = new ArrayList<>();
        mDoPostBean.setRows(10);
        mDoPostBean.setPage(1);
        //发起网络
        http_request.doPost(URL,mDoPostBean,handler,500);

        oldpic_gridview = (GridView)findViewById(R.id.oldpic_gridview);
        mMemoryAdapter = new MemoryAdapter(this);
        oldpic_gridview.setAdapter(mMemoryAdapter);
        oldpic_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MemoryBean.Pictures pictures = picturesBeanList.get(i);
                Intent intent = new Intent(LiuYangJiYi.this,SceneryLoadActivity.class);
                intent.putExtra("picture",pictures);
                startActivity(intent);
            }
        });
        refreshLayout.setRefreshHeader(new MaterialHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this).setSpinnerStyle(SpinnerStyle.Scale));
        //刷新加载
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                picturesBeanList = new ArrayList<>();
                mMemoryAdapter.clear();
                mDoPostBean.setRows(10);
                mDoPostBean.setPage(1);
                http_request.doPost(URL,mDoPostBean,handler,500);
                refreshlayout.finishRefresh(2000);
            }
        });
        //加载更多
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if(picturesBeanList.size()<mMemoryBean.getTotal()){
                    mDoPostBean.setRows(10);
                    mDoPostBean.setPage(mDoPostBean.getPage()+1);
                    http_request.doPost(URL,mDoPostBean,handler,500);
                    refreshlayout.finishLoadmore(2000);
                }else{
                    Toast.makeText(LiuYangJiYi.this,"加载完毕", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
