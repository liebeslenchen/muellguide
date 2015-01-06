package mawi.muellguidems.activities;

import java.util.ArrayList;

import mawi.muellguidems.adapter.AdapterSingleItem;
import mawi.muellguidems.adapter.CustomEntsorgungStandortAdapter;
import mawi.muellguidems.parseobjects.Entsorgungsart;
import mawi.muellguidems.util.DAO;
import mawi.muellguidems.util.EntsorgungsartUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class EntsorgungStandorteActivity extends Activity {

	private ListView lvEntsorgungStandorte;
	private ArrayList<AdapterSingleItem> data;

	private String entsorgungsArtId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entsorgung_standorte);

		lvEntsorgungStandorte = (ListView) findViewById(R.id.lvEntsorgungStandorte);
		entsorgungsArtId = getIntent().getStringExtra("id");

		data = DAO.getStandortListByIdFuerAdapter(entsorgungsArtId);
		setList();

		lvEntsorgungStandorte.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String selectedStandortId = data.get(position).getId();

				Intent intent = new Intent(getBaseContext(),
						StandortDetailsActivity.class);
				intent.putExtra("id", selectedStandortId);
				startActivity(intent);
			}
		});

		// Titeltext und Icon setzten, je nach Entsorgungsart
		Entsorgungsart entsorgungsart = EntsorgungsartUtil.ENTSORGUNGSART_HASH_MAP
				.get(entsorgungsArtId);
		setTitle(entsorgungsart.getBezeichnung() + " Standorte");
		getActionBar().setIcon(R.drawable.entsorgung_white);
	}

	private void setList() {
		if (data != null) {
			CustomEntsorgungStandortAdapter adapter = new CustomEntsorgungStandortAdapter(
					getBaseContext(), data);
			lvEntsorgungStandorte.setAdapter(adapter);
		}
	}

	public void onClickBtnAlleStandorteAnzeigen(View v) {
		if (entsorgungsArtId != null) {
			Toast.makeText(EntsorgungStandorteActivity.this,
					"Nur Standorte mit genauen Ortsangaben werden angezeigt",
					Toast.LENGTH_LONG).show();
			Intent mapsIntent = new Intent(this, MapsActivity.class);
			mapsIntent.putExtra("entsorgungsartId", entsorgungsArtId);
			startActivity(mapsIntent);
		}
	}

}
