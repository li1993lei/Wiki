import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

public class SecondClass extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//获取SD卡路径
		File directory = Environment.getExternalStorageDirectory();
		String sdmeMoryInfo = getMemoryInfo(directory);
		
		//手机内存路径
		File dataDirectory = Environment.getDataDirectory();
		String dataMemoryInfo = getMemoryInfo(dataDirectory);
		
		//拿到数据后 再对其进行其他逻辑的处理
	}

	private String getMemoryInfo(File directory) {
		// TODO Auto-generated method stub
		StatFs statFs = new StatFs(directory.toString());
		long blockCount = statFs.getBlockCount();  //扇区数量
		long blockSize = statFs.getBlockSize();   //扇区大小
		
		long availableBlocks = statFs.getAvailableBlocks();//可用扇区数量
		
		String totalMemory = Formatter.formatFileSize(this, blockCount*blockSize);  //总内存大小
		String availableMemory = Formatter.formatFileSize(this, availableBlocks*blockSize);  //可用内存大小
		return "总内存大小"+totalMemory+"可用内存大小"+availableBlocks;
	}
}
