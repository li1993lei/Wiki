protected void settingCenter() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(
				"android.settings.APPLICATION_DETAILS_SETTINGS");
		intent.setData(Uri.parse("package:" + clickBean.getPackageName()));
		startActivity(intent);
	}
