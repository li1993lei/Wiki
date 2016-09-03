




//增加
Uri uri = Uri.parse("content://com.itheima28.sqlitedemo.providers.PersonContentProvider/person/insert");
		ContentResolver resolver = getContext().getContentResolver();
		ContentValues values = new ContentValues();
		values.put("name", "nimei");
		values.put("age", 20);
		uri = resolver.insert(uri, values);

		long id = ContentUris.parseId(uri);


//删除
Uri uri = Uri.parse("content://com.itheima28.sqlitedemo.providers.PersonContentProvider/person/delete");
		
		// 内容提供者访问对象
		ContentResolver resolver = getContext().getContentResolver();
		
		String where = "_id = ?";
		String[] selectionArgs = {"21"};
		int count = resolver.delete(uri, where, selectionArgs);
		Log.i(TAG, "删除行: " + count);
//更新

		Uri uri = Uri.parse("content://com.itheima28.sqlitedemo.providers.PersonContentProvider/person/update");
		
		// 内容提供者访问对象
		ContentResolver resolver = getContext().getContentResolver();
		
		ContentValues values = new ContentValues();
		values.put("name", "lisi");
		
		int count = resolver.update(uri, values, "_id = ?", new String[]{"20"});
		Log.i(TAG, "更新行: " + count);
//查询全部
Uri uri = Uri.parse("content://com.itheima28.sqlitedemo.providers.PersonContentProvider/person/queryAll");
		
		// 内容提供者访问对象
		ContentResolver resolver = getContext().getContentResolver();
		
		Cursor cursor = resolver.query(uri, new String[]{"_id", "name", "age"}, null, null, "_id desc");
		
		if(cursor != null && cursor.getCount() > 0) {
			
			int id;
			String name;
			int age;
			while(cursor.moveToNext()) {
				id = cursor.getInt(0);
				name = cursor.getString(1);
				age = cursor.getInt(2);
				Log.i(TAG, "id: " + id + ", name: " + name + ", age: " + age);
			}
			cursor.close();
		}
//查询单条数据


		Uri uri = Uri.parse("content://com.itheima28.sqlitedemo.providers.PersonContentProvider/person/query/#");
		
		// 在uri的末尾添加一个id content://com.itheima28.sqlitedemo.providers.PersonContentProvider/person/query/20
		uri = ContentUris.withAppendedId(uri, 20);
		
		// 内容提供者访问对象
		ContentResolver resolver = getContext().getContentResolver();
		
		Cursor cursor = resolver.query(uri, new String[]{"_id", "name", "age"}, null, null, null);
		
		if(cursor != null && cursor.moveToFirst()) {
			int id = cursor.getInt(0);
			String name = cursor.getString(1);
			int age = cursor.getInt(2);
			cursor.close();
			Log.i(TAG, "id: " + id + ", name: " + name + ", age: " + age);
		}
