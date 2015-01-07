package mawi.muellguidems.util;

import java.util.ArrayList;
import java.util.List;

import mawi.muellguidems.activities.MapsActivity;
import mawi.muellguidems.parseobjects.Entsorgungsart;
import mawi.muellguidems.parseobjects.Standort;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Hilfsklasse für das Verwalten und Befüllen der {@link MapsActivity}
 */
public class MapUtils {

	/**
	 * Erzeugt in Abhängigkeit der {@link Intent}-Attribute entsorgungsartId und
	 * objectId die {@link MarkerOptions} für den MapsIntent
	 * 
	 * @param entsorgungsartId
	 * @param id
	 * @return
	 */
	public static ArrayList<MarkerOptions> getAllMakers(
			String entsorgungsartId, String id) {

		ArrayList<MarkerOptions> markerOptions = new ArrayList<MarkerOptions>();
		List<Standort> standorte = new ArrayList<Standort>();
		if (entsorgungsartId == null && id == null) {
			// zeige alles
			standorte = DAO.getStandortListForAllTypes();
		} else if (entsorgungsartId != null && id == null) {
			// zeige alles für eine gegebene Entsorgungsart
			standorte = DAO
					.getAllStandorteForGivenEntsorgungsart(entsorgungsartId);
		} else if (entsorgungsartId == null && id != null) {
			// zeige einen gegebene Standort
			standorte = DAO.getStandortListById(id);
		}
		markerOptions = createMarkerForAllGivenStandorte(standorte);

		return markerOptions;

	}

	/**
	 * Erstellt eine {@link ArrayList} an {@link MarkerOptions} für eine
	 * gegebene {@link List} an {@link Standort}en
	 * 
	 * @param standorte
	 * @return {@link ArrayList} von {@link MarkerOptions}
	 */
	private static ArrayList<MarkerOptions> createMarkerForAllGivenStandorte(
			List<Standort> standorte) {
		ArrayList<MarkerOptions> markers = new ArrayList<MarkerOptions>();
		for (Standort standort : standorte) {
			if (standort.getGpsStandort() != null) {
				LatLng latLng = new LatLng(standort.getGpsStandort()
						.getLatitude(), standort.getGpsStandort()
						.getLongitude());
				MarkerOptions marker = new MarkerOptions().position(latLng)
						.title(standort.getBezeichnung());

				BitmapDescriptor icon = getIconsFromEntsorgungsartUtilForStandort(standort);
				if (icon != null) {
					marker.icon(icon);
				}
				markers.add(marker);
			}
		}
		return markers;
	}

	private static BitmapDescriptor getIconsFromEntsorgungsartUtilForStandort(
			Standort standort) {
		Entsorgungsart entsorgungsart = EntsorgungsartUtil.ENTSORGUNGSART_HASH_MAP
				.get(standort.getEntsorgungsartId());

		BitmapDescriptor icon = EntsorgungsartUtil
				.getDrawableForEntsorgungsart(entsorgungsart);

		return icon;
	}

	/**
	 * Methode, die anhand des mitgegebenen {@link LocationManager}s den zuletzt
	 * bekannten Standort im {@link LatLng}-Format zurückgibt
	 * 
	 * @param locationManager
	 * @return {@link LatLng} der zuletzt bekannten Position
	 */
	public static LatLng getCurrentLocation(LocationManager locationManager) {
		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, false);
		Location location = locationManager.getLastKnownLocation(provider);
		LatLng currentPosition = new LatLng(location.getLatitude(),
				location.getLongitude());

		return currentPosition;

	}
}
