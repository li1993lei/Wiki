// 1 dao层的处理   判断版本号是否是最新  和添加数据
public static final String PATH = "data/data/com.lilei.weishi2/files/antivirus.db";
	/**
	 * 查询文件是否是病毒
	 * @param md5
	 * 要查询文件的MD5值
	 * @return
	 * 是否是病毒
	 */
	public static boolean isVirus(String md5){
		
		boolean res = false;
		SQLiteDatabase db = SQLiteDatabase.openDatabase(PATH, null, SQLiteDatabase.OPEN_READONLY);
		Cursor cursor = db.rawQuery("select 1 from datable where md5 =?", new String[]{md5});
		if (cursor.moveToNext()) {
			res = true;
		}
		cursor.close();
		db.close();
		return res;
	}
	/**
	 * @param version
	 *     传递的服务器的病毒库版本
	 * @return
	 *    病毒库版本是否一致
	 */
	public static boolean isNewVirus(int version){
		boolean res = false;
		SQLiteDatabase database = SQLiteDatabase.openDatabase(
				PATH, null,
				SQLiteDatabase.OPEN_READWRITE);
		Cursor cursor = database.rawQuery("select 1 from version where subcnt=?", new String[]{version+""});
		if (cursor.moveToNext()) {
			res = true;
		}
		cursor.close();
		database.close();
		return res;
	}
	/**
	 * @param md5   病毒文件的md5
	 *        desc  病毒的描述信息
	 *     
	 */
	public static void addVirus(String md5,String desc){
		//获取数据库 /data/data/com.ith
		SQLiteDatabase database = SQLiteDatabase.openDatabase(
				PATH, null,
				SQLiteDatabase.OPEN_READWRITE);
		ContentValues values = new ContentValues();
		values.put("md5", md5);
		values.put("type", 6);
		values.put("name", "com.itheima62.mobilesafe");
		values.put("desc", desc);
		database.insert("datable", null, values);
		database.close();
	}







//2 使用HttpUtils联网查询数据  并处理
	//处理等待联网显示的文本变化
	private String getPoints(int j) {
		String res = "";
		for (int i = 0; i < j; i++) {
			res += ".";
		}
		return res;
	}
	private void checkVersion() {
		tvPackageName.setText("正在尝试联网中......");
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				connecting = true;
				int i = 0;
				while(connecting){
					i++;
					final int j = i % 6 + 1;// i 1 到 6
					runOnUiThread(new Runnable() {
						public void run() {
							tvPackageName.setText("正在尝试联网" + getPoints(j));
						}
					});
					SystemClock.sleep(200);
				}
				
			}
		}).start();
		
		HttpUtils utils = new HttpUtils();
		utils.configTimeout(5000);
		utils.send(HttpMethod.GET, MyContant.GETVERSION, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				connecting = false;
				Toast.makeText(getApplicationContext(), "联网失败", 1).show();
				initData();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				connecting = false;
				String version =  arg0.result;
				if (dao.isNewVirus(Integer.parseInt(version))) {
					Toast.makeText(getApplicationContext(), "病毒库最新", 1).show();
					initData();
				}else {
					showUpDataDialong();
				}
			}
		});
	}
//弹出升级对话框  并对对话框的操作进行处理
	protected void showUpDataDialong() {
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		ab.setTitle("有新病毒")
		  .setMessage("是否更新病毒库？")
		  .setNegativeButton("取消", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				initData();
			}
		})
		.setPositiveButton("确定", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				HttpUtils utils = new HttpUtils();
				utils.configTimeout(5000);
				utils.send(HttpMethod.GET, MyContant.GETVIRUE, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						initData();
					}

					@SuppressWarnings("static-access")
					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						String versionjson =  arg0.result;
						try {
							JSONObject jsonObject = new JSONObject(versionjson);
							String md5 = jsonObject.getString("md5");
							String desc = jsonObject.getString("desc");
							dao.addVirus(md5, desc);
							Toast.makeText(getApplicationContext(), "更新病毒库成功", 1).show();
							initData();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					}
				});
				
			}
		});
		ab.create().show();
	}