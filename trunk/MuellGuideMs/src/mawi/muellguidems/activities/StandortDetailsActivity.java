package mawi.muellguidems.activities;

import mawi.muellguidems.parseobjects.Bezirk;
import mawi.muellguidems.parseobjects.Entsorgungsart;
import mawi.muellguidems.parseobjects.Standort;
import mawi.muellguidems.util.DAO;
import mawi.muellguidems.util.EntsorgungsartUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseGeoPoint;

public class StandortDetailsActivity extends Activity {

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

			ParseGeoPoint geoPoint = standort.getGpsStandort();
			if (geoPoint == null) {
				/* TODO: Feedbackbutton anzeigen! */
			}
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.standort_details, menu);
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

	public void onClickBtnStandortAufKarteAnzeigen(View v) {
		// Klick-Effekt anzeigen wenn Button gedrückt wird
		v.startAnimation(MuellGuideMsApplication.BUTTON_CLICK_ANIMATION);

		// Falls kein genauer Standort vorhanden ist Hinweis anzeigen und
		// Karte nicht öffnen
		if (standort != null && standort.getGpsStandort() != null) {
			Intent mapsIntent = new Intent(this, MapsActivity.class);
			mapsIntent.putExtra("objectId", standort.getId());
			startActivity(mapsIntent);
		} else {
			Toast.makeText(
					StandortDetailsActivity.this,
					"Der Standort verfügt über keine genaue Ortsangabe und kann deshalb nicht auf einer Karte angezeigt werden. Unter Feedback können Sie uns den genauen Standort mitteilen.",
					Toast.LENGTH_LONG).show();
		}
	}
}
