package com.example.dell.liuyang_culturecloud.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.dell.liuyang_culturecloud.Activity.Views.ShowImageView;
import com.example.dell.liuyang_culturecloud.R;

public class SceneryPlayActivity extends BaseActivity {
    private int picture_count = 0;
    LinearLayout erasureView;
    Button       return_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scenery_play);
        erasureView =(LinearLayout)findViewById(R.id.scenery_layout);
        ShowImageView imageView = new ShowImageView(SceneryPlayActivity.this,1350,800);
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
