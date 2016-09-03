



写入：

writeToLocal("private.txt", Context.MODE_PRIVATE);
		// 可读文件
		writeToLocal("readable.txt", Context.MODE_WORLD_READABLE);
		// 可写文件
		writeToLocal("writeable.txt", Context.MODE_WORLD_WRITEABLE);
		// 可读可写文件
		writeToLocal("readable_writeable.txt", Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE);



		private void writeToLocal(String fileName, int mode) {
		try {
			FileOutputStream fos = openFileOutput(fileName, mode);
			fos.write(("第一个程序写的数据: " + fileName).getBytes());
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}








	读取：
	/**
	 * 读文件
	 * @param fileName
	 * basicPath 路径
	 */
	//传入一个文件名
	private String basicPath = "/data/data/com.itheima28.writedata/files/";
	private void readFile(String fileName) {
		try {
			String path = basicPath + fileName;
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			String text = reader.readLine();
			reader.close();
			Toast.makeText(this, "读取成功: " + text, 0).show();
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "读取失败: " + fileName, 0).show();
		}
	}