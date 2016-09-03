//提供服务运行级别
		Notification noti = new Notification();
		noti.icon = R.drawable.ic_launcher;
		Intent intent = new Intent();
		intent.setAction("com.itheima62.homeactivity");
		PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
		noti.setLatestEventInfo(getApplicationContext(), "安全卫士", "我在保护您的手机！请不要关闭", contentIntent );
		//设置成前台进程
		startForeground(110,noti);