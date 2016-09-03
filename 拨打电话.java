// 1. 取出输入框中的号码
		EditText etNumber = (EditText) findViewById(R.id.number);	// 输入框对象
		String number = etNumber.getText().toString();	// 将要拨打的号码
// 2. 根据号码拨打电话
		Intent intent = new Intent();		// 创建一个意图
		intent.setAction(Intent.ACTION_CALL);		// 指定其动作为拨打电话
		intent.setData(Uri.parse("tel:" + number));	// 指定将要拨出的号码
		startActivity(intent);	// 执行这个动作