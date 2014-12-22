package mawi.muellguidems.util;

import java.util.ArrayList;

import mawi.muellguidems.activities.MapsActivity;

import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Hilfsklasse für das Verwalten und Befüllen der {@link MapsActivity}
 */
public class MapUtils {

	public static ArrayList<MarkerOptions> getAllMakers(String muellType,
			String id) {

		ArrayList<MarkerOptions> markerOptions = new ArrayList<MarkerOptions>();

		if (muellType == null && id == null) {
			// zeige alles
			markerOptions = DAO.getAllMarkersForAllEntsorgungsarten();
		} else if (muellType != null && id == null) {
			// zeige alles für eine gegebene Entsorgungsart
			markerOptions = DAO.getAllMarkersForGivenEntsorgungsart(muellType);
		} else if (muellType == null && id != null) {
			// zeige einen gegebene Standort
			markerOptions = DAO.getAllMarkersForStandortById(id);
		}

		return markerOptions;

	}

}
