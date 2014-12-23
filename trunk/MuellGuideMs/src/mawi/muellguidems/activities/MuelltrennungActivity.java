package mawi.muellguidems.activities;

import java.util.ArrayList;
import java.util.HashMap;

import mawi.muellguidems.util.DAO;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;

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

		String itemDetails = "";

		ArrayList<HashMap<String, String>> alleGegenstaende = DAO
				.getAlleGegenstaendeFuerExpandableAdapter();

		// Beinhaltet die übergeordneten Eltern-Items
		ArrayList<HashMap<String, String>> parent = new ArrayList<HashMap<String, String>>();

		// Beinhaltet die SubItems des jeweiligens Eltern-Items
		ArrayList<ArrayList<HashMap<String, String>>> children = new ArrayList<ArrayList<HashMap<String, String>>>();

		// Gehe alle Gegenstände durch...
		for (int i = 0; i < alleGegenstaende.size(); i++) {

			// Erstelle HashMap zum Eltern-Item...
			HashMap<String, String> curGroup = new HashMap<String, String>();
			curGroup.put("bezeichnung",
					alleGegenstaende.get(i).get("bezeichnung"));
			parent.add(curGroup);

			// Erstelle für das Eltern-Item ne Liste mit Children-Items
			ArrayList<HashMap<String, String>> childsOfCurGroup = new ArrayList<HashMap<String, String>>();

			// Gehe alle Attribute des Eltern-Items durch...
			for (String key : alleGegenstaende.get(i).keySet()) {

				if (key.equals("entsorgungsart")) {
					itemDetails += "Entsorgungsart: "
							+ alleGegenstaende.get(i).get(key) + "\r\n";
				} else if (key.equals("hinweis")) {

					itemDetails += "Hinweis: "
							+ alleGegenstaende.get(i).get(key) + "\r\n";

				}

			}
			HashMap<String, String> curGroupChilds = new HashMap<String, String>();
			curGroupChilds.put("itemDetails", itemDetails);
			childsOfCurGroup.add(curGroupChilds);
			children.add(childsOfCurGroup);

			itemDetails = "";

		}

		SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
				getBaseContext(), parent, R.layout.muelltrennung_item,
				new String[] { "bezeichnung" },
				new int[] { R.id.tvMuelltrennungItem }, children,
				R.layout.muelltrennung_item_details,
				new String[] { "itemDetails" },
				new int[] { R.id.tvMuelltrennungItemDetails });

		elvMuelltrennung.setAdapter(adapter);

	}
}
