// 1. ȡ��������еĺ���
		EditText etNumber = (EditText) findViewById(R.id.number);	// ��������
		String number = etNumber.getText().toString();	// ��Ҫ����ĺ���
// 2. ���ݺ��벦��绰
		Intent intent = new Intent();		// ����һ����ͼ
		intent.setAction(Intent.ACTION_CALL);		// ָ���䶯��Ϊ����绰
		intent.setData(Uri.parse("tel:" + number));	// ָ����Ҫ�����ĺ���
		startActivity(intent);	// ִ���������