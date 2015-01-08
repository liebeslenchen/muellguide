package mawi.muellguidems.activities;

import mawi.muellguidems.parseobjects.Bezirk;
import mawi.muellguidems.parseobjects.Entsorgungsart;
import mawi.muellguidems.parseobjects.Standort;
import mawi.muellguidems.util.DAO;
import mawi.muellguidems.util.EntsorgungsartUtil;
import mawi.muellguidems.util.NetworkIdentifier;
import mawi.muellguidems.util.NetworkIdentifier.NetworkCondition;
import mawi.muellguidems.util.StringEnum;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class StandortDetailsActivity extends BaseActivity {

	private TextView tvAdresseContext;
	private TextView tvOeffnungszeitenContext;
	private TextView tvHinweisContext;
	private ProgressDialog progressDialog;
	private Standort standort;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_standort_details);

		tvAdresseContext = (TextView) findViewById(R.id.tvStandortAdresseContext);
		tvHinweisContext = (TextView) findViewById(R.id.tvStandortHinweisContext);
		tvOeffnungszeitenContext = (TextView) findViewById(R.id.tvStandortOeffnungszeitenContext);

		// Titelicon setzen
		getActionBar().setIcon(R.drawable.entsorgung_white);

		String standortId = getIntent().getStringExtra("id");
		new AsyncParseLoader().execute(standortId);
	}

	private class AsyncParseLoader extends AsyncTask<String, Integer, Standort> {

		protected void onPreExecute() {
			progressDialog = new ProgressDialog(StandortDetailsActivity.this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setTitle("Daten werden geladen");
			progressDialog.setMessage("Bitte warten...");
			progressDialog.setIndeterminate(false);
			progressDialog.show();
		}

		protected Standort doInBackground(String... params) {

			try {
				standort = DAO.getStandortById(params[0]);

				return standort;
			}

			catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(getBaseContext(),
						getString(R.string.fehler_beim_laden),
						Toast.LENGTH_LONG).show();

				return null;
			}

		}

		protected void onPostExecute(Standort result) {
			if (progressDialog.isShowing())
				progressDialog.cancel();

			try {

				if (standort != null) {

					// Adresse auslesen
					String strasse = standort.getStrasse();
					String plzValue = "...";
					String plz = standort.getPlz();
					if (!plz.equals("")) {
						plzValue = plz;
					}

					String bezirkValue = "";
					String bezirkId = standort.getBezirkId();
					Bezirk bezirk = DAO.getBezirkById(bezirkId);
					bezirkValue = bezirk.getBezeichnung();

					String adressValue = strasse + "\r\n" + plzValue
							+ " Münster (" + bezirkValue + ")";
					tvAdresseContext.setText(adressValue);

					// Hinweis auslesen
					String hinweisValue = "-";
					String hinweis = standort.getHinweis();
					if (!hinweis.equals("")) {
						hinweisValue = hinweis;
					}
					tvHinweisContext.setText(hinweisValue);

					// Öffungszeiten auslesen
					String oeffnungszeitenValue = StringEnum.IMMER_OFFEN
							.toString();
					Entsorgungsart entsorgungsart = EntsorgungsartUtil.ENTSORGUNGSART_HASH_MAP
							.get(standort.getEntsorgungsartId());
					if (entsorgungsart.getBezeichnung().equalsIgnoreCase(
							StringEnum.ALTGLAS.toString())) {
						oeffnungszeitenValue = DAO
								.getContainerOeffnungszeitenAufbereitet(standort
										.getEntsorgungsartId());
					} else if (entsorgungsart.getBezeichnung()
							.equalsIgnoreCase(
									StringEnum.ELEKTROKLEINGERAETE.toString())) {
						oeffnungszeitenValue = DAO
								.getContainerOeffnungszeitenAufbereitet(standort
										.getEntsorgungsartId());
					} else if (entsorgungsart.getBezeichnung()
							.equalsIgnoreCase(
									StringEnum.RECYCLINGHOF.toString())) {
						oeffnungszeitenValue = DAO
								.getRecyclinghofOeffnungszeitenAufbereitet(standort
										.getId());
					}

					tvOeffnungszeitenContext.setText(oeffnungszeitenValue);

					// Titeltext setzten, je nach Entsorgungsart
					setTitle(standort.getBezeichnung());
				}
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(getBaseContext(),
						getString(R.string.fehler_beim_laden),
						Toast.LENGTH_LONG).show();
			}
		}

	}

	public void onClickBtnStandortAufKarteAnzeigen(View v) {
		// Klick-Effekt anzeigen wenn Button gedrückt wird
		v.startAnimation(MuellGuideMsApplication.BUTTON_CLICK_ANIMATION);

		// Falls kein genauer Standort vorhanden ist Hinweis anzeigen und
		// Karte nicht öffnen
		if (standort != null && standort.getGpsStandort() != null) {
			// Netzwerk prüfen
			NetworkCondition netzwerkStatus = MuellGuideMsApplication
					.getNetzwerkStatus();
			if (netzwerkStatus == NetworkIdentifier.NetworkCondition.NO_CONNECTION) {
				StandortDetailsActivity.this
						.showToastIfNecessary(netzwerkStatus);
				return;
			}
			// Maps öffnen
			Intent mapsIntent = new Intent(this, MapsActivity.class);
			mapsIntent.putExtra("objectId", standort.getId());
			startActivity(mapsIntent);
		} else {
			// AlertDialog für Feedback
			AlertDialog.Builder builder = new AlertDialog.Builder(
					StandortDetailsActivity.this);
			builder.setMessage(R.string.dialogStandortFeedback);
			// Hinzufügen der Buttons
			builder.setNegativeButton(R.string.zurueck,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// User bricht denn Dialog ab
						}
					});
			builder.setPositiveButton(R.string.feedback,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// Feedback Activity aufrufen
							startActivity(new Intent(getBaseContext(),
									FeedbackActivity.class));
						}
					});
			// Dialog anzeigen
			AlertDialog dialog = builder.create();
			dialog.show();
		}

	}
}
