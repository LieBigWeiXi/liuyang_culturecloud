package com.example.dell.liuyang_culturecloud.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.liuyang_culturecloud.Activity.Bean.WenHuaPlaceBean;
import com.example.dell.liuyang_culturecloud.Activity.Bean.WenHuaYiChanBean;
import com.example.dell.liuyang_culturecloud.Activity.StaticResources.NetworkInfo;
import com.example.dell.liuyang_culturecloud.R;

public class ShowPlaceInfo extends BaseActivity {
    TextView title_text;
    WebView introduce_web,info_web;
    ImageView cover_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_moreinfo);
        title_text = (TextView) (findViewById(R.id.title_textView));
        introduce_web = (WebView) (findViewById(R.id.whyc_intro_web));
        info_web = (WebView) (findViewById(R.id.whyc_info_web));
        cover_image = (ImageView) (findViewById(R.id.whyc_cover));
        Intent intent = getIntent();
        WenHuaPlaceBean.Data wtcg = (WenHuaPlaceBean.Data)intent.getSerializableExtra("Place");
        String WEBVIEW_CONTENT = "<html><head></head><body style=\"" +
                "text-align:justify;" +
                "margin:10px;" +
                "font-size:5px;" +
                "color:#ffffff;"+
                "text-indent:2em\">%s</body></html>";
        introduce_web.setBackgroundColor(0);//设置背景透明

        title_text.setText(wtcg.getName());
        Glide.with(this).load(NetworkInfo.IP_ADDRESS + "/media/" + wtcg.getCover()).into(cover_image);
        introduce_web.loadDataWithBaseURL(NetworkInfo.IP_ADDRESS,
                String.format(WEBVIEW_CONTENT, wtcg.getInfo()),
                "text/html;charset=UTF-8", null, null);
        info_web.loadUrl(wtcg.getUrl());



    }
}
