package com.example.culturecloud.Views;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.culturecloud.R;

/**
 * Created by DELL on 2018/7/12.
 */

public class ReturnButton extends FrameLayout implements View.OnClickListener {
    public ReturnButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.bottom_bar,this);
        Button return_button = (Button)findViewById(R.id.return_button);
        return_button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.return_button:
                ((Activity)getContext()).finish();
                break;
            default:
                break;
        }
    }
}
