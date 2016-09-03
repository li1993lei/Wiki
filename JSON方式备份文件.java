	public interface BackUpProgress{
		/**
		 * 初始化显示控件 
		 */
		void show();
		/**
		 * 设置最大值
		 * @param max
		 */
		void setMax(int max);
		/**
		 * 设置当前进度
		 * @param progress
		 */
		void setProgress(int progress);
		/**
		 * 结束时调用
		 */
		void end();
	}	










		public static void backUpSmsForJSON(Activity context,final BackUpProgress pd) {
		Uri uri = Uri.parse("content://sms");
		final Cursor cursor = context.getContentResolver().query(uri,
				new String[] { "address", "date", "type", "body" }, null, null,
				"_id desc");
		File file = new File(Environment.getExternalStorageDirectory(), "sms.json");
		try {
			PrintWriter pw = new PrintWriter(file);
			context.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					pd.show();
					pd.setMax(cursor.getCount());
				}
			});
			final PD pd1 = new PD();
			//写根标记     {"count":"10"
			pw.println("{\"count\":\""+cursor.getCount()+"\"");
			pw.println(",\"smses\":[");
			while(cursor.moveToNext()){
				pd1.progress++;
				SystemClock.sleep(100);
				//取短信
				if (cursor.getPosition() == 0) {
					pw.println("{");
				} else {
					pw.println(",{");
				}
				//address 封装  "address":"hello"
				pw.println("\"address\":\"" + cursor.getString(0) + "\"," );
				//date 封装
				pw.println("\"date\":\"" + cursor.getString(1) + "\"," );
				//body 封装
				pw.println("\"body\":\"" + cursor.getString(2) + "\"," );
				//type 封装
				pw.println("\"type\":\"" + cursor.getString(3) + "\"" );
				if (cursor.getPosition() != cursor.getCount() - 1) {
					pw.println("}");
				}else {
					pw.println("]}");
				}
				
				context.runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						pd.setProgress(pd1.progress);
					}
				});
			}
			context.runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					pd.end();
				}
			});
			pw.flush();
			pw.close();
			cursor.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}




	private static class  PD{
		int progress;
	}