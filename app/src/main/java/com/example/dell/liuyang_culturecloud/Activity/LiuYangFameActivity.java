package com.example.dell.liuyang_culturecloud.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.liuyang_culturecloud.Activity.Adapter.GridViewAdpater;
import com.example.dell.liuyang_culturecloud.Activity.Bean.WenHuaYiChanBean;
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

public class LiuYangFameActivity extends BaseActivity {

    WenHuaYiChanBean            mWenHuaYiChanBean = new WenHuaYiChanBean();
    List<WenHuaYiChanBean.Data> mDataList         = new ArrayList<>();
    GridView        mGridView;
    GridViewAdpater mGridViewAdpater;
    TextView        now_page,total_page;

    final String URL = NetworkInfo.IP_ADDRESS+NetworkInfo.WEN_HUA_YI_CHAN_URL;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Gson gson = new Gson();
            switch (msg.what){
                case 200:
                    //解析返回的数据
                    String response = (String)msg.obj;
                    Log.d("WenHuaYiChan", "handleMessage: 200 response :"+ response );
                    mWenHuaYiChanBean = gson.fromJson(response,WenHuaYiChanBean.class);
                    String totalPage = String.valueOf(mWenHuaYiChanBean.total/mDoPostBean.getRows()+1);
                    total_page.setText(totalPage);
                    mDataList.addAll(mWenHuaYiChanBean.getRows());
                    Log.d("WenHuaYiChan", "handleMessage: 200 datasize :"+ mDataList.size());
                    mGridViewAdpater.setWHYCList(mDataList);
                    mGridViewAdpater.notifyDataSetChanged();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liu_yang_fame);
        mGridView = (GridView)findViewById(R.id.fame_gridview);
        now_page = (TextView)findViewById(R.id.now_page);
        total_page = (TextView)findViewById(R.id.total_page);
        http_request  = doRequest.getInstance(getApplicationContext());
        mDoPostBean.setRows(10);
        mDoPostBean.setType(2);
        http_request.doPost(URL,mDoPostBean,handler,200);

        mGridViewAdpater = new GridViewAdpater(this);
        mGridView.setAdapter(mGridViewAdpater);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                WenHuaYiChanBean.Data hesitateBean = new WenHuaYiChanBean.Data();
                hesitateBean = mDataList.get(i);
                Intent intent;
                intent = new Intent(LiuYangFameActivity.this,LiuYangFame.class);
                intent.putExtra("whyc_key",hesitateBean);
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
                http_request.doPost(URL,mDoPostBean,handler,200);
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if(mDataList.size()<mWenHuaYiChanBean.total){
                    mDoPostBean.setPage(mDoPostBean.getPage() + 1 );
                    now_page.setText(String.valueOf(mDoPostBean.getPage()));
                    http_request.doPost(URL,mDoPostBean,handler,200);
                }else {
                    Toast.makeText(LiuYangFameActivity.this,"全部加载完成",Toast.LENGTH_SHORT).show();
                }
                refreshlayout.finishLoadmore(2000);
            }
        });
    }
}
