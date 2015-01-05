package mawi.muellguidems.activities;

import mawi.muellguidems.parseobjects.Standort;
import mawi.muellguidems.util.DAO;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class StandortDetailsActivity extends Activity {

	private TextView tvAdresse;
	private TextView tvOeffnungszeiten;
	private String standortId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_standort_details);

		tvAdresse = (TextView) findViewById(R.id.tvStandortAdresse);
		tvOeffnungszeiten = (TextView) findViewById(R.id.tvStandortOeffnungszeiten);

		standortId = getIntent().getStringExtra("id");

		Standort standort = DAO.getStandortById(standortId);

		if (standort != null) {

			String strasse = standort.getStrasse();
			String plz = standort.getPlz();
			String bezirk = "Münster"; // TODO: Besser an dieser Stelle Bezirk
										// angeben !

			String adressValue = "Adresse:\r\n" + strasse + " " + "\r\n" + plz
					+ " " + bezirk;
			tvAdresse.setText(adressValue);

			/* TODO: Öffnungszeiten fehlen noch! */

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
		if (standortId != null) {
			Intent mapsIntent = new Intent(this, MapsActivity.class);
			mapsIntent.putExtra("objectId", standortId);
			startActivity(mapsIntent);
		}
	}
}
