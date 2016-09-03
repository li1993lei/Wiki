package com.lilei.youku;

import android.view.animation.RotateAnimation;
import android.widget.RelativeLayout;

/**
 * @author 李小磊
 *
 */

public class RotatUtils {

	/**
	 * 让指定的view执行旋转离开的动画
	 * @param view  
	 */
	public static void startAnimout(RelativeLayout view) {
		startAnimout(view,0);
	}
	/**
	 * 让指定的view 延时执行旋转离开的动画
	 * @param view
	 * @param offset
	 */
	public static void startAnimout(RelativeLayout view,long offset) {
		// TODO Auto-generated method stub
		RotateAnimation animation = new RotateAnimation(0, 180,	view.getWidth()/2, view.getHeight());
		animation.setDuration(500);  //设置运行时间
		animation.setStartOffset(offset);//延迟offset时间执行该动画
		animation.setFillAfter(true); //设置旋转后是否保持原状态
		view.startAnimation(animation);
	}
	
	
	/**让指定的view执行旋转进入的动画
	 * @param view
	 */
	public static void startAnimin(RelativeLayout view) {

		startAnimin(view, 0);
	}
	/**
	 * 让指定的view延时 执行旋转进入的动画
	 * @param view
	 * @param offset
	 */
	public static void startAnimin(RelativeLayout view,long offset) {
		// TODO Auto-generated method stub
		RotateAnimation animation = new RotateAnimation(180, 360,view.getWidth()/2, view.getHeight());
		animation.setDuration(500);  //设置运行时间
		animation.setStartOffset(offset);//延迟offset时间执行该动画
		animation.setFillAfter(true); //设置旋转后是否保持原状态
		view.startAnimation(animation);
	}
}


















主程序处理处理逻辑


package com.lilei.youku;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;


public class MainActivity extends Activity implements OnClickListener {

	
	private RelativeLayout level1;
	private RelativeLayout level2;
	private RelativeLayout level3;
	
	private ImageView ic_menu;
	private ImageView icon_home;
	/*判断三级菜单是否显示
	 * true 为显示
	 * */
	private boolean isLevel3Show = true;
	/*判断二级菜单是否显示
	 * true 为显示
	 * */
	private boolean isLevel2Show = true;
	/*判断一级菜单是否显示
	 * true 为显示
	 * */
	private boolean isLevel1Show = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        level1 = (RelativeLayout) findViewById(R.id.level1);
        level2 = (RelativeLayout) findViewById(R.id.level2);
        level3 = (RelativeLayout) findViewById(R.id.level3);
        ic_menu=(ImageView) findViewById(R.id.icon_menu);
        icon_home=(ImageView) findViewById(R.id.icon_home);
        
        ic_menu.setOnClickListener(this);
        icon_home.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.icon_menu:  //处理menu的点击事件   
			if(isLevel3Show){
				RotatUtils.startAnimout(level3);
			}else{
				RotatUtils.startAnimin(level3);
			}
			isLevel3Show=!isLevel3Show;
			break;
		case R.id.icon_home:
			if(isLevel2Show){  //处理菜单的点击事件
								//2级菜单显示的话 就滑出  并把标记置为false
				RotatUtils.startAnimout(level2);
				isLevel2Show=false;
				if(isLevel3Show){
					RotatUtils.startAnimout(level3,200);
					isLevel3Show=false;
				}
			}else{            //2级菜单不显示  就打开二级菜单   并把标记置为true
				RotatUtils.startAnimin(level2);
				isLevel2Show=true;
			}
			break;
		default:
			break;
		}
	}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// TODO Auto-generated method stub
    	if(keyCode==KeyEvent.KEYCODE_MENU){
    		changeLevelState();
    	}
    	return super.onKeyDown(keyCode, event);
    }

	private void changeLevelState() {
		// TODO Auto-generated method stub
		if(isLevel1Show){
			RotatUtils.startAnimout(level1);
			isLevel1Show=false;
			if(isLevel2Show){
				RotatUtils.startAnimout(level2, 200);
				isLevel2Show=false;
				if(isLevel3Show){
					RotatUtils.startAnimout(level3, 200);
					isLevel3Show=false;
				}
			}
		}else{   //显示12级菜单
			RotatUtils.startAnimin(level1);
			isLevel1Show=true;
			RotatUtils.startAnimin(level2,200);
			isLevel2Show=true;
		}
	}
}







页面xml文件   



<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.lilei.youku.MainActivity" >

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerHorizontal="true"
        android:layout_centerVertical="true"
        android:text="@string/app_name" />

    <RelativeLayout
        android:id="@+id/level1"
        android:layout_width="100dp"
        android:layout_height="50dp"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:background="@drawable/level1" >

        <ImageView
            android:id="@+id/icon_home"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerInParent="true"
            android:background="@drawable/icon_home" />
    </RelativeLayout>

    <RelativeLayout
        android:id="@+id/level2"
        android:layout_width="180dp"
        android:layout_height="90dp"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:background="@drawable/level2" >

        <ImageView
            android:id="@+id/icon_search"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentBottom="true"
            android:layout_margin="10dp"
            android:background="@drawable/icon_search" />

        <ImageView
            android:id="@+id/icon_menu"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerHorizontal="true"
            android:layout_margin="5dp"
            android:background="@drawable/icon_menu" />

        <ImageView
            android:id="@+id/icon_myyouku"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentBottom="true"
            android:layout_alignParentRight="true"
            android:layout_margin="10dp"
            android:background="@drawable/icon_myyouku" />
    </RelativeLayout>

    <RelativeLayout
        android:id="@+id/level3"
        android:layout_width="280dp"
        android:layout_height="140dp"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:background="@drawable/level3" >

        <ImageView
            android:id="@+id/channel1"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentBottom="true"
            android:layout_marginBottom="10dp"
            android:layout_marginLeft="10dp"
            android:background="@drawable/channel1" />

        <ImageView
            android:id="@+id/channel2"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_above="@id/channel1"
            android:layout_alignLeft="@id/channel1"
            android:layout_marginBottom="6dp"
            android:layout_marginLeft="20dp"
            android:background="@drawable/channel2" />

        <ImageView
            android:id="@+id/channel3"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_above="@id/channel2"
            android:layout_alignLeft="@id/channel2"
            android:layout_marginBottom="6dp"
            android:layout_marginLeft="30dp"
            android:background="@drawable/channel3" />

        <ImageView
            android:id="@+id/channel7"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentBottom="true"
            android:layout_alignParentRight="true"
            android:layout_marginBottom="10dp"
            android:layout_marginRight="10dp"
            android:background="@drawable/channel7" />

        <ImageView
            android:id="@+id/channel6"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_above="@id/channel7"
            android:layout_alignRight="@id/channel7"
            android:layout_marginBottom="6dp"
            android:layout_marginRight="20dp"
            android:background="@drawable/channel6" />
         <ImageView
            android:id="@+id/channel5"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_above="@id/channel2"
            android:layout_alignRight="@id/channel6"
            android:layout_marginBottom="6dp"
            android:layout_marginRight="30dp"
            android:background="@drawable/channel5" />
          <ImageView
            android:id="@+id/channel4"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerHorizontal="true"
            android:layout_margin="5dp"
            android:background="@drawable/channel4" />
        
    </RelativeLayout>

</RelativeLayout>