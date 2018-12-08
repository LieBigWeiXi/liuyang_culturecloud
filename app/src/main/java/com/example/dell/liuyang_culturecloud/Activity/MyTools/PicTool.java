package com.example.dell.liuyang_culturecloud.Activity.MyTools;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * Created by Atlas on 2017/12/2.
 */

public class PicTool {
    public static Bitmap ChangePicSize(Bitmap bitmap)
    {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = 1366;
        int newHeight = 768;
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);

        return bitmap;
    }
}
