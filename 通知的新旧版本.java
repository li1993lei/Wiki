

//旧版本

public void click(View view){
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification notification = new Notification(R.drawable.notification, "我是一个通知", System.currentTimeMillis());
		notification.flags = Notification.FLAG_AUTO_CANCEL;
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_CALL);
		intent.setData(Uri.parse("tel:110"));
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);
		notification.setLatestEventInfo(this, "我是标题", "我是内容", contentIntent);
		nm.notify(0, notification);
	}


//新版本
 
	@SuppressLint("NewApi")
	public void click2(View view){
		 Notification noti = new Notification.Builder(this)
         .setContentTitle("我是标题")
         .setContentText("我是内容")
         .setSmallIcon(R.drawable.notification)
         .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher))
         .build();
		 NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		 nm.notify(0, noti);
	}