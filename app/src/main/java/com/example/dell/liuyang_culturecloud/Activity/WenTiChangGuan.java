package com.example.dell.liuyang_culturecloud.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dell.liuyang_culturecloud.Activity.Adapter.GridViewAdpater;
import com.example.dell.liuyang_culturecloud.Activity.Adapter.PlaceGridViewAdpater;
import com.example.dell.liuyang_culturecloud.Activity.Bean.WenHuaPlaceBean;
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

public class WenTiChangGuan extends BaseActivity {

    final String URL = NetworkInfo.IP_ADDRESS+NetworkInfo.WEN_TI_CHANG_GUAN;

    LinearLayout wenhua,tiyu;

    GridView mGridView;

    PlaceGridViewAdpater mGridViewAdpater;

    WenHuaPlaceBean mWenHuaPlaceBean = new WenHuaPlaceBean();
    List<WenHuaPlaceBean.Data> mDataList = new ArrayList<>();

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 200:
                    Gson gson = new Gson();
                    String response = (String)msg.obj;
                    Log.d("WenHuaPlace", "handleMessage: response:"+response);
                    mWenHuaPlaceBean = gson.fromJson(response,WenHuaPlaceBean.class);
                    mDataList.addAll(mWenHuaPlaceBean.getRows());
                    mGridViewAdpater.setList(mDataList);
                    mGridViewAdpater.notifyDataSetChanged();
                    mGridView.setAdapter(mGridViewAdpater);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wen_ti_chang_guan);

        mGridViewAdpater = new PlaceGridViewAdpater(this);
        http_request.doPost(URL,mDoPostBean,mHandler,200);
        tiyu = (LinearLayout)findViewById(R.id.ti_yu_place);
        wenhua = (LinearLayout)findViewById(R.id.wen_hua_place);

        tiyu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataList = new ArrayList<>();
                mDoPostBean.setType(1);
                tiyu.setBackgroundResource(R.drawable.chg_bkg);
                wenhua.setBackgroundResource(0);
                http_request.doPost(URL,mDoPostBean,mHandler,200);
            }
        });

        wenhua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataList = new ArrayList<>();
                mDoPostBean.setType(0);
                wenhua.setBackgroundResource(R.drawable.chg_bkg);
                tiyu.setBackgroundResource(0);
                http_request.doPost(URL,mDoPostBean,mHandler,200);
            }
        });



        mGridView = (GridView)findViewById(R.id.place_gridview);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                WenHuaPlaceBean.Data data = mDataList.get(i);
                Intent intent = new Intent(WenTiChangGuan.this,ShowPlaceInfo.class);
                intent.putExtra("Place",data);
                startActivity(intent);
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
                http_request.doPost(URL,mDoPostBean,mHandler,200);
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if(mDataList.size()<mWenHuaPlaceBean.getTotal()){
                    mDoPostBean.setPage(mDoPostBean.getPage() + 1 );
                    http_request.doPost(URL,mDoPostBean,mHandler,200);
                }else {
                    Toast.makeText(WenTiChangGuan.this,"全部加载完成",Toast.LENGTH_SHORT).show();
                }
                refreshlayout.finishLoadmore(2000);
            }
        });
    }
}
