package com.lilei.weishi2.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class AddressDao {
	//数据库在splashActivity启动中就复制到本地
	//本地数据库的url
	private static String path = "/data/data/com.lilei.weishi2/files/address.db";
	
	/**
	 * 号码归属地查询方法
	 * 
	 * @param number
	 *            待查询的号码
	 * @return 号码归属地
	 */
	public static String addressQury(String number) {
		String address = number;
		//打开一个本地数据库 
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null,
				SQLiteDatabase.OPEN_READONLY);
				//对号码进行 正则判断
		if (number.matches("^1[3458]\\d{9}$")) {
			Cursor cursor = db.rawQuery("select location from data2 where id = (select outkey from data1 where id =?)",
					new String[] { number.substring(0, 7) });
			if (cursor.moveToNext()) {
				address = cursor.getString(0);
			}
		}else {
			switch (number.length()) {
			case 3:
				address = "特殊号码";
				break;
			case 4://5556
				address = "模拟器";
				break;
			case 5:
				address = "客服电话";
				break;
				
			case 7:
				address = "本地电话";
				break;
				
			case 8:
				address = "本地电话";
				break;
			default:
				if (number.length()>10 && number.startsWith("0")) {
					Cursor cursor = db.rawQuery("select location from data2 where area =?", new String[]{number.substring(1, 3)});
					if (cursor.moveToNext()) {
						String location = cursor.getString(0);
						address  = location.substring(0,location.length()-2);
					}
					cursor.close();
					cursor = db.rawQuery("select location from data2 where area =?", new String[]{number.substring(1, 4)});
					if (cursor.moveToNext()) {
						String location = cursor.getString(0);
						address  = location.substring(0,location.length()-2);
					}
					cursor.close();
				}
				break;
			}
			
		}
		
		return address;
	}
}
