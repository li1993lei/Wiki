//两者的区别  post发送数据的大小不受限制  并且其请求数据都是写在流里面   
//GET方式    GET方式发送数据的大小受限制  而且其敏感数据易被窃取 
//通常 请求联网的操作 须在子线程中操作   不然容易引起ANR异常 在主线程联网操作 编译会不通过
//更新UI时可以使用Handler或者runOnUIThread()更新UI；


//1  POST方式

	/**
	 * 使用post的方式登录
	 * @param userName
	 * @param password
	 * @return
	 */
	public static String loginOfPost(String userName, String password) {
		HttpURLConnection conn = null;
		try {
			URL url = new URL("http://10.0.2.2:8080/ServerItheima28/servlet/LoginServlet");
			
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("POST");
			conn.setConnectTimeout(10000); // 连接的超时时间
			conn.setReadTimeout(5000); // 读数据的超时时间
			conn.setDoOutput(true);	// 必须设置此方法, 允许输出
//			conn.setRequestProperty("Content-Length", 234);		// 设置请求头消息, 可以设置多个
			
			// post请求的参数
			String data = "username=" + userName + "&password=" + password;
			
			// 获得一个输出流, 用于向服务器写数据, 默认情况下, 系统不允许向服务器输出内容
			OutputStream out = conn.getOutputStream();	
			out.write(data.getBytes());
			out.flush();
			out.close();
			
			int responseCode = conn.getResponseCode();
			if(responseCode == 200) {
				InputStream is = conn.getInputStream();
				//该方法用于把流转换成字符串
				String state = getStringFromInputStream(is);
				return state;
			} else {
				Log.i(TAG, "访问失败: " + responseCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				conn.disconnect();
			}
		}
		return null;
	}



		/**
	 * 根据流返回一个字符串信息
	 * @param is
	 * @return
	 * @throws IOException 
	 */
	private static String getStringFromInputStream(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		
		while((len = is.read(buffer)) != -1) {
			baos.write(buffer, 0, len);
		}
		is.close();
		
		String html = baos.toString();	// 把流中的数据转换成字符串, 采用的编码是: utf-8
		
//		String html = new String(baos.toByteArray(), "GBK");
		
		baos.close();
		return html;
	}














//GET方式去请求数据

		/**
	 * 使用get的方式登录
	 * @param userName
	 * @param password
	 * @return 登录的状态
	 */
	public static String loginOfGet(String userName, String password) {
		HttpURLConnection conn = null;
		try {
			String data = "username=" + URLEncoder.encode(userName) + "&password=" + URLEncoder.encode(password);
			URL url = new URL("http://10.0.2.2:8080/ServerItheima28/servlet/LoginServlet?" + data);
			conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");		// get或者post必须得全大写
			conn.setConnectTimeout(10000); // 连接的超时时间
			conn.setReadTimeout(5000); // 读数据的超时时间
			
			int responseCode = conn.getResponseCode();
			if(responseCode == 200) {
				InputStream is = conn.getInputStream();
				String state = getStringFromInputStream(is);
				return state;
			} else {
				Log.i(TAG, "访问失败: " + responseCode);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(conn != null) {
				conn.disconnect();		// 关闭连接
			}
		}
		return null;
	}