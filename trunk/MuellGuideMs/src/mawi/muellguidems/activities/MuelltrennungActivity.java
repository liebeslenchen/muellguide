package mawi.muellguidems.activities;

import java.util.ArrayList;

import mawi.muellguidems.adapter.AdapterGroupItem;
import mawi.muellguidems.adapter.CustomExpandableAdapter;
import mawi.muellguidems.util.DAO;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

public class MuelltrennungActivity extends Activity {

	ExpandableListView elvMuelltrennung;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_muelltrennung);

		elvMuelltrennung = (ExpandableListView) findViewById(R.id.elvMuelltrennung);
		setList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.muelltrennung, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void setList() {

		ArrayList<AdapterGroupItem> data = DAO
				.getAlleGegenstaendeFuerExpandableAdapter();
		CustomExpandableAdapter adapter = new CustomExpandableAdapter(this,
				data);

		elvMuelltrennung.setAdapter(adapter);

	}
}
