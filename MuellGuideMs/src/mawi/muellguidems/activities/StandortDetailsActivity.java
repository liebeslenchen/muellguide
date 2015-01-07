package mawi.muellguidems.activities;

import mawi.muellguidems.parseobjects.Bezirk;
import mawi.muellguidems.parseobjects.Entsorgungsart;
import mawi.muellguidems.parseobjects.Standort;
import mawi.muellguidems.util.DAO;
import mawi.muellguidems.util.EntsorgungsartUtil;
import mawi.muellguidems.util.NetworkIdentifier;
import mawi.muellguidems.util.NetworkIdentifier.NetworkCondition;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StandortDetailsActivity extends BaseActivity {

	private TextView tvAdresseContext;
	private TextView tvOeffnungszeitenContext;
	private TextView tvHinweisContext;

	private Standort standort;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_standort_details);

		tvAdresseContext = (TextView) findViewById(R.id.tvStandortAdresseContext);
		tvHinweisContext = (TextView) findViewById(R.id.tvStandortHinweisContext);
		tvOeffnungszeitenContext = (TextView) findViewById(R.id.tvStandortOeffnungszeitenContext);

		String standortId = getIntent().getStringExtra("id");

		standort = DAO.getStandortById(standortId);

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

			String adressValue = strasse + "\r\n" + plzValue + " Münster ("
					+ bezirkValue + ")";
			tvAdresseContext.setText(adressValue);

			// Hinweis auslesen
			String hinweisValue = "-";
			String hinweis = standort.getHinweis();
			if (!hinweis.equals("")) {
				hinweisValue = hinweis;
			}
			tvHinweisContext.setText(hinweisValue);

			// Öffungszeiten auslesen
			String oeffnungszeitenValue = "immer geöffnet";
			Entsorgungsart entsorgungsart = EntsorgungsartUtil.ENTSORGUNGSART_HASH_MAP
					.get(standort.getEntsorgungsartId());
			if (entsorgungsart.getBezeichnung().equalsIgnoreCase("Altglas")) {
				oeffnungszeitenValue = DAO.getContainerOeffnungszeiten(standort
						.getEntsorgungsartId());
			} else if (entsorgungsart.getBezeichnung().equalsIgnoreCase(
					"Elektrokleingeräte")) {
				oeffnungszeitenValue = DAO.getContainerOeffnungszeiten(standort
						.getEntsorgungsartId());
			} else if (entsorgungsart.getBezeichnung().equalsIgnoreCase(
					"Recyclinghof")) {
				oeffnungszeitenValue = DAO
						.getRecyclinghofOeffnungszeiten(standort.getId());
			}

			tvOeffnungszeitenContext.setText(oeffnungszeitenValue);

			// Titeltext setzten, je nach Entsorgungsart
			setTitle(standort.getBezeichnung());
		} else {
			setTitle("Standort");
			// TODO: Fehlerbehandlung fehlt noch !
			// Kann eigentlich nur auftreten bei Parse Fehler (kein Netzwerk
			// etc.), dann ist standort null

		}
		// Titelicon setzen
		getActionBar().setIcon(R.drawable.entsorgung_white);
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
			// Add the buttons
			builder.setNegativeButton(R.string.zurueck,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// User cancelled the dialog
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
			// Get the AlertDialog and show it
			AlertDialog dialog = builder.create();
			dialog.show();
		}

	}
}
