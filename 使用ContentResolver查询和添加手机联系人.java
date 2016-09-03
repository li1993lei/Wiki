//查询手机联系人 号码 姓名 邮箱

	/**
	 * 查询联系人
	 */
	public void queryContact(View v) {
		// 1 拿到ContentProvider的路径
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		Uri dataUri = Uri.parse("content://com.android.contacts/data");
		// 2 查询 _id列
		Cursor cursor = getContentResolver().query(uri, new String[] { "_id" },
				null, null, null);
		if (cursor != null && cursor.getCount() > 0) {
			while (cursor.moveToNext()) {
				int id = cursor.getInt(0);
				// 利用此id去data表中查询数据
				Cursor cursor2 = getContentResolver().query(uri,
						new String[] { "data1", "mimetype" },
						"raw_contact_id = ?",
						new String[] { String.valueOf(id) }, null);
					if(cursor2!=null&&cursor2.getCount()>0){
						while(cursor2.moveToNext()){
							String data1 = cursor2.getString(0); // 当前取的是data1数据
							String mimetype = cursor2.getString(1); // 当前取的是mimetype的值

							if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
								Log.i(TAG, "号码: " + data1);
							} else if ("vnd.android.cursor.item/name"
									.equals(mimetype)) {
								Log.i(TAG, "姓名: " + data1);
							} else if ("vnd.android.cursor.item/email_v2"
									.equals(mimetype)) {
								Log.i(TAG, "邮箱: " + data1);
							}
						}
						cursor2.close();
					}
			}
			cursor.close();
		}
	}









//添加联系人 



	public void addContacts(View v) {
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		Uri dataUri = Uri.parse("content://com.android.contacts/data");
		// 1. 在raw_contacts表中添加一个记录

		// 取raw_contacts表中contact_id的值
		Cursor cursor = getContentResolver().query(uri,
				new String[] { "contact_id" }, null, null,
				"contact_id desc limit 1");
		if (cursor != null && cursor.moveToFirst()) {
			int contact_id = cursor.getInt(0);
			contact_id++;
			cursor.close();

			ContentValues values = new ContentValues();
			values.put("contact_id", contact_id);
			getContentResolver().insert(uri, values);

			// 2. 根据上面添加记录的id, 取data表中添加三条数据

			// 存号码
			values = new ContentValues();
			values.put("mimetype", "vnd.android.cursor.item/phone_v2");
			values.put("data1", "10086");
			values.put("raw_contact_id", contact_id);
			getContentResolver().insert(dataUri, values);

			// 存姓名
			values = new ContentValues();
			values.put("mimetype", "vnd.android.cursor.item/name");
			values.put("data1", "中国移动");
			values.put("raw_contact_id", contact_id);
			getContentResolver().insert(dataUri, values);

			// 存姓名
			values = new ContentValues();
			values.put("mimetype", "vnd.android.cursor.item/email_v2");
			values.put("data1", "10086@kengni.com");
			values.put("raw_contact_id", contact_id);
			getContentResolver().insert(dataUri, values);
		}

	}