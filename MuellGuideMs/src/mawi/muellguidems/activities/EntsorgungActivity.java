package mawi.muellguidems.activities;

import java.util.ArrayList;

import mawi.muellguidems.adapter.AdapterSingleItem;
import mawi.muellguidems.adapter.CustomEntsorgungsartenAdapter;
import mawi.muellguidems.util.DAO;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class EntsorgungActivity extends Activity {

	private ListView lvEntsorgungMenu;
	private ArrayList<AdapterSingleItem> data;

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

				String selectedEntsorgungsartId = data.get(position).getId();
				String selectedEntsorgungsartBezeichnung = data.get(position)
						.getBezeichnung();

				Intent intent = new Intent(getBaseContext(),
						EntsorgungStandorteActivity.class);
				intent.putExtra("id", selectedEntsorgungsartId);
				intent.putExtra("bezeichnung",
						selectedEntsorgungsartBezeichnung);
				startActivity(intent);
				;
			}
		});
	}

	private void setList() {
		data = DAO.getEntsorgungsartenMitStandortFuerAdapter();

		CustomEntsorgungsartenAdapter adapter = new CustomEntsorgungsartenAdapter(
				getBaseContext(), data);
		lvEntsorgungMenu.setAdapter(adapter);
	}
}
