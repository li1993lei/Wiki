


					SmsManager smsManager = SmsManager.getDefault();		// 短信管理器
					smsManager.sendTextMessage(
							"18511619290", 	// 收件人的号码
							null,			// 短信中心号码 
							"今晚小树林, 不见不散.", 
							null, 	// 如果发送成功, 回调此广播, 通知我们.
							null);	// 当对方接收成功, 回调此广播.