package com.example.culturecloud.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.culturecloud.Bean.Places;
import com.example.culturecloud.Bean.WenHuaYiChanBean;
import com.example.culturecloud.R;
import com.example.culturecloud.StaticResources.NetworkInfo;

public class InfomationActivity extends BaseActivity {
    ImageView show_iamge;
    TextView title_text;
    WebView content_text;
    Button return_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infomation);
        show_iamge = (ImageView) findViewById(R.id.image_view_fy);
        content_text = (WebView)findViewById(R.id.text_fy);
        title_text = (TextView)findViewById(R.id.title_text);
        return_button = (Button)findViewById(R.id.information_return);
        return_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getExtData();

    }
    private void getExtData(){
        Intent intent = getIntent();
        WenHuaYiChanBean.WHYC whyc = (WenHuaYiChanBean.WHYC) intent.getSerializableExtra("whyc_key");
        Places.Info wtcg = (Places.Info) intent.getSerializableExtra("wtcg_key");
        String WEBVIEW_CONTENT = "<html><head></head><body style=\"text-align:justify;margin:20px;font-size:20px;text-indent:2em\">%s</body></html>";
        content_text.setBackgroundColor(0);//设置背景透明
        content_text.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        if(whyc!=null){
            String html_data = whyc.getInfo();
            title_text.setText(whyc.getName());
            //content_text.loadData(String.format(WEBVIEW_CONTENT,html_data),"text/html;charset=UTF-8",null);
            content_text.loadDataWithBaseURL(NetworkInfo.NEW_IP_ADDRESS,String.format(WEBVIEW_CONTENT,html_data),"text/html;charset=UTF-8",null,null);
            Glide.with(this).load(NetworkInfo.NEW_IP_ADDRESS+"/media/"+whyc.getCover()).into(show_iamge);
        }else if(wtcg!=null){
            String html_data_wtcg = wtcg.getInfo();
            title_text.setText(wtcg.getName());
            content_text.loadData(String.format(WEBVIEW_CONTENT,html_data_wtcg),"text/html;charset=UTF-8",null);
            String url = NetworkInfo.NEW_IP_ADDRESS+"/media/"+wtcg.getCover();
            Log.d("infomation", "cover_url");
            Glide.with(this).load(url).into(show_iamge);
        }

    }
}
