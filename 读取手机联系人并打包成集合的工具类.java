/**
 * 读取联系人的功能类
 * @author 李小磊
 *
 */
public class ReadContantEngine {
	/**
	 * 读取手机联系人
	 */
	public static List<ContactBean> readContants(Context context){
		List<ContactBean> datas = new ArrayList<ContactBean>();
		Uri uriContants = Uri.parse("content://com.android.contacts/contacts");
		Uri uriDatas = Uri.parse("content://com.android.contacts/data");
		
		Cursor cursor = context.getContentResolver().query(uriContants, new String[]{"_id"}, null, null, null);
		//循环取数据 
		while(cursor.moveToNext()){
			//好友信息的封装bean
			ContactBean bean = new ContactBean();
			//直接打印id
			String id = cursor.getString(0);//获取到联系人的id
			
			Cursor cursor2 = context.getContentResolver().query(uriDatas,new String[]{"data1","mimetype"}, " raw_contact_id = ? ", new String[]{id}, null);
			
			//循环每条数据信息都是一个好友的一部分信息
			while(cursor2.moveToNext()) {
				String data  = cursor2.getString(0);
				String mimeType = cursor2.getString(1);
				

				if (mimeType.equals("vnd.android.cursor.item/name")) {
					System.out.println("第" +id + "个用户：名字：" + data);
					bean.setName(data);
				} else if (mimeType.equals("vnd.android.cursor.item/phone_v2")) {
					System.out.println("第" +id + "个用户：电话：" + data);
					bean.setPhone(data);
				}
			}
			cursor2.close();//关闭游标释放资源
			datas.add(bean);//加一条好友信息
		}
		cursor.close();
		return datas;
	}
}
