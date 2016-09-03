/*
			 * <intent-filter> <action android:name="android.intent.action.VIEW" />
			 * <action android:name="android.intent.action.DELETE" /> <category
			 * android:name="android.intent.category.DEFAULT" /> <data
			 * android:scheme="package" /> </intent-filter>
			 */
			if (!clickBean.isSystem()) {
				//用户apk
				Intent intent = new Intent("android.intent.action.DELETE");
				intent.addCategory("android.intent.category.DEFAULT");
				intent.setData(
						Uri.parse("package:" + clickBean.getPackName()));
				startActivity(intent);// 删除用户apk的Activity
				//刷新自己的数据，监听：package remove   注册删除数据广播,通过广播来更新数据
			}