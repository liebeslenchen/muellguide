package mawi.muellguidems.activities;

import java.util.ArrayList;

import mawi.muellguidems.util.MapUtils;
import android.app.Activity;
import android.content.Intent;
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

			Intent myIntent = getIntent();
			String entsorgungsartId = myIntent
					.getStringExtra("entsorgungsartId");
			String id = myIntent.getStringExtra("objectId");

			ArrayList<MarkerOptions> allMarkers = MapUtils.getAllMakers(
					entsorgungsartId, id);

			for (MarkerOptions markerOptions : allMarkers) {
				googleMap.addMarker(markerOptions);
			}

			// Münster Zentrum als Zoom
			// TODO aktuelle Position
			LatLng cityCenter = new LatLng(51.961749, 7.625591);
			// Sorgt für den Zoom auf Muenster
			CameraPosition cameraPosition = new CameraPosition.Builder()
					.target(cityCenter).zoom(12).build();
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