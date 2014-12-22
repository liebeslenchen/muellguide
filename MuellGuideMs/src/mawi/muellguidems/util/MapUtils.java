package mawi.muellguidems.util;

import java.util.ArrayList;

import com.google.android.gms.maps.model.MarkerOptions;

public class MapUtils {

	public static ArrayList<MarkerOptions> getAllMakers(String muellType,
			String id) {

		ArrayList<MarkerOptions> markerOptions = new ArrayList<MarkerOptions>();

		if (muellType == null && id == null) {
			// SHOW ALL!
			// TODO
		} else if (muellType != null && id == null) {
			// SHOW ALL ENTRIES FOR muellType
			// TODO
		} else if (muellType == null && id != null) {
			// SHOW SPECIFIC STANDORT
			markerOptions = DAO.getAllMarkersForStandortById(id);
		}

		return markerOptions;

	}

}
