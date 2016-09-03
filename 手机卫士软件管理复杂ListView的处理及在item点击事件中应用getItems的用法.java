package com.lilei.weishi2.activities;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.lilei.weishi2.R;
import com.lilei.weishi2.domain.AppBean;
import com.lilei.weishi2.engine.AppmanagerEngine;

public class ApkManagerActivity extends Activity {

	protected static final int LOADING = 0;
	protected static final int FINISH = 1;
	private TextView tv_sdAvail;
	private TextView tv_romAvail;
	private ListView lv_datas;
	private ProgressBar pb_loading;
	private MyAdapter adapter;
	private long sdAvail;  //sd卡可用内存
	private long romAvail; //rom可用内存
	AppBean clickBean;  //点击的时候保存的bean
	PackageManager pm;

	// 用户apk的容器
	private List<AppBean> userApks = new ArrayList<AppBean>();

	// 系统的apk容器
	private List<AppBean> sysApks = new ArrayList<AppBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(); // 界面初始化
		initData(); // 数据初始化
		initEvent(); // 事件初始化
		initPopWindow();// 初始化弹出窗体
	}
	//初始化popwindow
	private void initPopWindow() {
		//实例化弹出窗体包裹的view
		popView = View.inflate(getApplicationContext(), R.layout.popup_appmanager, null);
		
		LinearLayout ll_remove = (LinearLayout) popView
				.findViewById(R.id.ll_appmanager_pop_remove);
		LinearLayout ll_setting = (LinearLayout) popView
				.findViewById(R.id.ll_appmanager_pop_setting);
		LinearLayout ll_share = (LinearLayout) popView
				.findViewById(R.id.ll_appmanager_pop_share);
		LinearLayout ll_start = (LinearLayout) popView
				.findViewById(R.id.ll_appmanager_pop_start);
		View.OnClickListener listener = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.ll_appmanager_pop_remove:// 卸载软件
				//	removeApk();// 卸载apk
					System.out.println("卸载");
					break;
				case R.id.ll_appmanager_pop_setting:// 设置中心
				 	settingCenter();// 设置中心
					System.out.println("设置");
					break;
				case R.id.ll_appmanager_pop_share:// 软件分享
				 	shareApk();// 软件分享
					System.out.println("分享");
					break;
				case R.id.ll_appmanager_pop_start:// 启动软件
			 		startApk();// 启动软件
					System.out.println("启动");
					break;

				default:
					break;
				}
			}
		};
		ll_remove.setOnClickListener(listener);
		ll_setting.setOnClickListener(listener);
		ll_share.setOnClickListener(listener);
		ll_start.setOnClickListener(listener);
		//-2代表包裹内容
		pw = new PopupWindow(popView, -2, -2);
		pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); //设置透明色
		//定义矢量动画
		sa = new ScaleAnimation(0f, 1.0f, 0.5f, 1.0f, 
				Animation.RELATIVE_TO_SELF, 0f, 
				Animation.RELATIVE_TO_SELF, 0.5f);
		//定义动画显示市场
		sa.setDuration(300);
	}
	//启动另外一个程序的方法
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
	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(getString(R.string.share));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl("http://sharesdk.cn");
		// text是分享文本，所有平台都需要这个字段
		oks.setText("测试1");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		oks.setImagePath("/sdcard/Download/jumbo.jpg");// 确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl("http://sharesdk.cn");
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("我是单身美女，过来约我");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite(getString(R.string.app_name));
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl("http://sharesdk.cn");

		// 启动分享GUI
		oks.show(this);
	}

	protected void shareApk() {
		showShare();
		
	}

	protected void settingCenter() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(
				"android.settings.APPLICATION_DETAILS_SETTINGS");
		intent.setData(Uri.parse("package:" + clickBean.getPackageName()));
		startActivity(intent);
	}

	private Handler handler = new Handler() {
		@SuppressLint("HandlerLeak")
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LOADING:
				pb_loading.setVisibility(View.VISIBLE);
				lv_datas.setVisibility(View.GONE);// 隐藏listview
				tv_listview_lable.setVisibility(View.GONE);
				break;
			case FINISH:
				pb_loading.setVisibility(View.GONE);
				lv_datas.setVisibility(View.VISIBLE);// 隐藏listview
				tv_listview_lable.setVisibility(View.VISIBLE);

				tv_sdAvail.setText("ROM可用空间"
						+ Formatter.formatFileSize(getApplicationContext(),
								sdAvail));
				tv_romAvail.setText("SD卡可用空间"
						+ Formatter.formatFileSize(getApplicationContext(),
								romAvail));
				tv_listview_lable.setText("用户软件" + userApks.size());
				adapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
		};
	};
	private TextView tv_listview_lable;
	private View popView;
	private PopupWindow pw;
	private ScaleAnimation sa;

	private void initEvent() {
		lv_datas.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 处理标签点击事件 用户标签设置可点击 消耗点击事件 不让事件继续传递
				/*if (position == userApks.size() + 1) {
					return;
				} else if (position <= userApks.size()) {
					clickBean = userApks.get(position - 1);
				} else {
					clickBean = sysApks.get(position - 1 - userApks.size() - 1);
				}*/
				clickBean = (AppBean)lv_datas.getItemAtPosition(position);
				System.out.println(clickBean.getApkName());
				int[] location = new int[2];
				view.getLocationInWindow(location);
				showPopWindow(view,location[0]+100,location[1]); // 弹出窗体
			}
		});
		/**
		 * 滑动时实时改变标签上的文本
		 */
		lv_datas.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				closePopWindow();
				if (firstVisibleItem >= userApks.size() + 1) {
					tv_listview_lable.setText("系统软件" + sysApks.size());
				} else {
					tv_listview_lable.setText("用户软件" + userApks.size());
				}

			}
		});
	}

	protected void showPopWindow(View parent,int x,int y) {
		closePopWindow();
		pw.showAtLocation(parent, Gravity.LEFT | Gravity.TOP, x, y);
		popView.startAnimation(sa);
	}
	protected void closePopWindow() {
		// TODO Auto-generated method stub
		if (pw != null && pw.isShowing()) {
			pw.dismiss();
		}
	}
	private void initData() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {

			@Override
			public void run() {
				handler.obtainMessage(LOADING).sendToTarget();
				List<AppBean> apks = AppmanagerEngine
						.getApks(getApplicationContext());
				// 给软件分类
				for (AppBean appBean : apks) {
					if (appBean.isSystem()) {
						sysApks.add(appBean);
					} else {
						userApks.add(appBean);
					}
				}
				sdAvail = AppmanagerEngine.getSDAvail();
				romAvail = AppmanagerEngine.getROMAvail();
				handler.obtainMessage(FINISH).sendToTarget();
			}
		}).start();
	}

	private void initView() {
		setContentView(R.layout.activity_apk_manager);
		tv_sdAvail = (TextView) findViewById(R.id.tv_appmanager_sdsize);

		tv_romAvail = (TextView) findViewById(R.id.tv_appmanager_romsize);

		lv_datas = (ListView) findViewById(R.id.lv_appmanager_appdatas);

		pb_loading = (ProgressBar) findViewById(R.id.pb_appmanager_loading);
		tv_listview_lable = (TextView) findViewById(R.id.tv_appmanager_listview_lable);
		adapter = new MyAdapter();
		lv_datas.setAdapter(adapter);
		pm = getPackageManager();
	}

	private class ViewHolder {
		ImageView iv_icon;
		TextView tv_title;
		TextView tv_location;
		TextView tv_size;
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return userApks.size() + 1 + sysApks.size() + 1;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (position == 0) {
				TextView tv_userTable = new TextView(getApplicationContext());
				tv_userTable.setText("用户软件(" + userApks.size() + ")");
				tv_userTable.setTextColor(Color.WHITE);// 文字为白色
				tv_userTable.setBackgroundColor(Color.GRAY);// 文字背景为灰色
				return tv_userTable;
			} else if (position == userApks.size() + 1) {
				TextView tv_userTable = new TextView(getApplicationContext());
				tv_userTable.setText("系统软件(" + sysApks.size() + ")");
				tv_userTable.setTextColor(Color.WHITE);// 文字为白色
				tv_userTable.setBackgroundColor(Color.GRAY);// 文字背景为灰色
				return tv_userTable;
			} else {
				ViewHolder holder = new ViewHolder();
				if (convertView != null
						&& convertView instanceof RelativeLayout) {
					holder = (ViewHolder) convertView.getTag();
				} else {
					convertView = View.inflate(getApplicationContext(),
							R.layout.item_appmanager_listview_item, null);

					holder.iv_icon = (ImageView) convertView
							.findViewById(R.id.iv_appmanager_listview_item_icon);
					holder.tv_title = (TextView) convertView
							.findViewById(R.id.tv_appmanager_listview_item_title);
					holder.tv_location = (TextView) convertView
							.findViewById(R.id.tv_appmanager_listview_item_location);
					holder.tv_size = (TextView) convertView
							.findViewById(R.id.tv_appmanager_listview_item_size);
					// 绑定tag
					convertView.setTag(holder);
				}
				AppBean appBean = getItem(position);
				holder.iv_icon.setImageDrawable(appBean.getIcon());
				holder.tv_title.setText(appBean.getApkName());
				holder.tv_title.setTextColor(Color.BLACK);
				holder.tv_size.setText(Formatter.formatFileSize(
						getApplicationContext(), appBean.getSize()));
				if (appBean.isSDcard()) {
					holder.tv_location.setText("SD卡存储");
					holder.tv_location.setTextColor(Color.RED);
				} else {
					holder.tv_location.setText("ROM存储");
					holder.tv_location.setTextColor(Color.GREEN);
				}

			}
			return convertView;

		}

		@Override
		public AppBean getItem(int position) {
			AppBean appBean = null;
			if (position <= userApks.size()) {
				appBean = userApks.get(position - 1);
			} else {
				appBean = sysApks.get(position - 1 - userApks.size() - 1);
			}
			return appBean;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

	}
}
