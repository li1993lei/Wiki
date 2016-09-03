package com.lilei.weishi2.engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.lilei.weishi2.domain.TaskBean;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 获取用户进程信息的业务类
 * @author 李小磊
 *
 */
public class TaskManagerEngine {
	
	public static List<TaskBean> getAllRunningTaskInfos(Context context){
		List<TaskBean> datas = new ArrayList<TaskBean>();
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		PackageManager pm = context.getPackageManager();
		List<RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
		for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
			TaskBean bean = new TaskBean();
			//设置包名
			String packageName = runningAppProcessInfo.processName; //包名
			bean.setPackageName(packageName);
			PackageInfo packageInfo = null;
			try {
				packageInfo = pm.getPackageInfo(packageName, 0);
			} catch (NameNotFoundException e) {
				continue;
			}
			//设置图标
			bean.setIcon(packageInfo.applicationInfo.loadIcon(pm));
			//设置程序名称
			bean.setName(packageInfo.applicationInfo.loadLabel(pm)+"");
			if((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0){
				bean.setSystem(true);
			}else {
				bean.setSystem(false);
			}
			//获取程序占用的内存大小
			android.os.Debug.MemoryInfo[] memoryInfo = am.getProcessMemoryInfo(new int[]{runningAppProcessInfo.pid});
			long totalPrivateDirty =  memoryInfo[0].getTotalPrivateDirty() * 1024;
			bean.setMemsize(totalPrivateDirty);
			datas.add(bean);
		}
		return datas;
	}
	/**
	 * @return 获取可用内存的大小
	 */
	public static long getAvailMemSize(Context context) {
		long size = 0;
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		MemoryInfo outInfo = new MemoryInfo();
		// MemoryInfo 存放内存的信息
		am.getMemoryInfo(outInfo);

		// 把kb 转换成byte
		size = outInfo.availMem;

		return size;
	}

	public static long getTotalMemSize(Context context){
		//此种方法试用于16版本以后
 		long size = 0;
//		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE); 
//		MemoryInfo outInfo = new MemoryInfo();
//		am.getMemoryInfo(outInfo);
//		size = outInfo.totalMem;
 		File file = new File("/proc/meminfo");
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			// //zip流 pipe流

			String totalMemInfo = reader.readLine();

			int startIndex = totalMemInfo.indexOf(':');
			int endIndex = totalMemInfo.indexOf('k');
			// 单位是kb
			totalMemInfo = totalMemInfo.substring(startIndex + 1, endIndex)
					.trim();
			size = Long.parseLong(totalMemInfo);
			size *= 1024;// byte单位
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return size;
	}
}
