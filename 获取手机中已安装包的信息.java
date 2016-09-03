	public static List<AppBean> getApks(Context context){
		List<AppBean> apks = new ArrayList<AppBean>();
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> packages = pm.getInstalledPackages(0);
		for (PackageInfo packageInfo : packages) {
			AppBean bean = new AppBean();
			//���ð���
			bean.setPackageName(packageInfo.packageName);
			//����ͼ��
			bean.setIcon(packageInfo.applicationInfo.loadIcon(pm));
			//���ó�����
			bean.setApkName(packageInfo.applicationInfo.loadLabel(pm)+"");
			//���ó���ռ�õĴ�С
			String sourceDir = packageInfo.applicationInfo.sourceDir;
			File file = new File(sourceDir);
			bean.setSize(file.length());
			//����Ƿ���ϵͳ���
			int flags = packageInfo.applicationInfo.flags;
			if ((flags & ApplicationInfo.FLAG_SYSTEM) != 0) { //��ϵͳ���
				bean.setSystem(true);
			}else {
				bean.setSystem(false);
			}
			//��ǳ����Ƿ�׿��SD��
			if ((flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) { //��ϵͳ���
				bean.setSDcard(true);
			}else {
				bean.setSDcard(false);
			}
			apks.add(bean);
		}
		return apks;
	}