package mawi.muellguidems.activities;

import java.util.ArrayList;

import mawi.muellguidems.adapter.AdapterSingleItem;
import mawi.muellguidems.adapter.CustomEntsorgungStandortAdapter;
import mawi.muellguidems.parseobjects.Entsorgungsart;
import mawi.muellguidems.util.DAO;
import mawi.muellguidems.util.EntsorgungsartUtil;
import mawi.muellguidems.util.NetworkIdentifier;
import mawi.muellguidems.util.NetworkIdentifier.NetworkCondition;
import mawi.muellguidems.util.SettingUtils;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class EntsorgungStandorteActivity extends BaseActivity {

	private ListView lvEntsorgungStandorte;
	private ArrayList<AdapterSingleItem> data;
	private CustomEntsorgungStandortAdapter adapter;
	private EditText etSearchStandort;
	private String entsorgungsArtId;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_entsorgung_standorte);

		lvEntsorgungStandorte = (ListView) findViewById(R.id.lvEntsorgungStandorte);
		entsorgungsArtId = getIntent().getStringExtra("id");

		lvEntsorgungStandorte.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				NetworkCondition netzwerkStatus = MuellGuideMsApplication
						.getNetzwerkStatus();
				if (netzwerkStatus == NetworkIdentifier.NetworkCondition.NO_CONNECTION) {
					MuellGuideMsApplication.showToastIfNecessary(
							getBaseContext(), netzwerkStatus);
					return;
				}

				try {
					if (data != null) {
						String selectedStandortId = adapter.getItem(position)
								.getId();

						Intent intent = new Intent(getBaseContext(),
								StandortDetailsActivity.class);
						intent.putExtra("id", selectedStandortId);
						startActivity(intent);
					}
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(getBaseContext(),
							getString(R.string.fehler_beim_laden),
							Toast.LENGTH_LONG).show();
				}
			}
		});

		etSearchStandort = (EditText) findViewById(R.id.etSearchStandort);

		etSearchStandort.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				// When user changed the Text
				try {
					if (adapter != null) {
						EntsorgungStandorteActivity.this.adapter.getFilter()
								.filter(cs);
					}
				} catch (Exception ex) {
					Toast.makeText(getBaseContext(), ex.toString(),
							Toast.LENGTH_LONG);
				}

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
			}
		});

		// Titeltext und Icon setzen, je nach Entsorgungsart
		Entsorgungsart entsorgungsart = EntsorgungsartUtil.ENTSORGUNGSART_HASH_MAP
				.get(entsorgungsArtId);
		setTitle(entsorgungsart.getBezeichnung() + " Standorte");
		getActionBar().setIcon(R.drawable.entsorgung_white);

		// Re-Loading nur ausführen, wenn eine Verbindung zum Netzwerk besteht!
		if (SettingUtils.isConnectedToNetwork(EntsorgungStandorteActivity.this)
				&& !SettingUtils
						.isAirplaneModeOn(EntsorgungStandorteActivity.this)) {
			new AsyncParseLoader().execute();
		}
	}

	private class AsyncParseLoader extends
			AsyncTask<Void, Integer, ArrayList<AdapterSingleItem>> {

		protected void onPreExecute() {
			progressDialog = new ProgressDialog(
					EntsorgungStandorteActivity.this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setTitle("Daten werden geladen");
			progressDialog.setMessage("Bitte warten...");
			progressDialog.setIndeterminate(false);
			progressDialog.show();
		}

		protected ArrayList<AdapterSingleItem> doInBackground(Void... params) {

			try {
				data = DAO.getStandortListByIdFuerAdapter(entsorgungsArtId);

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
					adapter = new CustomEntsorgungStandortAdapter(
							getBaseContext(), result);
					lvEntsorgungStandorte.setAdapter(adapter);
					// Wenn man von Portrait auf Landscape wechselt,
					// Suche berücksichtigen und Ergebnisse filtern
					if (adapter != null && etSearchStandort.getTextSize() != 0) {
						EntsorgungStandorteActivity.this.adapter.getFilter()
								.filter(etSearchStandort.getText());
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void onClickBtnAlleStandorteAnzeigen(View v) {
		// Klick-Effekt anzeigen wenn Button gedrückt wird
		v.startAnimation(MuellGuideMsApplication.BUTTON_CLICK_ANIMATION);

		NetworkCondition netzwerkStatus = MuellGuideMsApplication
				.getNetzwerkStatus();

		boolean flightModeOn = SettingUtils
				.isAirplaneModeOn(EntsorgungStandorteActivity.this);
		if (flightModeOn) {
			MuellGuideMsApplication.showToastIfNecessary(
					EntsorgungStandorteActivity.this,
					NetworkCondition.AIRPLANE_MODE);
			return;

		} else if (netzwerkStatus == NetworkIdentifier.NetworkCondition.NO_CONNECTION) {
			MuellGuideMsApplication.showToastIfNecessary(getBaseContext(),
					netzwerkStatus);
			return;
		} else {
			if (entsorgungsArtId != null) {
				Intent mapsIntent = new Intent(this, MapsActivity.class);
				mapsIntent.putExtra("entsorgungsartId", entsorgungsArtId);
				startActivity(mapsIntent);
			}
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

}
