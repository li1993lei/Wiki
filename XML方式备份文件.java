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










	public static void backUpSmsForXML(Activity context,final BackUpProgress pd) {
		Uri uri = Uri.parse("content://sms");
		final Cursor cursor = context.getContentResolver().query(uri,
				new String[] { "address", "date", "type", "body" }, null, null,
				"_id desc");
		File file = new File(Environment.getExternalStorageDirectory(), "sms.xml");
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
			pw.println("<smss count='"+ cursor.getCount() +"'>");
			while(cursor.moveToNext()){
				pd1.progress++;
				SystemClock.sleep(200);
				pw.println("<sms>");
				pw.println("<address>"+cursor.getString(0)+"</address>");
				pw.println("<date>"+cursor.getString(1)+"</date>");
				pw.println("<type>"+cursor.getString(2)+"</type>");
				pw.println("<body>"+cursor.getString(3)+"</body>");
				pw.println("</sms>");
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
			pw.println("</smss>");
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