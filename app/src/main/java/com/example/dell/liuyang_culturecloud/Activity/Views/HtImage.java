package com.example.dell.liuyang_culturecloud.Activity.Views;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by Lenovo on 2017/5/31.
 */
public abstract class HtImage {

    public static int rgbPixelAvg(Bitmap bitmap, int width, int height) {
        int sum=0;
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        for(int i=0;i<pixels.length;i++){
            int color=pixels[i];
            int A= Color.alpha(color);
            sum+=A;
        }
        return sum/(pixels.length); //返回红色像素平均值
    }
}