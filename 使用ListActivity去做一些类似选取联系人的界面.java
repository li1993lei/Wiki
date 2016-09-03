public class FriendActivity extends ListActivity {
	
	protected static final int LOADING = 0;
	protected static final int FINISH = 1;
	private List<ContactBean> datas = new ArrayList<ContactBean>();
	private ListView lvData;
	private MyAdapter adapter;
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		lvData = getListView();
		adapter = new MyAdapter();
		lvData.setAdapter(adapter);
		//数据初始化
		initData();
		//初始化事件
		initEvent();
	}
	private void initEvent() {
		// TODO Auto-generated method stub
		lvData.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				ContactBean bean = datas.get(position);
				String phone = bean.getPhone();
				Intent data = new Intent();
				data.putExtra(MyContant.SAFENUMBER, phone);
				//设置数据
				setResult(1, data);
				//关闭自己
				finish();
			}
		});
	}
	private Handler handler = new Handler(){

		private ProgressDialog pd;
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LOADING:
				pd = new ProgressDialog(FriendActivity.this);
				pd.setTitle("WARNING!");
				pd.setMessage("正在加载！");
				pd.show();
				break;
			case FINISH:
				if(pd != null){
					pd.dismiss();
					pd = null;
				}
				//更新数据
				adapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
			
		}
		
	};
	private void initData() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				Message msg = Message.obtain();
				msg.what = LOADING;
				handler.sendMessage(msg);
				//睡两秒
				SystemClock.sleep(2000);
				//获取数据
				datas = ReadContantEngine.readContants(getApplicationContext());
				msg = Message.obtain();
				msg.what = FINISH;
				handler.sendMessage(msg);
			}
		}).start();
	}
	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return datas.size();
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(getApplicationContext(), R.layout.item_friend_listview, null);
			TextView tv_name = (TextView) view.findViewById(R.id.tv_friends_item_name);
			TextView tv_phone = (TextView) view.findViewById(R.id.tv_friends_item_phone);
			ContactBean bean = datas.get(position);
			tv_name.setText("姓名:"+bean.getName());
			tv_phone.setText("号码:"+bean.getPhone());
			return view;
		}
		
		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		
		
	}
}
