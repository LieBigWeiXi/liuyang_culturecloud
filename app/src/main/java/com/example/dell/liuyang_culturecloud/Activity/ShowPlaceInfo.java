package com.example.dell.liuyang_culturecloud.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.liuyang_culturecloud.Activity.Bean.WenHuaPlaceBean;
import com.example.dell.liuyang_culturecloud.Activity.Bean.WenHuaYiChanBean;
import com.example.dell.liuyang_culturecloud.Activity.StaticResources.NetworkInfo;
import com.example.dell.liuyang_culturecloud.R;

public class ShowPlaceInfo extends BaseActivity {
   /* TextView title_text;
    WebView introduce_web,info_web;
    ImageView cover_image;*/
   TextView  title_text;
   WebView   info_web;
   ImageView cover_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_moreinfo);
        title_text = (TextView) (findViewById(R.id.text_intro));
//        introduce_web = (WebView) (findViewById(R.id.whyc_intro_web));
        info_web = (WebView) (findViewById(R.id.whyc_info_web));
        info_web.setWebViewClient(new WebViewClient());
        info_web.getSettings().setJavaScriptEnabled(true);
        cover_image = (ImageView) (findViewById(R.id.whyc_cover));
        Intent intent = getIntent();
        WenHuaPlaceBean.Data wtcg = (WenHuaPlaceBean.Data)intent.getSerializableExtra("Place");
        String WEBVIEW_CONTENT = "<html><head></head><body style=\"" +
                "text-align:justify;" +
                "margin:10px;" +
                "font-size:14px;" +
                "color:#ffffff;"+
                "text-indent:2em\">%s</body></html>";
        info_web.setBackgroundColor(0);//设置背景透明

        title_text.setText(wtcg.getName());
        Glide.with(this).load(NetworkInfo.IP_ADDRESS + "/media/" + wtcg.getCover()).into(cover_image);
        if(wtcg.getUrl()!=null){
            info_web.loadUrl(wtcg.getUrl());
        }else {
            info_web.loadDataWithBaseURL(NetworkInfo.IP_ADDRESS,
                    String.format(WEBVIEW_CONTENT, wtcg.getInfo()),
                    "text/html;charset=UTF-8", null, null);
        }
    }
}
