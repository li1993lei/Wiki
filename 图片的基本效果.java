package com.lilei.picturedemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends Activity {
	private ImageView iv1,iv2;
	private Bitmap alterBitmap;
	private Bitmap srcBmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv1 = (ImageView) findViewById(R.id.iv1);
		iv2 = (ImageView) findViewById(R.id.iv2);
		//给第一个imageView设置一个位图
		srcBmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		iv1.setImageBitmap(srcBmp);
		//给第二个imageView 设置一个空的位图
		alterBitmap = Bitmap.createBitmap(srcBmp.getWidth(), srcBmp.getHeight(), srcBmp.getConfig());
    }
    public void click(View view){
    	System.out.println("click");
    	//准备画布
    	Canvas canvas = new Canvas(alterBitmap);
    	//准备画笔
    	Paint paint = new Paint();
    	paint.setColor(Color.BLACK);
    	//画画
        //复制图片
    	Matrix matrix = new Matrix();
     	canvas.drawBitmap(srcBmp, matrix, paint);
     	iv2.setImageBitmap(alterBitmap);
    }
}













//图片的旋转
public void click(View view){
        //1.准备一个画板  在上面放上准备好的 空白的位图
        Canvas canvas = new Canvas(alterBitmap);
        //2.准备一个画笔
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        //3.画画
        Matrix m = new Matrix();
        m.setRotate(180, srcBmp.getWidth()/2, srcBmp.getHeight()/2);
        
        canvas.drawBitmap(srcBmp, m, paint);
        iv2.setImageBitmap(alterBitmap);//把原图的副本设置到界面上。
    }















//图片的镜面效果


    public void click(View view){
        //1.准备一个画板  在上面放上准备好的 空白的位图
        Canvas canvas = new Canvas(alterBitmap);
        //2.准备一个画笔
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        //3.画画
        Matrix m = new Matrix();
        m.setScale(1.0f, -1.0f);
        m.postTranslate(0, srcBmp.getHeight());
        canvas.drawBitmap(srcBmp, m, paint);
        iv2.setImageBitmap(alterBitmap);//把原图的副本设置到界面上。
    }










//图片的渐变效果
    public void click(View view){
        //1.准备一个画板  在上面放上准备好的 空白的位图
        Canvas canvas = new Canvas(alterBitmap);
        //2.准备一个画笔
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        //3.画画
        Matrix m = new Matrix();
        
        ColorMatrix cm = new ColorMatrix();
        cm.set(new float[] {
        0.5f, 0, 0, 0, 0,
        0, 0.8f, 0, 0, 0,
        0, 0, 0.6f, 0, 0,
        0, 0, 0, 1, 0
        });
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(srcBmp, m, paint);
        iv2.setImageBitmap(alterBitmap);//把原图的副本设置到界面上。
    }


