package mawi.muellguidems.activities;

import java.util.ArrayList;
import java.util.List;

import mawi.muellguidems.parseobjects.TestObject;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class ParseTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parse_test);

		fillListView();
	}

	public void onClickBtnSave(View view) {
		EditText input = (EditText) findViewById(R.id.etInput);

		TestObject testObject = new TestObject();

		testObject.setTest(input.getText().toString());
		testObject.saveInBackground(new SaveCallback() {
			public void done(ParseException e) {
				fillListView();
			}
		});
		input.setText("");
	}

	public void onClickBtnUpdate(View view) {
		fillListView();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		fillListView();
	}

	private void fillListView() {
		// Parse TestObject auslesen
		ParseQuery<TestObject> query = TestObject.getQuery();
		query.findInBackground(new FindCallback<TestObject>() {

			@Override
			public void done(List<TestObject> objects, ParseException e) {
				if (e == null) {
					ArrayList<String> outputTest = new ArrayList<String>();
					for (TestObject testObject : objects) {
						String test = testObject.getTest();
						outputTest.add(test);
					}
					ListView listView = (ListView) findViewById(R.id.lvOutput);
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							getApplicationContext(),
							android.R.layout.simple_list_item_1, outputTest);
					listView.setAdapter(adapter);
				}
			}

		});
	}
}
