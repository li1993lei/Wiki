package com.lilei.weishi2.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class AddressDao {
	//���ݿ���splashActivity�����о͸��Ƶ�����
	//�������ݿ��url
	private static String path = "/data/data/com.lilei.weishi2/files/address.db";
	
	/**
	 * ��������ز�ѯ����
	 * 
	 * @param number
	 *            ����ѯ�ĺ���
	 * @return ���������
	 */
	public static String addressQury(String number) {
		String address = number;
		//��һ���������ݿ� 
		SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null,
				SQLiteDatabase.OPEN_READONLY);
				//�Ժ������ �����ж�
		if (number.matches("^1[3458]\\d{9}$")) {
			Cursor cursor = db.rawQuery("select location from data2 where id = (select outkey from data1 where id =?)",
					new String[] { number.substring(0, 7) });
			if (cursor.moveToNext()) {
				address = cursor.getString(0);
			}
		}else {
			switch (number.length()) {
			case 3:
				address = "�������";
				break;
			case 4://5556
				address = "ģ����";
				break;
			case 5:
				address = "�ͷ��绰";
				break;
				
			case 7:
				address = "���ص绰";
				break;
				
			case 8:
				address = "���ص绰";
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
