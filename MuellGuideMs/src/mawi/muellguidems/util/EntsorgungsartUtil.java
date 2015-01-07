package mawi.muellguidems.util;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import mawi.muellguidems.activities.MuellGuideMsApplication;
import mawi.muellguidems.activities.R;
import mawi.muellguidems.parseobjects.Entsorgungsart;
import mawi.muellguidems.parseobjects.OeffungszeitenContainer;
import mawi.muellguidems.parseobjects.OeffungszeitenRecyclinghof;
import mawi.muellguidems.parseobjects.Standort;
import android.content.Context;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.parse.ParseException;
import com.parse.ParseQuery;

/**
 * Hilfsklasse, die alle Entsorgungsarten als HashMap vorhält. Der key ist die
 * ObjectId und value ist das entsprechende Entsorgungsartobjekt.
 */
public class EntsorgungsartUtil {

	public static final HashMap<String, Entsorgungsart> ENTSORGUNGSART_HASH_MAP = loadAllEntsorgungsartenHashMap();

	private static HashMap<String, Entsorgungsart> loadAllEntsorgungsartenHashMap() {
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
	 * Hilfsmethode, die die Öffnungszeiten für einen gegebenen {@link Standort}
	 * für den aktuellen Tag wiedergibt
	 * 
	 * @param standort
	 * @return {@link Standort} mit den heutigen Öffnungszeiten des
	 *         {@link Standort}s
	 */
	public static String getOeffnungszeitenForCurrentDayAndStandort(
			Standort standort) {
		try {
			// String gilt für Altkleidercontainer
			String oeffnungszeitenValue = "immer geöffnet";
			Entsorgungsart entsorgungsart = EntsorgungsartUtil.ENTSORGUNGSART_HASH_MAP
					.get(standort.getEntsorgungsartId());
			if (entsorgungsart.getBezeichnung().equalsIgnoreCase("Altglas")
					|| entsorgungsart.getBezeichnung().equalsIgnoreCase(
							"Elektrokleingeräte")) {
				oeffnungszeitenValue = "heute nicht geöffnet";
				List<OeffungszeitenContainer> allOeffnungszeiten = DAO
						.getContainerOeffnungszeitenList(standort
								.getEntsorgungsartId());
				String weekDay = getWochentagFromSystemDate();
				for (OeffungszeitenContainer oeff : allOeffnungszeiten) {
					if (weekDay.equalsIgnoreCase(oeff.getWochentag())) {

						return "geöffnet von " + oeff.getStart() + " bis "
								+ oeff.getEnde();
					}
				}

			} else if (entsorgungsart.getBezeichnung().equalsIgnoreCase(
					"Recyclinghof")) {
				oeffnungszeitenValue = "heute nicht geöffnet";
				List<OeffungszeitenRecyclinghof> allOeffnungszeiten = DAO
						.getRecyclinghofOeffnungszeitenList(standort.getId());
				String weekDay = getWochentagFromSystemDate();
				for (OeffungszeitenRecyclinghof oeff : allOeffnungszeiten) {
					if (weekDay.equalsIgnoreCase(oeff.getWochentag())) {
						return "geöffnet von " + oeff.getStart() + " bis "
								+ oeff.getEnde();
					}
				}
			}
			return oeffnungszeitenValue;
		} catch (Exception e) {
			return null;
		}

	}

	public static String getWochentagFromSystemDate() {
		Calendar rightNow = Calendar.getInstance(TimeZone.getDefault());
		int dayOfWeek = rightNow.get(Calendar.DAY_OF_WEEK);
		switch (dayOfWeek) {
		case 1:
			return "Sonntag";
		case 2:
			return "Montag";
		case 3:
			return "Dienstag";
		case 4:
			return "Mittwoch";
		case 5:
			return "Donnerstag";
		case 6:
			return "Freitag";
		case 7:
			return "Samstag";
		}
		return "";

	}

	/**
	 * Gibt ein Drawable in Form eines {@link BitmapDescriptor}s zurück, in
	 * Abhängigkeit von einer gegebenen {@link Entsorgungsart}. Zugriff erfolgt
	 * über die {@link Context}-Variable in {@link MuellGuideMsApplication}
	 * 
	 * @param entsorgungsart
	 * @return {@link BitmapDescriptor}
	 */
	public static BitmapDescriptor getDrawableForEntsorgungsart(
			Entsorgungsart entsorgungsart) {
		BitmapDescriptor drawable = null;
		drawable = BitmapDescriptorFactory
				.fromResource(getDrawableIdForEntsorgungsart(entsorgungsart));
		return drawable;
	}

	/**
	 * Gibt die R.Drawable ID in Form eines {@link Integer}s zurück, in
	 * Abhängigkeit von einer gegebenen {@link Entsorgungsart}.
	 * 
	 * @param entsorgungsart
	 * @return {@link Integer}
	 */
	public static Integer getDrawableIdForEntsorgungsart(
			Entsorgungsart entsorgungsart) {
		Integer drawableId = null;

		if (entsorgungsart.getBezeichnung().equalsIgnoreCase(
				MuellGuideMsApplication.getContext().getResources()
						.getString(R.string.db_entsorgungsart_value_altglas))) {
			// Altglas
			drawableId = R.drawable.altglas;
		} else if (entsorgungsart
				.getBezeichnung()
				.equalsIgnoreCase(
						MuellGuideMsApplication
								.getContext()
								.getResources()
								.getString(
										R.string.db_entsorgungsart_value_altkleider))) {
			// Altkleider
			drawableId = R.drawable.altkleider;
		} else if (entsorgungsart
				.getBezeichnung()
				.equalsIgnoreCase(
						MuellGuideMsApplication
								.getContext()
								.getResources()
								.getString(
										R.string.db_entsorgungsart_value_elektrokleingeraete))) {
			// Elektrokleingeräte
			drawableId = R.drawable.elektrokleingeraet;
		} else if (entsorgungsart.getBezeichnung().equalsIgnoreCase(
				MuellGuideMsApplication
						.getContext()
						.getResources()
						.getString(
								R.string.db_entsorgungsart_value_recyclinghof))) {
			// Recyclinghof
			drawableId = R.drawable.recyclinghof;
		} else if (entsorgungsart
				.getBezeichnung()
				.equalsIgnoreCase(
						MuellGuideMsApplication
								.getContext()
								.getResources()
								.getString(
										R.string.db_entsorgungsart_value_papiermuell))) {
			// Papier
			drawableId = R.drawable.papiermuell;
		} else if (entsorgungsart
				.getBezeichnung()
				.equalsIgnoreCase(
						MuellGuideMsApplication
								.getContext()
								.getResources()
								.getString(
										R.string.db_entsorgungsart_value_gelber_sack))) {
			// Gelber Sack
			drawableId = R.drawable.gelbersack;
		} else if (entsorgungsart.getBezeichnung().equalsIgnoreCase(
				MuellGuideMsApplication.getContext().getResources()
						.getString(R.string.db_entsorgungsart_value_biotonne))) {
			// Biotonne
			drawableId = R.drawable.biotonne;
		} else if (entsorgungsart.getBezeichnung().equalsIgnoreCase(
				MuellGuideMsApplication.getContext().getResources()
						.getString(R.string.db_entsorgungsart_value_restmuell))) {
			// Restmüll
			drawableId = R.drawable.restmuell;
		} else {
			drawableId = R.drawable.ic_launcher;
		}

		return drawableId;
	}

	/**
	 * Gibt die R.Drawable ID für ein graues Icon in Form eines {@link Integer}s
	 * zurück, in Abhängigkeit von einer gegebenen {@link Entsorgungsart}.
	 * 
	 * @param entsorgungsart
	 * @return {@link Integer}
	 */
	public static Integer getDrawableIdForEntsorgungsartGrey(
			Entsorgungsart entsorgungsart) {
		Integer drawableId = null;

		if (entsorgungsart.getBezeichnung().equalsIgnoreCase(
				MuellGuideMsApplication.getContext().getResources()
						.getString(R.string.db_entsorgungsart_value_altglas))) {
			// Altglas
			drawableId = R.drawable.altglas_grey;
		} else if (entsorgungsart
				.getBezeichnung()
				.equalsIgnoreCase(
						MuellGuideMsApplication
								.getContext()
								.getResources()
								.getString(
										R.string.db_entsorgungsart_value_altkleider))) {
			// Altkleider
			drawableId = R.drawable.altkleider_grey;
		} else if (entsorgungsart
				.getBezeichnung()
				.equalsIgnoreCase(
						MuellGuideMsApplication
								.getContext()
								.getResources()
								.getString(
										R.string.db_entsorgungsart_value_elektrokleingeraete))) {
			// Elektrokleingeräte
			drawableId = R.drawable.elektrokleingeraet_grey;
		} else {
			drawableId = R.drawable.ic_launcher;
		}

		return drawableId;
	}
}
