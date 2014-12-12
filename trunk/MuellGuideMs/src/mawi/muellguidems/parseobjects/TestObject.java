package mawi.muellguidems.parseobjects;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

@ParseClassName("TestObject")
public class TestObject extends ParseObject {

	public TestObject() {
	}

	public String getTest() {
		return getString("Test");
	}

	public void setTest(String value) {
		put("Test", value);
	}

	public static ParseQuery<TestObject> getQuery() {
		return ParseQuery.getQuery(TestObject.class);
	}
}
