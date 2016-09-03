// 1定义一个内部类 去继承广播接收者

private class MyBroadcastReceiver extends BroadcastReceiver {

		

	}
//2  对广播接收者进行初始化 并设置过滤条件 优先级等
 public void onCreate() {
		receiver = new MyBroadcastReceiver();
		IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
		filter.setPriority(Integer.MAX_VALUE); //设置优先级
		registerReceiver(receiver, filter);
		super.onCreate();
	}	
//3在onReceive中写自己的逻辑
@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			
			Object datas[] = (Object[]) bundle.get("pdus");
			for (Object object : datas) {
				SmsMessage message = SmsMessage.createFromPdu((byte[])object);
				System.out.println(message.getMessageBody()+"::"+message.getOriginatingAddress());
				//在这里对接受到的短信进行处理  
				abortBroadcast();  //中止广播
			}

		}
// 4 在onDestory中 解除广播接收者的注册
@Override
	public void onDestroy() {
		unregisterReceiver(receiver);
		super.onDestroy();
	}