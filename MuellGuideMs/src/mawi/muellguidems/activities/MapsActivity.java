package mawi.muellguidems.activities;

import java.util.ArrayList;

import mawi.muellguidems.parseobjects.Entsorgungsart;
import mawi.muellguidems.parseobjects.Standort;
import mawi.muellguidems.util.DAO;
import mawi.muellguidems.util.EntsorgungsartUtil;
import mawi.muellguidems.util.MapUtils;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends Activity {

	private GoogleMap googleMap;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);

		Intent myIntent = getIntent();
		String entsorgungsartId = myIntent.getStringExtra("entsorgungsartId");
		String id = myIntent.getStringExtra("objectId");

		try {
			// Loading map
			initializeMap();

			ArrayList<MarkerOptions> allMarkers = MapUtils.getAllMakers(
					entsorgungsartId, id);

			for (MarkerOptions markerOptions : allMarkers) {
				googleMap.addMarker(markerOptions);
			}

			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

			checkForServices(locationManager);

			// Liest aktuelle Position mit Hilfe der MapUtils aus
			LatLng zoomLocation = MapUtils.getCurrentLocation(locationManager);

			if (allMarkers.size() == 1) {
				// Wenn nur eine Position zurückgegeben wird, soll auch nur auf
				// diese Position gezoomt werden (z.B. bei Recyclinghöfen)
				zoomLocation = allMarkers.get(0).getPosition();
			} else if (zoomLocation == null) {
				// Münster Zentrum als Zoom, falls die aktuelle Position nicht
				// bekannt ist
				zoomLocation = new LatLng(51.961749, 7.625591);
			}

			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(zoomLocation).zoom(12).build();

			googleMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));

			googleMap.setMyLocationEnabled(true);
			googleMap.getUiSettings().setMyLocationButtonEnabled(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Titeltext und Icon setzten, je nach Entsorgungsart oder Standort
		getActionBar().setIcon(R.drawable.entsorgung_white);
		if (entsorgungsartId != null) {
			Entsorgungsart entsorgungsart = EntsorgungsartUtil.ENTSORGUNGSART_HASH_MAP
					.get(entsorgungsartId);
			setTitle(entsorgungsart.getBezeichnung() + " Standorte");
		} else if (id != null) {
			try {
				Standort standort = DAO.getStandortById(id);
				setTitle(standort.getBezeichnung());
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(getBaseContext(),
						getString(R.string.fehler_beim_laden),
						Toast.LENGTH_LONG).show();
			}
		}
	}

	/**
	 * Überprüft, welche Standort-Dienste aktiviert sind und gibt gegebenenfalls
	 * ein Toast mit Hinweisen aus
	 * 
	 * @param locationManager
	 */
	private void checkForServices(LocationManager locationManager) {
		if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
				&& !locationManager
						.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			buildLocationSettingsAlert(R.string.gps_keineDiensteAktiviert);
			// } else if (!locationManager
			// .isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			// Toast.makeText(getBaseContext(), R.string.gps_gpsEinschalten,
			// Toast.LENGTH_LONG).show();
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
		AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
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

	private void initializeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Entschuldigung, die Map konnte nicht geladen werden.",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	protected void onResume() {
		super.onResume();
		initializeMap();
	}
}