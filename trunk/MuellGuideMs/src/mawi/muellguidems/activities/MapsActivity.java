package mawi.muellguidems.activities;

import android.app.Activity;
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

		try {
			// Loading map
			initializeMap();

			// Beliebiges Target setzen
			double latitude = 51.968135;
			double longitude = 7.595453;
			LatLng target = new LatLng(latitude, longitude);

			// Landmarke erstellen
			MarkerOptions marker = new MarkerOptions().position(target).title(
					"FHZ");
			googleMap.addMarker(marker);

			// Sorgt für den Zoom auf Muenster
			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(target).zoom(12).build();
			googleMap.animateCamera(CameraUpdateFactory
					.newCameraPosition(cameraPosition));

			// googleMap.setMyLocationEnabled(true);
			// googleMap.getUiSettings().setMyLocationButtonEnabled(true);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initializeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry, unable to create Map", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	protected void onResume() {
		super.onResume();
		initializeMap();
	}
}