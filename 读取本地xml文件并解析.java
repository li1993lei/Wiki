	private List<Person> parserXmlFromLocal() {
		
		try {
			File path = new File(Environment.getExternalStorageDirectory(),
					"persons.xml");
			//File path = new File(Environment.getDataDirectory(),
					"persons.xml");
			FileInputStream fis = new FileInputStream(path);

			// 获得pull解析器对象
			XmlPullParser parser = Xml.newPullParser();
			// 指定解析的文件和编码格式
			parser.setInput(fis, "utf-8");

			int eventType = parser.getEventType(); // 获得事件类型

			List<Person> personList = null;
			Person person = null;
			String id;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				String tagName = parser.getName(); // 获得当前节点的名称

				switch (eventType) {
				case XmlPullParser.START_TAG: // 当前等于开始节点 <person>
					if ("persons".equals(tagName)) { // <persons>
						personList = new ArrayList<Person>();
					} else if ("person".equals(tagName)) { // <person id="1">
						person = new Person();
						id = parser.getAttributeValue(null, "id");
						person.setId(Integer.valueOf(id));
					} else if ("name".equals(tagName)) { // <name>
						person.setName(parser.nextText());
					} else if ("age".equals(tagName)) { // <age>
						person.setAge(Integer.parseInt(parser.nextText()));
					}
					break;
				case XmlPullParser.END_TAG: // </persons>
					if ("person".equals(tagName)) {
						// 需要把上面设置好值的person对象添加到集合中
						personList.add(person);
					}
					break;
				default:
					break;
				}

				eventType = parser.next(); // 获得下一个事件类型
			}
			return personList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}