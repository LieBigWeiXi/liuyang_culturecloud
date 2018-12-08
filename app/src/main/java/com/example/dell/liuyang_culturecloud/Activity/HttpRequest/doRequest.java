package com.example.dell.liuyang_culturecloud.Activity.HttpRequest;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 村长 on 2018/6/16.
 */

public class doRequest {
    private  OkHttpClient mOkHttpClient = new OkHttpClient();
    private static doRequest instance;
    private Context mContext;
    private doRequest(){}
    private doRequest(Context context){
        this.mContext = context;
    }

    public static doRequest getInstance(Context context){
        if(instance==null){
            instance = new doRequest(context);
        }
        return  instance;
    }


    public  void doPost(final String url, final Object object, final Handler handler, final int msg_what){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                //将数据对象解析成json格式
                String json = gson.toJson(object);
                //将数据对象转换成json格式的请求体
                RequestBody requestBody = FormBody.create(MediaType.parse("application/json;charset=utf-8"), json);
                Request request = new Request.Builder().url(url).post(requestBody).build();
                Response response = null;
                try {
                    response = mOkHttpClient.newCall(request).execute(); //得到返回数据
                    if(response != null){
                        String responseData = response.body().string();
                        Message message = new Message();
                        message.what = msg_what;
                        message.obj = responseData; //将返回的数据传给消息携带者Message
                        handler.sendMessage(message);
                    }else{
                        Toast.makeText(mContext,"网络连接异常，无法获取服务器返回数据！",Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    public void doGet(final String url, final int msg_what, final Handler handler){
        Log.d("DoGet", "doGet: url"+url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder().url(url).build();
                try {
                    Response response = mOkHttpClient.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("doRequest", "run: responseData"+responseData);
                    Message message = new Message();
                    message.what = msg_what;
                    message.obj = responseData;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
