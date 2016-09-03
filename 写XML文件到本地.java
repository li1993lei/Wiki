

	private List<Person> getPersonList() {
		List<Person> personList = new ArrayList<Person>();
		
		for (int i = 0; i < 30; i++) {
			personList.add(new Person(i, "wang" + i, 18 + i));
		}
		
		return personList;
	}

List<Person> personList = getPersonList();  //该方法用于添加listView的数据
		
		// 获得序列化对象
		XmlSerializer serializer = Xml.newSerializer();
		
		try {
			File path = new File(Environment.getExternalStorageDirectory(), "persons.xml");
			FileOutputStream fos = new FileOutputStream(path);
			// 指定序列化对象输出的位置和编码
			serializer.setOutput(fos, "utf-8");
			
			serializer.startDocument("utf-8", true);	// 写开始 <?xml version='1.0' encoding='utf-8' standalone='yes' ?>
			
			serializer.startTag(null, "persons");		// <persons>
			
			for (Person person : personList) {
				// 开始写人

				serializer.startTag(null, "person");	// <person>
				//属性
				serializer.attribute(null, "id", String.valueOf(person.getId()));
				
				// 写名字
				serializer.startTag(null, "name");		// <name>
				serializer.text(person.getName());
				serializer.endTag(null, "name");		// </name>
				
				// 写年龄
				serializer.startTag(null, "age");		// <age>
				serializer.text(String.valueOf(person.getAge()));
				serializer.endTag(null, "age");		// </age>
				
				serializer.endTag(null, "person");	// </person>
			}
			
			serializer.endTag(null, "persons");			// </persons>
			
			serializer.endDocument();		// 结束
		} catch (Exception e) {
			e.printStackTrace();
		}