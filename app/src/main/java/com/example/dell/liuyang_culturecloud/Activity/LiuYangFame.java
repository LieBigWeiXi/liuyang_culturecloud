package com.example.dell.liuyang_culturecloud.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dell.liuyang_culturecloud.Activity.Bean.WenHuaYiChanBean;
import com.example.dell.liuyang_culturecloud.Activity.StaticResources.NetworkInfo;
import com.example.dell.liuyang_culturecloud.R;


public class LiuYangFame extends BaseActivity {
    /*ImageView show_iamge;
    TextView  title_text;
    WebView   content_text;
    Button    return_button;*/
    TextView  title_text;
    WebView   content_text;
    ImageView show_iamge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.liuyang_fame);
        setContentView(R.layout.show_fame_info);
       /* show_iamge = (ImageView) findViewById(R.id.image_view_fy);
        content_text = (WebView)findViewById(R.id.text_fy);
        title_text = (TextView)findViewById(R.id.title_text);
        return_button = (Button)findViewById(R.id.information_return);
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/

       show_iamge = (ImageView)findViewById(R.id.whyc_cover) ;
       title_text = (TextView)findViewById(R.id.text_intro);
       content_text = (WebView)findViewById(R.id.whyc_info_web) ;
       getExtData();

    }
    private void getExtData(){
        Intent intent = getIntent();
        WenHuaYiChanBean.Data whyc = (WenHuaYiChanBean.Data) intent.getSerializableExtra("whyc_key");

        String WEBVIEW_CONTENT = "<html><head></head><body style=\"" +
                "text-align:justify;" +
                "margin:20px;" +
                "font-size:12px;" +
                "text-indent:2em\">%s</body></html>";
        content_text.setBackgroundColor(0);//设置背景透明
        content_text.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        if(whyc!=null){
            String html_data = whyc.getInfo();
            title_text.setText(whyc.getName());
            //content_text.loadData(String.format(WEBVIEW_CONTENT,html_data),"text/html;charset=UTF-8",null);
            if(whyc.getUrl()!=null){
                content_text.loadUrl(whyc.getUrl());
            }else{
                content_text.loadDataWithBaseURL(NetworkInfo.IP_ADDRESS,
                        String.format(WEBVIEW_CONTENT,html_data),
                        "text/html;charset=UTF-8",
                        null,
                        null);
            }

            Glide.with(this).load(NetworkInfo.IP_ADDRESS+"/media/"+whyc.getCover()).into(show_iamge);
        }

    }
}
