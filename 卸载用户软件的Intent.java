/*
			 * <intent-filter> <action android:name="android.intent.action.VIEW" />
			 * <action android:name="android.intent.action.DELETE" /> <category
			 * android:name="android.intent.category.DEFAULT" /> <data
			 * android:scheme="package" /> </intent-filter>
			 */
			if (!clickBean.isSystem()) {
				//�û�apk
				Intent intent = new Intent("android.intent.action.DELETE");
				intent.addCategory("android.intent.category.DEFAULT");
				intent.setData(
						Uri.parse("package:" + clickBean.getPackName()));
				startActivity(intent);// ɾ���û�apk��Activity
				//ˢ���Լ������ݣ�������package remove   ע��ɾ�����ݹ㲥,ͨ���㲥����������
			}