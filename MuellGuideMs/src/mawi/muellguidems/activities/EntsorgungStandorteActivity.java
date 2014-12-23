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

public class EntsorgungStandorteActivity extends Activity {

	private ListView lvEntsorgungStandorte;
	private ArrayList<HashMap<String, String>> data;

	private String entsorgungsArtId;
	private String entsorgungsArtBezeichnung;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entsorgung_standorte);

		lvEntsorgungStandorte = (ListView) findViewById(R.id.lvEntsorgungStandorte);

		entsorgungsArtId = getIntent().getStringExtra("id");
		entsorgungsArtBezeichnung = getIntent().getStringExtra("bezeichnung");

		data = DAO.getStandortListByIdFuerAdapter(entsorgungsArtId);
		setList();

		lvEntsorgungStandorte.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String selectedStandortId = data.get(position).get("id");
				String selectedStandortBezeichnung = data.get(position).get(
						"bezeichnung");

				Intent intent = new Intent(getBaseContext(),
						StandortDetailsActivity.class);
				intent.putExtra("id", selectedStandortId);
				startActivity(intent);
			}
		});
	}

	private void setList() {
		if (data != null) {
			SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), data,
					R.layout.entsorgung_standorte_item,
					new String[] { "bezeichnung" },
					new int[] { R.id.tvEntsorgungStandort });
			lvEntsorgungStandorte.setAdapter(adapter);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.entsorgung_standorte, menu);
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

	public void onClickBtnAlleStandorteAnzeigen(View v) {
		if (entsorgungsArtId != null) {
			Intent mapsIntent = new Intent(this, MapsActivity.class);
			mapsIntent.putExtra("entsorgungsartId", entsorgungsArtId);
			startActivity(mapsIntent);
		}
	}

}
