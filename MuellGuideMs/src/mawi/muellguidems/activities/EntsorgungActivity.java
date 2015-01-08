package mawi.muellguidems.activities;

import java.util.ArrayList;

import mawi.muellguidems.adapter.AdapterSingleItem;
import mawi.muellguidems.adapter.CustomEntsorgungsartenAdapter;
import mawi.muellguidems.util.DAO;
import mawi.muellguidems.util.NetworkIdentifier;
import mawi.muellguidems.util.NetworkIdentifier.NetworkCondition;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class EntsorgungActivity extends BaseActivity {

	private ListView lvEntsorgungMenu;
	private ArrayList<AdapterSingleItem> data;
	ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entsorgung);

		lvEntsorgungMenu = (ListView) findViewById(R.id.lvEntsorgung);

		lvEntsorgungMenu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				try {
					if (data != null) {
						NetworkCondition netzwerkStatus = MuellGuideMsApplication
								.getNetzwerkStatus();
						if (netzwerkStatus == NetworkIdentifier.NetworkCondition.NO_CONNECTION) {
							EntsorgungActivity.this
									.showToastIfNecessary(netzwerkStatus);
							return;
						}

						String selectedEntsorgungsartId = data.get(position)
								.getId();
						String selectedEntsorgungsartBezeichnung = data.get(
								position).getBezeichnung();

						Intent intent = new Intent(getBaseContext(),
								EntsorgungStandorteActivity.class);
						intent.putExtra("id", selectedEntsorgungsartId);
						intent.putExtra("bezeichnung",
								selectedEntsorgungsartBezeichnung);

						startActivity(intent);
					}
					;
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(getBaseContext(),
							getString(R.string.fehler_beim_laden),
							Toast.LENGTH_LONG).show();
				}
			}
		});

		new AsyncParseLoader().execute();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	private class AsyncParseLoader extends
			AsyncTask<Void, Integer, ArrayList<AdapterSingleItem>> {

		protected void onPreExecute() {
			progressDialog = new ProgressDialog(EntsorgungActivity.this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setTitle("Daten werden geladen");
			progressDialog.setMessage("Bitte warten...");
			progressDialog.setIndeterminate(false);
			progressDialog.show();
		}

		protected ArrayList<AdapterSingleItem> doInBackground(Void... params) {

			try {
				data = DAO.getEntsorgungsartenMitStandortFuerAdapter();

				return data;
			}

			catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(getBaseContext(),
						getString(R.string.fehler_beim_laden),
						Toast.LENGTH_LONG).show();

				return null;
			}

		}

		protected void onPostExecute(ArrayList<AdapterSingleItem> result) {
			if (progressDialog.isShowing())
				progressDialog.cancel();

			try {

				if (result != null) {
					CustomEntsorgungsartenAdapter adapter = new CustomEntsorgungsartenAdapter(
							getBaseContext(), result);
					lvEntsorgungMenu.setAdapter(adapter);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
