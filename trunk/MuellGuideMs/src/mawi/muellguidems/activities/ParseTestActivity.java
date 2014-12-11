package mawi.muellguidems.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class ParseTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_parse_test);

		fillListView();
	}

	public void onClickBtnSave(View view) {
		EditText input = (EditText) findViewById(R.id.etInput);

		ParseObject testObject = new ParseObject("TestObject");
		testObject.put("Test", input.getText().toString());
		testObject.saveInBackground();
		input.setText("");
		fillListView();
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
		ParseQuery<ParseObject> query = ParseQuery.getQuery("TestObject");
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> objects, ParseException e) {
				if (e == null) {
					ArrayList<String> outputTest = new ArrayList<String>();
					for (ParseObject testObject : objects) {
						String test = testObject.getString("Test");
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
