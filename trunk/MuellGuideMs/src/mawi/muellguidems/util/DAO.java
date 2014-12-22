package mawi.muellguidems.util;

import java.util.ArrayList;
import java.util.HashMap;

import mawi.muellguidems.activities.R;
import mawi.muellguidems.parseobjects.Standort;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class DAO {

	public static ArrayList<HashMap<String, String>> getAlleGegenstaende() {
		try {
			ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

			HashMap<String, String> gegenstand = new HashMap<String, String>();
			gegenstand.put("id", "1234");
			gegenstand.put("bezeichnung", "Flasche");
			gegenstand.put("entsorgungsart", "Altglas");
			gegenstand.put("hinweis",
					"Bitte auf Braun-/Weinglas-Trennung achten!");
			result.add(gegenstand);

			return result;
		} catch (Exception ex) {
			return null;
		}
	}

	public static ArrayList<MarkerOptions> getAllMarkersForStandortById(
			String id) {
		ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
		ParseQuery<Standort> stParseQuery = Standort.getQuery();
		try {
			Standort standort = stParseQuery.get(id);
			if (standort.getGpsStandort() != null) {
				LatLng latLng = new LatLng(standort.getGpsStandort()
						.getLatitude(), standort.getGpsStandort()
						.getLongitude());
				MarkerOptions marker = new MarkerOptions().position(latLng)
						.title(standort.getBezeichnung());
				// TODO icon abhängig von Typ setzen
				marker.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.recyclinghof));
				markers.add(marker);
			}

		} catch (ParseException e1) {
			e1.printStackTrace();
		}

		return markers;
	}
}
