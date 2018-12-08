package com.example.culturecloud.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.culturecloud.Activity.SceneryLoadActivity;
import com.example.culturecloud.Bean.pointBean;
import com.example.culturecloud.MyTools.PicTool;

import java.util.HashMap;


public class ShowImageView extends View {
	Bitmap fore_bitmap;//声明Bitmap类
	Bitmap back_bitmap;//声明Bitmap类
	Bitmap temp_bitmap;//声明Bitmap类
	Canvas canvasTemp;//声明画布类
	Paint paint;//声明画笔类
	Path path;//画图类
	int width;//声明width变量
	int height;//声明height变量
	Context context;
	HashMap<Integer,pointBean> points = new HashMap<Integer, pointBean>();
	Matrix matrix;//使用矩阵控制图片移动、缩放、旋转
	Handler handler = new Handler(){
		public void handleMessage(Message msg){
			switch (msg.what)
			{
				case 202:
					canvasTemp.drawRect(0,0,fore_bitmap.getWidth(),fore_bitmap.getHeight(),paint);
					break;
			}
		}
	};


	public ShowImageView(Context context, int width, int height) {//构造方法
		super(context);
		this.width = width;
		this.height = height ;
		this.context = context;
		init();
	}
	public ShowImageView(Context context, AttributeSet attrs, int width, int height) {//构造方法
		super(context, attrs);
		this.width = width;
		this.height = height;
		this.context=context;
		init();
	}
	void init() {
		back_bitmap = PicTool.ChangePicSize(SceneryLoadActivity.back_bitmap);
		temp_bitmap= PicTool.ChangePicSize(SceneryLoadActivity.front_bitmap);
		fore_bitmap = Bitmap.createBitmap(width, height, Config.ARGB_4444);//创建一个位图 ARGB_4444 代表16位ARGB位图
		paint = new Paint();//实例化paint类 画笔类
		paint.setAntiAlias(true);//设置画笔的锯齿效果
		paint.setDither(true);// 设置递色 不是太清楚
		canvasTemp = new Canvas(fore_bitmap);//实例化 画布 fore_bitmap类当参数
		canvasTemp.drawColor(Color.TRANSPARENT);//设置背景颜色为透明的
		matrix = new Matrix();//实例化Matrix类
		matrix.setScale(width*1.0F/temp_bitmap.getWidth(), height*1.0F/temp_bitmap.getHeight());//缩放  它采用两个浮点数作为参数，分别表示在每个轴上所产生的缩放量。第一个参数是x轴的缩放比例，而第二个参数是y轴的缩放比例。
		canvasTemp.drawBitmap(temp_bitmap, matrix, paint);//绘制图像
		temp_bitmap.recycle();//回收
        paint.setColor(Color.RED);//设置画笔颜色
        paint.setStrokeWidth(25);//设置描边宽度
        BlurMaskFilter bmf = new BlurMaskFilter(40, BlurMaskFilter.Blur.NORMAL);//指定了一个模糊的样式和半径来处理Paint的边缘。
        paint. setMaskFilter(bmf);//为Paint分配边缘效果。
        paint.setStyle(Paint.Style.FILL);//让画出的图形是空心的
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));//它的作用是用此画笔后，画笔划过的痕迹就变成透明色了。画笔设置好了后，就可以调用该画笔进行橡皮痕迹的绘制了
        paint.setStrokeJoin(Paint.Join.ROUND);//设置结合处的样子 Miter:结合处为锐角， Round:结合处为圆弧：BEVEL：结合处为直线。
        paint.setStrokeCap(Paint.Cap.ROUND);//画笔笔刷类型   方形形状
		path = new Path();//实例化画图类

	}

	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawBitmap(back_bitmap, matrix, null);//绘制图像
		canvas.drawBitmap(fore_bitmap, 0, 0, null);//绘制图像
	}
	
	
	public boolean onTouchEvent(MotionEvent event) {
        int pointerCount = event.getPointerCount();
		HashMap<Integer,pointBean> new_points=new HashMap<Integer, pointBean>();
		int id = 0;
		long time= System.currentTimeMillis();
		if (canvasTemp != null) {
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					break;
				case MotionEvent.ACTION_MOVE:
					for (int i = 0; i < pointerCount; i++) {
						int point_id=(int) event.getPointerId(i);
						int x = (int) event.getX(i);
						int y = (int) event.getY(i);
						new_points.put(point_id,new pointBean(x,y,time));
						if(points.containsKey(point_id)) {
							pointBean old_point=points.get(point_id);
							if(time-old_point.mCurTime<150) canvasTemp.drawLine(old_point.x,old_point.y,x,y,paint);
						}
					}
					points=new_points;
					break;
				case MotionEvent.ACTION_UP:
					break;
				case MotionEvent.ACTION_POINTER_UP:
					break;
			}
		}
		getPicturePixel(fore_bitmap);
		postInvalidate();//刷新界面
		return true;
	}

	public void realease(){//用来回收内存
		if(null != back_bitmap && !fore_bitmap.isRecycled())
			back_bitmap.recycle();
		if(null != fore_bitmap && !fore_bitmap.isRecycled())
			fore_bitmap.recycle();
	}

	private void getPicturePixel(final Bitmap bitmap) {
		final int width = bitmap.getWidth();
		final int height = bitmap.getHeight();
		new Thread(new Runnable() {
			@Override
			public void run() {
				int c=HtImage.rgbPixelAvg(bitmap,width,height);
				if(c<175&&c>0)
				{
					Message msg=new Message();
					msg.what=202;
					handler.sendMessage(msg);
				}
			}
		}).start();
	}
}
