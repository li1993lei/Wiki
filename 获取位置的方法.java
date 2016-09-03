public class MainActivity extends Activity {

	private LocationManager lm;
	private MyLocationListener listener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);
		listener = new MyLocationListener();
		// 第二个参数：两次位置更新的时间间隔
		lm.requestLocationUpdates("gps", 0, 0, listener);

	}

	class MyLocationListener implements LocationListener {
		// 当位置发生变化 执行者方法
		@Override
		public void onLocationChanged(Location location) {
			float accuracy = location.getAccuracy();// 精确度,以米为单位
			double altitude = location.getAltitude();// 获取海拔高度
			double longitude = location.getLongitude();// 获取经度
			double latitude = location.getLatitude();// 获取纬度
			float speed = location.getSpeed();// 速度
			//定位信息
			StringBuilder tv_mess = new StringBuilder();
			tv_mess.append("accuracy:" + accuracy + "\n");
			tv_mess.append("altitude:" + altitude + "\n");
			tv_mess.append("longitude:" + longitude + "\n");
			tv_mess.append("latitude:" + latitude + "\n");
			tv_mess.append("speed:" + speed + "\n");
			
			//发送短信
			String number = SpTools.getString(getApplicationContext(), MyContant.SAFENUMBER, "");
			SmsManager sm = SmsManager.getDefault();
			sm.sendTextMessage(number, "", tv_mess.toString(), null, null);
			//关闭GPS
			stopSelf();
		}

		// 当某一个位置提供者状态发生变化的时候 关闭--》开启 或者开启--》关闭
		@Override
public void onStatusChanged(String provider, int status, Bundle extras) {

		}

		@Override
		public void onProviderEnabled(String provider) {

		}

		@Override
		public void onProviderDisabled(String provider) {

		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		lm.removeUpdates(listener);
		listener = null;
	}

}



//2 权限
	<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION"/>
	<uses-permission android:name="android.permission.ACCESS_MOCK_LOCATION"/>