package mawi.muellguidems.activities;

import java.util.ArrayList;

import mawi.muellguidems.adapter.AdapterGroupItem;
import mawi.muellguidems.adapter.CustomMuelltrennungExpandableAdapter;
import mawi.muellguidems.util.DAO;
import android.app.Activity;
import android.os.Bundle;
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

	public void setList() {

		ArrayList<AdapterGroupItem> data = DAO
				.getAlleGegenstaendeFuerExpandableAdapter();

		CustomMuelltrennungExpandableAdapter adapter = null;

		if (data != null) {
			adapter = new CustomMuelltrennungExpandableAdapter(this, data);
		}

		elvMuelltrennung.setAdapter(adapter);

	}
}
