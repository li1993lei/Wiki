/**
	 * ��ȡ�ֻ��ڴ���ô�С
	 * @return
	 */
	public static long getROMAvail(){
		long romAvail = 0;
		File directory = Environment.getDataDirectory();
		romAvail = directory.getFreeSpace();
		return romAvail;
	}
	/**
	 * ��ȡSD���ڴ���ô�С
	 * @return
	 */
	public static long getSDAvail(){
		long romAvail = 0;
		File directory = Environment.getExternalStorageDirectory();
		romAvail = directory.getFreeSpace();
		return romAvail;
	}