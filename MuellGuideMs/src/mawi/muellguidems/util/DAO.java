package mawi.muellguidems.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mawi.muellguidems.activities.R;
import mawi.muellguidems.parseobjects.Entsorgungsart;
import mawi.muellguidems.parseobjects.Gegenstand;
import mawi.muellguidems.parseobjects.Standort;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.ParseException;
import com.parse.ParseQuery;

public class DAO {

	public static ArrayList<HashMap<String, String>> getAlleGegenstaendeFuerExpandableAdapter() {
		// try {
		// ArrayList<HashMap<String, String>> result = new
		// ArrayList<HashMap<String, String>>();
		//
		// HashMap<String, String> gegenstand = new HashMap<String, String>();
		// gegenstand.put("id", "1234");
		// gegenstand.put("bezeichnung", "Flasche");
		// gegenstand.put("entsorgungsart", "Altglas");
		// gegenstand.put("hinweis",
		// "Bitte auf Braun-/Weinglas-Trennung achten!");
		// result.add(gegenstand);
		//
		// return result;
		// } catch (Exception ex) {
		// return null;
		// }
		ParseQuery<Gegenstand> query = Gegenstand.getQuery();
		ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
		HashMap<String, Entsorgungsart> entsorgungsartMap = loadAllEntsorgungsartenMap();
		try {
			List<Gegenstand> gegenstandList = query.find();

			for (Gegenstand gegenstand : gegenstandList) {
				HashMap<String, String> gegenstandHashMap = new HashMap<String, String>();
				// Entsorgungsart entsorgungsart = (Entsorgungsart) gegenstand
				// .getEntsorgungsart();
				// entsorgungsart.fetchIfNeeded();

				
				
				gegenstandHashMap.put("id", gegenstand.getId());
				gegenstandHashMap.put("bezeichnung",
						gegenstand.getBezeichnung());
				gegenstandHashMap.put("entsorgungsart",
						entsorgungsartMap.get(gegenstand.getEntsorgungsartId())
								.getBezeichnung());
				gegenstandHashMap.put("hinweis",
						entsorgungsartMap.get(gegenstand.getEntsorgungsartId())
								.getHinweis());
				result.add(gegenstandHashMap);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static HashMap<String, Entsorgungsart> loadAllEntsorgungsartenMap() {
		HashMap<String, Entsorgungsart> entsorgungsartMap = new HashMap<String, Entsorgungsart>();
		ParseQuery<Entsorgungsart> queryEntsorgung = Entsorgungsart.getQuery();

		List<Entsorgungsart> entsorgungList;
		try {
			entsorgungList = queryEntsorgung.find();
			for (Entsorgungsart entsorgungsart : entsorgungList) {
				entsorgungsartMap.put(entsorgungsart.getId(), entsorgungsart);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return entsorgungsartMap;
	}

	/**
	 * Gibt einen {@link MarkerOptions} für einen gegebenen {@link Standort}
	 * zurück
	 * 
	 * @param id
	 * @return {@link ArrayList} von {@link MarkerOptions}
	 */
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

	/**
	 * Gibt eine Liste von {@link MarkerOptions} für alle {@link Entsorgungsart}
	 * en und {@link Standort}e zurück
	 * 
	 * @return {@link ArrayList} von {@link MarkerOptions}
	 */
	public static ArrayList<MarkerOptions> getAllMarkersForAllEntsorgungsarten() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Gibt eine Liste aller {@link MarkerOptions} von {@link Standort}en für
	 * eine gegebene {@link Entsorgungsart} zurück
	 * 
	 * @param muellType
	 * @return {@link ArrayList} von {@link MarkerOptions}
	 */
	public static ArrayList<MarkerOptions> getAllMarkersForGivenEntsorgungsart(
			String muellType) {
		// TODO Auto-generated method stub
		return null;
	}
}
