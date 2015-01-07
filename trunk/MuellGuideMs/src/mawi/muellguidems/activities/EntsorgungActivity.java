package mawi.muellguidems.activities;

import java.util.ArrayList;

import mawi.muellguidems.adapter.AdapterSingleItem;
import mawi.muellguidems.adapter.CustomEntsorgungsartenAdapter;
import mawi.muellguidems.util.DAO;
import mawi.muellguidems.util.NetworkIdentifier;
import mawi.muellguidems.util.NetworkIdentifier.NetworkCondition;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class EntsorgungActivity extends BaseActivity {

	private ListView lvEntsorgungMenu;
	private ArrayList<AdapterSingleItem> data;

	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entsorgung);

		progressDialog = new ProgressDialog(this);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.setTitle("Daten werden geladen");
		progressDialog.setMessage("Bitte warten...");
		progressDialog.setIndeterminate(true);

		lvEntsorgungMenu = (ListView) findViewById(R.id.lvEntsorgung);
		setList();

		lvEntsorgungMenu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				NetworkCondition netzwerkStatus = MuellGuideMsApplication
						.getNetzwerkStatus();
				if (netzwerkStatus == NetworkIdentifier.NetworkCondition.NO_CONNECTION) {
					EntsorgungActivity.this
							.showToastIfNecessary(netzwerkStatus);
					return;
				}

				String selectedEntsorgungsartId = data.get(position).getId();
				String selectedEntsorgungsartBezeichnung = data.get(position)
						.getBezeichnung();

				Intent intent = new Intent(getBaseContext(),
						EntsorgungStandorteActivity.class);
				intent.putExtra("id", selectedEntsorgungsartId);
				intent.putExtra("bezeichnung",
						selectedEntsorgungsartBezeichnung);

				progressDialog.show();
				startActivity(intent);
				;
			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (progressDialog.isShowing())
			progressDialog.cancel();
	}

	private void setList() {
		data = DAO.getEntsorgungsartenMitStandortFuerAdapter();

		CustomEntsorgungsartenAdapter adapter = new CustomEntsorgungsartenAdapter(
				getBaseContext(), data);
		lvEntsorgungMenu.setAdapter(adapter);
	}
}
