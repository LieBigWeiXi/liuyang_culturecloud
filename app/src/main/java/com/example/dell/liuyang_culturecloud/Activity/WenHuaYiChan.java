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
import android.widget.ListView;
import android.widget.Toast;

import com.example.dell.liuyang_culturecloud.Activity.Adapter.GridViewAdpater;
import com.example.dell.liuyang_culturecloud.Activity.Adapter.TypeBeanAdapter;
import com.example.dell.liuyang_culturecloud.Activity.Bean.TypeBean;
import com.example.dell.liuyang_culturecloud.Activity.Bean.WenHuaYiChanBean;
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

public class WenHuaYiChan extends BaseActivity {

    List<TypeBean> mTypeBeans = new ArrayList<>();
    ListView typeListView ;
    TypeBeanAdapter typeAdapter;
    WenHuaYiChanBean mWenHuaYiChanBean = new WenHuaYiChanBean();
    List<WenHuaYiChanBean.Data> mDataList = new ArrayList<>();
    GridView mGridView;
    GridViewAdpater mGridViewAdpater;
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
        setContentView(R.layout.wen_hua_yi_chan);
        typeListView = (ListView)findViewById(R.id.wenhuayichan_listview);
        initType();
        typeAdapter = new TypeBeanAdapter(this,R.layout.hesitate_left_type_item,mTypeBeans);
        typeListView.setAdapter(typeAdapter);

        http_request.doPost(URL,mDoPostBean,handler,200);

        typeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mDataList = new ArrayList<>();
                mDoPostBean.setType(mTypeBeans.get(i).getId());
                mDoPostBean.setPage(1);
                typeAdapter.setSelected_id(i);
                typeAdapter.notifyDataSetChanged();
                http_request.doPost(URL,mDoPostBean,handler,200);
            }
        });
        mGridView = (GridView) (findViewById(R.id.wenhuayichan_gridview));
        mGridViewAdpater = new GridViewAdpater(this);
        mGridView.setAdapter(mGridViewAdpater);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                WenHuaYiChanBean.Data hesitateBean = new WenHuaYiChanBean.Data();
                hesitateBean = mDataList.get(i);
                Intent intent;
                if(hesitateBean.getType()==2){
                    intent = new Intent(WenHuaYiChan.this,LiuYangFame.class);
                    intent.putExtra("whyc_key",hesitateBean);
                    startActivity(intent);
                }else{
                    //增加新的页面
                    intent = new Intent(WenHuaYiChan.this,ShowMoreInfo.class);
                    intent.putExtra("whyc_key",hesitateBean);
                    startActivity(intent);
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
                http_request.doPost(URL,mDoPostBean,handler,200);
                refreshlayout.finishRefresh(2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if(mDataList.size()<mWenHuaYiChanBean.total){
                    mDoPostBean.setPage(mDoPostBean.getPage() + 1 );
                    http_request.doPost(URL,mDoPostBean,handler,200);
                }else {
                    Toast.makeText(WenHuaYiChan.this,"全部加载完成",Toast.LENGTH_SHORT).show();
                }
                refreshlayout.finishLoadmore(2000);
            }
        });


    }
    private void initType(){
        TypeBean typeBean;
        String[]type_names = {"文物保护","非遗保护","浏阳名人"};
        for(int i = 0;i<type_names.length;i++){
            typeBean = new TypeBean();
            typeBean.setId(i);
            typeBean.setDt_name(type_names[i]);
            typeBean.setDt_icon(R.drawable.all);
            mTypeBeans.add(typeBean);
        }
    }
}
