package mawi.muellguidems.activities;

import java.util.ArrayList;
import java.util.HashMap;

import mawi.muellguidems.util.DAO;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class EntsorgungActivity extends Activity {

	private ListView lvEntsorgungMenu;
	private ArrayList<HashMap<String, String>> data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entsorgung);

		lvEntsorgungMenu = (ListView) findViewById(R.id.lvEntsorgung);
		setList();

		lvEntsorgungMenu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				String selectedEntsorgungsartId = data.get(position).get("id");
				String selectedEntsorgungsartBezeichnung = data.get(position)
						.get("bezeichnung");

				Intent intent = new Intent(getBaseContext(),
						EntsorgungStandorteActivity.class);
				intent.putExtra("id", selectedEntsorgungsartId);
				intent.putExtra("bezeichnung", selectedEntsorgungsartBezeichnung);
				startActivity(intent);
				;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.entsorgung, menu);
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

	private void setList() {
		data = DAO.getEntsorgungsartenMitStandortFuerAdapter();

		SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), data,
				R.layout.entsorgung_item, new String[] { "bezeichnung" },
				new int[] { R.id.tvEntsorgungItem });
		lvEntsorgungMenu.setAdapter(adapter);
	}
}
