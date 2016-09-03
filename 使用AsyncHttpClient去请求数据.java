//1 Post方式

	public void doPost(View v) {
		final String userName = etUserName.getText().toString();
		final String password = etPassword.getText().toString();
		
		AsyncHttpClient client = new AsyncHttpClient();

        RequestParams params = new RequestParams();
        params.put("username", userName);
        params.put("password", password);
        
        client.post("http://10.0.2.2:8080/ServerItheima28/servlet/LoginServlet", 
        		params, 
        		new MyResponseHandler());
	}





//内部类继承AsyncHttpResponseHandler

	class MyResponseHandler extends AsyncHttpResponseHandler {

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				byte[] responseBody) {
//			Log.i(TAG, "statusCode: " + statusCode);
			
			Toast.makeText(MainActivity2.this, 
					"成功: statusCode: " + statusCode + ", body: " + new String(responseBody), 
					0).show();
		}

		@Override
		public void onFailure(int statusCode, Header[] headers,
				byte[] responseBody, Throwable error) {
			Toast.makeText(MainActivity2.this, "失败: statusCode: " + statusCode, 0).show();
		}
    }






//2方式请求数据   
   public void doGet(View v) {
		final String userName = etUserName.getText().toString();
		final String password = etPassword.getText().toString();

		AsyncHttpClient client = new AsyncHttpClient();

		String data = "username=" + URLEncoder.encode(userName) + "&password="
				+ URLEncoder.encode(password);

		client.get("http://10.0.2.2:8080/ServerItheima28/servlet/LoginServlet?"
				+ data, new MyResponseHandler());
	}