package mawi.muellguidems.activities;

import java.util.ArrayList;

import mawi.muellguidems.adapter.AdapterSingleItem;
import mawi.muellguidems.adapter.CustomEntsorgungStandortAdapter;
import mawi.muellguidems.util.DAO;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

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
			Intent mapsIntent = new Intent(this, MapsActivity.class);
			mapsIntent.putExtra("entsorgungsartId", entsorgungsArtId);
			startActivity(mapsIntent);
		}
	}

}
