package com.example.dell.liuyang_culturecloud.Activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class BaseActivity extends AppCompatActivity {

   /* String URL;//发起网络请求
    doRequest http_request = doRequest.getInstance(this);
    DoPostBean mDoPostBean = new DoPostBean();
    FatherBean mFatherBean;//解析出来的网络实体*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Window _window;
        _window = getWindow();
        WindowManager.LayoutParams params = _window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION| View.SYSTEM_UI_FLAG_IMMERSIVE;
        _window.setAttributes(params);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
     /*   mScreensaver = new Screensaver(1000*60*30); //定时x分钟测试
        mScreensaver.setOnTimeOutListener(this); //监听
        mScreensaver.start(); //开始倒计时*/

    }
   /* public void getDatas(){
        http_request.doPost(URL,mDoPostBean,new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Gson gson = new Gson();
                switch (msg.what){
                    case 200:
                        String response = (String)msg.obj;
                        mFatherBean = gson.fromJson(response,FatherBean.class);
                }
            }
        },200);
    }*/
}
