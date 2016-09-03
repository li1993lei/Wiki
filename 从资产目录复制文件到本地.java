	private void copyDB() {
		// TODO Auto-generated method stub
		File file = new File(getFilesDir(), "address.db");
		if (file.exists() && file.length() > 0) {
			return;
		} else {

			try {
				AssetManager am = getAssets();
				InputStream is = am.open("address.db");

				FileOutputStream fos = new FileOutputStream(file);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
				}
				is.close();
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
