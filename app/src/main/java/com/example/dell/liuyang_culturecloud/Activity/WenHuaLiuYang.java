package com.example.dell.liuyang_culturecloud.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.dell.liuyang_culturecloud.R;

public class WenHuaLiuYang extends BaseActivity implements View.OnClickListener{
    ImageView liuyang_jianjie,liuyang_jiyi,liuyang_wenguangju,liuyang_zhengce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wen_hua_liu_yang);

        liuyang_jianjie = (ImageView)findViewById(R.id.liuyang_jianjie);
        liuyang_jiyi = (ImageView)findViewById(R.id.liuyang_jiyi);
        liuyang_wenguangju = (ImageView)findViewById(R.id.liuyang_wenguangju);
        liuyang_zhengce = (ImageView)findViewById(R.id.liuyang_zhengce);
        liuyang_jianjie.setOnClickListener(this);
        liuyang_jiyi.setOnClickListener(this);
        liuyang_wenguangju.setOnClickListener(this);
        liuyang_zhengce.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()){
            case R.id.liuyang_jianjie:
                intent = new Intent(this,LiuYangIntroduceActivity.class);
                intent.putExtra("url","http://www.liuyang.gov.cn/mlly/mobile/index.html#p");
                startActivity(intent);
                break;
            case R.id.liuyang_jiyi://修改浏阳影像-品牌活动
                intent = new Intent(this,LiuYangYinXiang.class);
                startActivity(intent);
//                Toast.makeText(this, "浏阳记忆！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.liuyang_wenguangju://修改浏阳政策12.11
                intent = new Intent(this,LiuYangZhengCe.class);
//                intent = new Intent(this,LiuYangWenGuangJuActivity.class);
                startActivity(intent);
//                Toast.makeText(this, "浏阳文广局暂无资料！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.liuyang_zhengce://修改 浏阳名人12.10
//                intent = new Intent(this,LiuYangZhengCe.class);
                intent = new Intent(this,LiuYangFameActivity.class);
                startActivity(intent);
//                Toast.makeText(this, "浏阳政策！", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
