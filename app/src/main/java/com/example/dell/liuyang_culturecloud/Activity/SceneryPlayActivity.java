package com.example.culturecloud.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.culturecloud.Bean.PicturesBean;
import com.example.culturecloud.MyTools.PicTool;
import com.example.culturecloud.R;
import com.example.culturecloud.StaticResources.NetworkInfo;
import com.example.culturecloud.Views.ErasureView;
import com.example.culturecloud.Views.ShowImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SceneryPlayActivity extends BaseActivity {
    private int picture_count = 0;
    LinearLayout erasureView;
    Button return_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenery_play);
        erasureView =(LinearLayout)findViewById(R.id.scenery_layout);
        ShowImageView imageView=new ShowImageView(SceneryPlayActivity.this,1350,800);
        erasureView.addView(imageView);
        return_btn = (Button)findViewById(R.id.btn_id);
        return_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public int dp2px(float dipValue) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
