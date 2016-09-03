//主页按键的处理逻辑跟返回键不同  许定义一个广播接收者 接受点击主页按钮的消息

private class HomeReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
				backHome();
			}
			
		}
		
	}


	//初始化逻辑
	receiver = new HomeReceiver();
		IntentFilter filter  = new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS");
		registerReceiver(receiver, filter );
	//销毁
	unregisterReceiver(receiver);	



//回到主页面的intent
Intent intent = new Intent("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.HOME");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.addCategory("android.intent.category.MONKEY");


		 <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.HOME" />
                <category android:name="android.intent.category.DEFAULT" />
                <category android:name="android.intent.category.MONKEY"/>
            </intent-filter>