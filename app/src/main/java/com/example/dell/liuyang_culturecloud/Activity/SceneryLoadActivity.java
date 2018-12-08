package com.example.culturecloud.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.culturecloud.Bean.MemoryBean;
import com.example.culturecloud.R;
import com.example.culturecloud.StaticResources.NetworkInfo;

public class SceneryLoadActivity extends BaseActivity {

    public static Bitmap front_bitmap=null,back_bitmap=null;
    private int pic_count = 0;
    Handler handler= new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 200:
                    front_bitmap=(Bitmap)msg.obj;
                    Log.d("ok==","新照片加载完成");
                    pic_count +=1;
                    if(pic_count >=2){
                        Intent intent =new Intent(SceneryLoadActivity.this,SceneryPlayActivity.class);
                        pic_count=0;
                        startActivity(intent);
                    }
                    break;
                case 201:
                    back_bitmap=(Bitmap)msg.obj;
                    Log.d("ok==","老照片加载完成");
                    pic_count +=1;
                    if(pic_count >=2){
                        Intent intent = new Intent(SceneryLoadActivity.this,SceneryPlayActivity.class);
                        pic_count=0;
                        startActivity(intent);
                    }
                    break;
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenery_load);
        findViewById(R.id.btn_id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getExtData();
    }

    private void getExtData() {
        Intent intent = getIntent();
        MemoryBean.Pictures picturesBean = (MemoryBean.Pictures) intent.getSerializableExtra("picture");

        Glide.with(this).load(NetworkInfo.NEW_IP_ADDRESS +"/media/"+picturesBean.getNew_picture()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Message message = new Message();
                message.what = 201;
                message.obj = resource;
                handler.sendMessage(message);
            }
        });

        Glide.with(this).load(NetworkInfo.NEW_IP_ADDRESS +"/media/"+picturesBean.getOld_picture()).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Message message = new Message();
                message.what = 200;
                message.obj = resource;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }
}
