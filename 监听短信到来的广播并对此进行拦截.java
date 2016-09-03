


//1 定义一个广播接收者 继承广播接收者  重写onReceive方法
package com.lilei.smsmms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.gsm.SmsMessage;

public class SmsReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("短信来了");
		Object[] objs = (Object[])intent.getExtras().get("pdus");
		for (Object object : objs) {
			//拿到短信对象
			SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) object);
			String body = smsMessage.getMessageBody();
			String address = smsMessage.getOriginatingAddress();
			System.out.println("address："+address+"body："+body);
			if("5556".equals(address)){
				abortBroadcast();
			}
		}
		
	}

}


2 //在清单文件中进行配置 也可在代码中配置

 <receiver android:name=".SmsReceiver">
             <intent-filter android:priority="1000">
                <action android:name="android.provider.Telephony.SMS_RECEIVED"/>
            </intent-filter>
        </receiver>