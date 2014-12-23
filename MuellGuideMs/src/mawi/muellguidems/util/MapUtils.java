package mawi.muellguidems.util;

import java.util.ArrayList;

import mawi.muellguidems.activities.MapsActivity;

import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Hilfsklasse für das Verwalten und Befüllen der {@link MapsActivity}
 */
public class MapUtils {

	public static ArrayList<MarkerOptions> getAllMakers(
			String entsorgungsartId, String id) {

		ArrayList<MarkerOptions> markerOptions = new ArrayList<MarkerOptions>();

		if (entsorgungsartId == null && id == null) {
			// zeige alles
			markerOptions = DAO
					.getAllGPSMarkersForAllEntsorgungsarten(entsorgungsartId);
		} else if (entsorgungsartId != null && id == null) {
			// zeige alles für eine gegebene Entsorgungsart
			markerOptions = DAO
					.getAllGPSMarkersForGivenEntsorgungsart(entsorgungsartId);
		} else if (entsorgungsartId == null && id != null) {
			// zeige einen gegebene Standort
			markerOptions = DAO.getAllGPSMarkersForStandortById(id);
		}

		return markerOptions;

	}

}
