package com.example.dell.liuyang_culturecloud.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.liuyang_culturecloud.Activity.Adapter.TuShuAdapter;
import com.example.dell.liuyang_culturecloud.Activity.Adapter.TuShuRecAdapter;
import com.example.dell.liuyang_culturecloud.Activity.Adapter.TushuTypeBeanAdapter;
import com.example.dell.liuyang_culturecloud.Activity.Adapter.TypeBeanAdapter;
import com.example.dell.liuyang_culturecloud.Activity.BaseActivity;
import com.example.dell.liuyang_culturecloud.Activity.Bean.DoPostBean;
import com.example.dell.liuyang_culturecloud.Activity.Bean.LibBean;
import com.example.dell.liuyang_culturecloud.Activity.Bean.TypeBean;
import com.example.dell.liuyang_culturecloud.Activity.HttpRequest.doRequest;
import com.example.dell.liuyang_culturecloud.Activity.StaticResources.NetworkInfo;
import com.example.dell.liuyang_culturecloud.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ShuXiangLiuYang extends BaseActivity {
    List<TypeBean> mTypeBeans = new ArrayList<>();
    ListView             typeListView;
    TushuTypeBeanAdapter typeBeanAdapter;
    LibBean           mLibBean   = new LibBean();
    List<LibBean.Lib> mLibs      = new ArrayList<>();
    WebView mWebView;
    TuShuAdapter recyclerAdpter;
    TuShuRecAdapter mTuShuRecAdapter;
    String URL = NetworkInfo.IP_ADDRESS+ NetworkInfo.SHU_XIANG_LIU_YANG;
    ImageView    mImageView;
    RecyclerView mRecyclerView;
    TextView     mTextView ;

    final String css_style = "<html><head><style> body{" +
            "text-align:justify;" +
            "margin:12px;" +
            "font-size:10px;" +
            "color:#ffffff;"+
            "text-indent:2em;}</style></head><body>%s</body></html>";
    final String css_style1 = "<html><head></head><body style:\" text-align:justify;" +
            "margin:12px;font-size:10px;color:#ffffff;text-indent:2em;\">%s</body>";
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Gson gson = new Gson();
            switch (msg.what){
                case 200:
                    String response = (String)msg.obj;
                    Log.d("tushu", "handleMessage: tushu"+response);
                    mLibBean = gson.fromJson(response,LibBean.class);
                    Log.d("图书", "handleMessage: LibInfoSize"+mLibBean.getTotal());
                    mLibs.addAll(mLibBean.getRows());
                    String data_html = "<div style=\"color:#ffffff\">" +mLibBean.getRows().get(0).getInfo() +"</div>";
                    mWebView.loadData(data_html,
                            "text/html;charset=UTF-8", null);
                    mWebView.loadData(data_html,
                            "text/html;charset=UTF-8", null);
                    Log.d("123", "handleMessage: after"+mLibs.get(0).getInfo());
                    Picasso.with(ShuXiangLiuYang.this)
                        .load(NetworkInfo.IP_ADDRESS+"/media/"+mLibs.get(0).getCover()).into(mImageView);

                    Log.d("picture", String.valueOf(mLibs.get(0).getCarousel().size()));
                    //mTextView.setText(mLibs.get(0).getName());
                    mTuShuRecAdapter.setDataList(mLibs);
                    mTuShuRecAdapter.notifyDataSetChanged();

                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shu_xiang_liu_yang);
        Button return_btn = (Button)findViewById(R.id.return_button);
        return_btn.setBackgroundResource(R.mipmap.tushu_return_btn);
        typeListView = (ListView) (findViewById(R.id.tushu_listview));
        mTextView = (TextView)findViewById(R.id.tushu_text) ;
        mTextView.setTextColor(Color.WHITE);
        mWebView = (WebView)(findViewById(R.id.tushu_webView));
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setBackgroundColor(0);
        initType();
        http_request = doRequest.getInstance(getApplicationContext());
        http_request.doPost(URL,mDoPostBean,handler,200);
        typeBeanAdapter = new TushuTypeBeanAdapter(this, R.layout.left_type_item_tushu,
                mTypeBeans);
        typeListView.setAdapter(typeBeanAdapter);
        typeBeanAdapter.notifyDataSetChanged();

        typeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //请求数据
               /* Toast.makeText(ShuXiangLiuYang.this, "正在请求数据……",
                Toast.LENGTH_SHORT).show();*/
                TypeBean typeBean = mTypeBeans.get(i);
                mLibs = new ArrayList<>();
                if(typeBean.getDt_name().equals("自助图书馆")){
                    mDoPostBean.setType(i-1);
                }else if(typeBean.getDt_name().equals("图书总分馆")){
                    mDoPostBean.setType(i+1);
                }else{
                    mDoPostBean.setPage(1);
                    mDoPostBean.setRows(20);
                    mDoPostBean.setType(i);
                }
                typeBeanAdapter.setSelected_id(typeBean.getId());
                typeBeanAdapter.notifyDataSetChanged();
                http_request.doPost(URL,mDoPostBean,handler,200);
            }
        });

        mRecyclerView = (RecyclerView)findViewById(R.id.cul_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mTuShuRecAdapter = new TuShuRecAdapter(ShuXiangLiuYang.this,
                new TuShuRecAdapter.OnClickCallBack() {
                    @Override
                    public void OnClink(int id) {
                        Picasso.with(ShuXiangLiuYang.this)
                                .load(NetworkInfo.IP_ADDRESS+"/media/"+mLibs.get(id).getCover())
                                .into(mImageView);
                        //mTextView.setText(mLibs.get(id).getInfo());
                        String data_html = "<div style=\"color:#ffffff\">" +mLibs.get(id).getInfo() +"</div>";
                        mWebView.loadData(data_html,
                                "text/html;charset=UTF-8",null);
                        mWebView.loadData(data_html,
                                "text/html;charset=UTF-8",null);
                    }
                },1000);

       /* recyclerAdpter = new TuShuAdapter(ShuXiangLiuYang.this,
                new TuShuAdapter.OnClickCallBack(){
                    @Override
                    public void OnClink(int id) {
                        //替换图
                        Picasso.with(ShuXiangLiuYang.this)
                                .load(NetworkInfo.IP_ADDRESS+"/media/"+mLibs.get(0).getCarousel().get(id))
                                .into(mImageView);
                    }
                },1000);*/
        mRecyclerView.setAdapter(mTuShuRecAdapter);

        mImageView = (ImageView)findViewById(R.id.big_pic);


    }

    private void initType(){
        TypeBean typeBean;
        String[]type_names = {"图书馆","图书新馆","农家书屋","图书总分馆","自助图书馆","阅读推广"};
        for(int i = 0;i<type_names.length;i++){
            typeBean = new TypeBean();
            typeBean.setId(i);
            typeBean.setDt_name(type_names[i]);
            typeBean.setDt_icon(R.drawable.all);
            mTypeBeans.add(typeBean);
        }

        for(TypeBean typeBean1 : mTypeBeans){
            Log.d("WenHua", "initType: type_name"+typeBean1.getDt_name());
        }
    }
}
