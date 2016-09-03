protected void startApk() {
		// TODO Auto-generated method stub
		String packageName = clickBean.getPackageName();

		Intent launchIntentForPackage = pm.getLaunchIntentForPackage(packageName);
		if(launchIntentForPackage!=null){
			startActivity(launchIntentForPackage);
		}else{
			Toast.makeText(getApplicationContext(), "此应用不能被开启", 1).show();
		}
	}