package mawi.muellguidems.activities;

import java.util.ArrayList;

import mawi.muellguidems.parseobjects.Bezirk;
import mawi.muellguidems.parseobjects.Entsorgungsart;
import mawi.muellguidems.parseobjects.Standort;
import mawi.muellguidems.util.DAO;
import mawi.muellguidems.util.EntsorgungsartUtil;
import mawi.muellguidems.util.MapUtils;
import mawi.muellguidems.util.NetworkIdentifier;
import mawi.muellguidems.util.NetworkIdentifier.NetworkCondition;
import mawi.muellguidems.util.StringEnum;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
				MuellGuideMsApplication.showToastIfNecessary(getBaseContext(),
						netzwerkStatus);
				return;
			}

			int display_mode = getResources().getConfiguration().orientation;
			// is Tablet and ladscape?
			if (isTablet(getApplicationContext()) && display_mode == 2) {
				// Kopiert aus Maps Activity
				GoogleMap googleMap = ((MapFragment) getFragmentManager()
						.findFragmentById(R.id.map)).getMap();
				ArrayList<MarkerOptions> allMarkers = MapUtils.getAllMakers(
						null, standort.getId());

				for (MarkerOptions markerOptions : allMarkers) {
					googleMap.addMarker(markerOptions);
				}

				LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

				checkForServices(locationManager);

				// Liest aktuelle Position mit Hilfe der MapUtils aus
				LatLng zoomLocation = MapUtils
						.getCurrentLocation(locationManager);

				if (allMarkers.size() == 1) {
					// Wenn nur eine Position zurückgegeben wird, soll auch nur
					// auf
					// diese Position gezoomt werden (z.B. bei Recyclinghöfen)
					zoomLocation = allMarkers.get(0).getPosition();
				}
				CameraPosition cameraPosition = new CameraPosition.Builder()
						.target(zoomLocation).zoom(12).build();

				googleMap.animateCamera(CameraUpdateFactory
						.newCameraPosition(cameraPosition));

				googleMap.setMyLocationEnabled(true);
				googleMap.getUiSettings().setMyLocationButtonEnabled(true);
			} else {
				// Maps öffnen
				Intent mapsIntent = new Intent(this, MapsActivity.class);
				mapsIntent.putExtra("objectId", standort.getId());
				startActivity(mapsIntent);
			}
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
							Intent gpsIntent = new Intent(getBaseContext(),
									FeedbackActivity.class);
							gpsIntent.putExtra(
									"gpsText",
									"Mein Feedback zum Standort: \""
											+ standort.getId() + "\": ");
							// Feedback Activity aufrufen
							startActivity(gpsIntent);
						}
					});
			// Dialog anzeigen
			AlertDialog dialog = builder.create();
			dialog.show();
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

	public boolean isTablet(Context context) {
		boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_XLARGE);
		boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
		return (xlarge || large);
	}

	/**
	 * Überprüft, welche Standort-Dienste aktiviert sind und gibt gegebenenfalls
	 * ein Toast mit Hinweisen aus (Aus Maps Activity kopiert)
	 * 
	 * @param locationManager
	 */
	private void checkForServices(LocationManager locationManager) {
		if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
				&& !locationManager
						.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			buildLocationSettingsAlert(R.string.gps_keineDiensteAktiviert);
		} else if (!locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			// Toast.makeText(getBaseContext(), R.string.gps_gpsEinschalten,
			// Toast.LENGTH_LONG).show();
			buildLocationSettingsAlert(R.string.gps_gpsEinschalten);
		} else if (!locationManager
				.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			Toast.makeText(getBaseContext(),
					R.string.gps_lokalisierungPerNetzwerk, Toast.LENGTH_LONG)
					.show();
		}
	}

	/**
	 * Erzeugt ein Popup, wenn keine Einstellungen aktiviert sind
	 */
	private void buildLocationSettingsAlert(int gpsKeinediensteaktiviert) {
		// AlertDialog für Feedback
		AlertDialog.Builder builder = new AlertDialog.Builder(
				StandortDetailsActivity.this);
		builder.setMessage(gpsKeinediensteaktiviert);
		// Hinzufügen der Buttons
		builder.setNegativeButton(R.string.zurueck,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// User bricht denn Dialog ab
					}
				});
		builder.setPositiveButton(R.string.gps_einstellungenAendern,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// Einstellungen aufrufen
						startActivity(new Intent(
								android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
					}
				});
		// Dialog anzeigen
		AlertDialog dialog = builder.create();
		dialog.show();
	}

}
