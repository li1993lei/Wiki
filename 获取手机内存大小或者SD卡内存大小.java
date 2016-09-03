/**
	 * 获取手机内存可用大小
	 * @return
	 */
	public static long getROMAvail(){
		long romAvail = 0;
		File directory = Environment.getDataDirectory();
		romAvail = directory.getFreeSpace();
		return romAvail;
	}
	/**
	 * 获取SD卡内存可用大小
	 * @return
	 */
	public static long getSDAvail(){
		long romAvail = 0;
		File directory = Environment.getExternalStorageDirectory();
		romAvail = directory.getFreeSpace();
		return romAvail;
	}