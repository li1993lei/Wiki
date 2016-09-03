	public static List<AppBean> getApks(Context context){
		List<AppBean> apks = new ArrayList<AppBean>();
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> packages = pm.getInstalledPackages(0);
		for (PackageInfo packageInfo : packages) {
			AppBean bean = new AppBean();
			//设置包名
			bean.setPackageName(packageInfo.packageName);
			//设置图标
			bean.setIcon(packageInfo.applicationInfo.loadIcon(pm));
			//设置程序名
			bean.setApkName(packageInfo.applicationInfo.loadLabel(pm)+"");
			//设置程序占用的大小
			String sourceDir = packageInfo.applicationInfo.sourceDir;
			File file = new File(sourceDir);
			bean.setSize(file.length());
			//标记是否是系统软件
			int flags = packageInfo.applicationInfo.flags;
			if ((flags & ApplicationInfo.FLAG_SYSTEM) != 0) { //是系统软件
				bean.setSystem(true);
			}else {
				bean.setSystem(false);
			}
			//标记程序是否安卓在SD卡
			if ((flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) { //是系统软件
				bean.setSDcard(true);
			}else {
				bean.setSDcard(false);
			}
			apks.add(bean);
		}
		return apks;
	}