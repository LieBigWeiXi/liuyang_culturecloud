package com.example.dell.liuyang_culturecloud.Activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dell.liuyang_culturecloud.Activity.Adapter.MarketRcyAdapter;
import com.example.dell.liuyang_culturecloud.Activity.Adapter.TypeBeanAdapter;
import com.example.dell.liuyang_culturecloud.Activity.Bean.DoPostBean;
import com.example.dell.liuyang_culturecloud.Activity.Bean.TypeBean;
import com.example.dell.liuyang_culturecloud.Activity.Bean.WenHuaShiChangBean;
import com.example.dell.liuyang_culturecloud.Activity.HttpRequest.doRequest;
import com.example.dell.liuyang_culturecloud.Activity.StaticResources.NetworkInfo;
import com.example.dell.liuyang_culturecloud.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class WenHuaShiChang extends BaseActivity {

    ListView typeListView;
    List<TypeBean> mTypeBeans = new ArrayList<>();
    TypeBeanAdapter typeBeanAdapter;
    List<WenHuaShiChangBean.WHSC> mWHSCList           = new ArrayList<>();
    WenHuaShiChangBean            mWenHuaShiChangBean = new WenHuaShiChangBean();
    TextView sc_title;

    final  String URL = NetworkInfo.IP_ADDRESS + NetworkInfo.WEN_HUA_SHI_CHANG;


   /* final String WEBVIEW_CONTENT = "<html><head><style> body{" +
            "text-align:justify;" +
            "margin:12px;" +
            "font-size:14px;" +
            "text-indent:2em;}</style></head><body>%s</body></html>";*/
    String WEBVIEW_CONTENT = "<html><head></head><body style=\"" +
            "text-align:justify;" +
            "margin:10px;" +
            "font-size:40px;" +
            "text-indent:2em\">%s</body></html>";

    MarketRcyAdapter mMarketRcyAdapter;
    RecyclerView mRecyclerView;
    WebView      mWebView;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 200://处理返回的数据
                    String response = (String)msg.obj;
                    Log.d("WenHuaShiChang", "handleMessage: 400 responseData"+response);
                    Gson gson = new Gson();
                    mWenHuaShiChangBean = gson.fromJson(response, WenHuaShiChangBean.class);
                    mWHSCList.addAll(mWenHuaShiChangBean.getRows());
                    mWebView.loadDataWithBaseURL(NetworkInfo.IP_ADDRESS,
                            String.format(WEBVIEW_CONTENT,mWHSCList.get(0).getInfo())
                            ,"text/html;charset=UTF-8",null,null);
                    mWebView.loadDataWithBaseURL(NetworkInfo.IP_ADDRESS,
                            String.format(WEBVIEW_CONTENT,mWHSCList.get(0).getInfo())
                            ,"text/html;charset=UTF-8",null,null);
                    sc_title.setText(mWHSCList.get(0).getName());
                    mMarketRcyAdapter.setWHSC_DataList(mWHSCList);
                    mMarketRcyAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wen_hua_shi_chang);
        sc_title = (TextView)findViewById(R.id.sc_title);
        typeListView = (ListView) (findViewById(R.id.wenhuashichang_listview));
        initType();
        typeBeanAdapter = new TypeBeanAdapter(this, R.layout.shichang_left_type_item,
                mTypeBeans);
        typeBeanAdapter.notifyDataSetChanged();
        typeListView.setAdapter(typeBeanAdapter);
        mDoPostBean.setType(2);
        //mDoPostBean.setRows(50);
        http_request = doRequest.getInstance(getApplicationContext());
        http_request.doPost(URL,mDoPostBean,handler,200);
        mWebView = (WebView)findViewById(R.id.news_wv);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setBackgroundColor(0);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
        mWebView.setInitialScale(50);//缩小50%

        mRecyclerView = (RecyclerView)findViewById(R.id.cul_list);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(layoutManager);
        int mScreenWidth = getResources().getDisplayMetrics().widthPixels - dp2px(510);
        Log.d("SHI_CHANG", "onCreate: mScreenWidth"+mScreenWidth);
        mMarketRcyAdapter = new MarketRcyAdapter(this, handler, new MarketRcyAdapter.OnClickCallBack() {
            @Override
            public void OnClink(int id) {
                //now_position = id;
                mWebView.loadDataWithBaseURL(NetworkInfo.IP_ADDRESS,
                        String.format(WEBVIEW_CONTENT,mWHSCList.get(id).getInfo()),
                        "text/html;charset=UTF-8",null,null);
                mWebView.loadDataWithBaseURL(NetworkInfo.IP_ADDRESS,
                        String.format(WEBVIEW_CONTENT,mWHSCList.get(id).getInfo()),
                        "text/html;charset=UTF-8",null,null);
                sc_title.setText(mWHSCList.get(id).getName());
            }
        },mScreenWidth);
        mRecyclerView.setAdapter(mMarketRcyAdapter);
        mRecyclerView.addOnScrollListener(mOnScrollListener);

        typeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TypeBean typeBean = mTypeBeans.get(i);
                mWHSCList = new ArrayList<>();
                WebView url_webview = (WebView)findViewById(R.id.url_web_view);;
                if(typeBean.getDt_name().equals("行政许可")){
                    //挂链接
                    String url = "http://zwfw.hunan.gov.cn/hnvirtualhall/serviceguide/jsp/serviceapprovegr.jsp?type=xndtbm&areacode=430181999000&main=1";

                    url_webview.getSettings().setJavaScriptEnabled(true);
                    url_webview.setBackgroundColor(0);
                    url_webview.setWebViewClient(new WebViewClient());
                    url_webview.getSettings().setUserAgentString("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
                    url_webview.setInitialScale(75);//缩

                    url_webview.loadUrl(url);
                    url_webview.setVisibility(View.VISIBLE);
                    findViewById(R.id.wenhualiuyang_layout).setVisibility(View.INVISIBLE);
                    //mRecyclerView.setVisibility(View.GONE);
                    typeBeanAdapter.setSelected_id(0);
                    typeBeanAdapter.notifyDataSetChanged();
                }else {
                    //mRecyclerView.setVisibility(View.VISIBLE);
                    findViewById(R.id.wenhualiuyang_layout).setVisibility(View.VISIBLE);
                    url_webview.setVisibility(View.GONE);
                    mDoPostBean.setPage(1);
                    mDoPostBean.setType(i+1);
                    typeBeanAdapter.setSelected_id(typeBean.getId());
                    typeBeanAdapter.notifyDataSetChanged();
                    http_request.doPost(URL,mDoPostBean,handler,200);
                }
            }
        });
    }
    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView,dx,dy);
        }
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            int se = recyclerView.computeHorizontalScrollExtent();
            int so = recyclerView.computeHorizontalScrollOffset();
            int sr = recyclerView.computeHorizontalScrollRange();
            if(newState == 0 && se+so ==sr){
                int page = mDoPostBean.getPage()+1;
                mDoPostBean.setPage(page) ;
                http_request.doPost(URL,mDoPostBean,handler,200);
            }
        }
    };
    private void initType() {
        TypeBean typeBean;
        String[] type_names = { "行政审批", "文化市场综合执法", "扫黄打非"};
        for (int i = 0; i < type_names.length; i++) {
            typeBean = new TypeBean();
            typeBean.setId(i);
            typeBean.setDt_name(type_names[i]);
            typeBean.setDt_icon(R.drawable.all);
            mTypeBeans.add(typeBean);
        }
    }
  /*  public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_before:
                if(now_position>0){
                    now_position-=1;
                    //news_wv.loadUrl(NetworkInfo.ip_address+"/website/get_news_website/"+String.valueOf(newsDataList.get(now_position).getId())+"/");
                    String WEBVIEW_CONTENT = "<html><head></head><body style=\"text-align:justify;margin:20px;font-size:15px;text-indent:2em\">%s</body></html>";
                    mWebView.loadData(String.format(WEBVIEW_CONTENT,mWHSCList.get(now_position).getInfo()),"text/html;charset=UTF-8",null);
                }
                break;
            case R.id.iv_next:
//                Log.d("unload",String.valueOf(mWHSCList.size()-1));
                if(now_position<mWHSCList.size()-1){
                    now_position+=1;
                    //news_wv.loadUrl(NetworkInfo.ip_address+"/website/get_news_website/"+String.valueOf(newsDataList.get(now_position).getId())+"/");
                    String WEBVIEW_CONTENT = "<html><head></head><body style=\"text-align:justify;margin:20px;font-size:15px;text-indent:2em\">%s</body></html>";
                    mWebView.loadData(String.format(WEBVIEW_CONTENT,mWHSCList.get(now_position).getInfo()),"text/html;charset=UTF-8",null);
                }
                break;
        }
    }*/
    public int dp2px(float dipValue) {
        //density显示器的逻辑密度,标准是160dpi，160dpi是density=1
        final float scale = this.getResources().getDisplayMetrics().density;
        //px = dp*density，+0.5f是补全四舍五入的值
        return (int) (dipValue * scale + 0.5f);
    }
}
