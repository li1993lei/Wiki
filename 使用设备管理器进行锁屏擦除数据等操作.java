//1 新建一个类继承DeviceAdminReceiver
例如：com.lilei.weishi2.receiver.DeviceReceiver.java


//2 对清单文件进行配置
<receiver
            android:name="com.lilei.weishi2.receiver.DeviceReceiver"
            android:description="@string/sample_device_admin_description"
            android:label="@string/sample_device_admin"
            android:permission="android.permission.BIND_DEVICE_ADMIN" >
            <meta-data
                android:name="android.app.device_admin"
                android:resource="@xml/device_admin_sample" />

            <intent-filter>
                <action android:name="android.app.action.DEVICE_ADMIN_ENABLED" />
            </intent-filter>
        </receiver>

 // 3配置String字符串
 	<string name="activity_sample_device_admin">设备管理员</string>
    <string name="sample_device_admin">管理员</string>
    <string name="sample_device_admin_description">开启设备管理员，不开启扣2000块</string> 

 
 // 4新建xml文件加  建device_admin_sample.xml文件 其内容为
           <device-admin xmlns:android="http://schemas.android.com/apk/res/android">
	  <uses-policies>
	    <limit-password />
	    <watch-login />
	    <reset-password />
	    <force-lock />
	    <wipe-data />
	    <expire-password />
	    <encrypted-storage />
	    <disable-camera />
	  </uses-policies>
	</device-admin>
//5 在代码中创建设备管理器 并使用
	//锁屏的应用  并重置密码  
DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
				dpm.resetPassword("123", 0);
				dpm.lockNow();	  //锁屏
				dpm.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);//擦除外部存储	 	





//用户通常不会自己去设备管理器勾选 所以Z在这里需要引导用户开启激活管理员  

public void lockScreen(View v){
		
		//如果没有激活设备管理员，提醒给用户做事
		ComponentName who = new ComponentName(this, DeviceReceiver.class);
		if (dpm.isAdminActive(who)) {//true
			dpm.lockNow();//一键锁屏
			finish();
		} else {
			
			  Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
              intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, who);
              intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                     "赶紧激活我吧！");
              startActivityForResult(intent, 1);
			
		}
	}
	//当APP取得管理员权限 是无法被卸载的  在卸载之前 需要取消程序的管理员权限 才能完成卸载 

	public void remove(View v){
		//调用卸载的界面
		/* <intent-filter>
         <action android:name="android.intent.action.VIEW" />
         <action android:name="android.intent.action.DELETE" />
         <category android:name="android.intent.category.DEFAULT" />
         <data android:scheme="package" />
     </intent-filter>*/
		
		// 取消激活设备管理
		DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(DEVICE_POLICY_SERVICE);
		ComponentName who = new ComponentName(this, DeviceReceiver.class);
		dpm.removeActiveAdmin(who);//取消激活管理设备
		
		//卸载
		Intent remove = new Intent("android.intent.action.DELETE");
		remove.addCategory("android.intent.category.DEFAULT");
		remove.setData(Uri.parse("package:" + getPackageName()));
		startActivity(remove);//卸载用户apk的界面
	}
