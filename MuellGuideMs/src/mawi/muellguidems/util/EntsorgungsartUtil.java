package mawi.muellguidems.util;

import java.util.HashMap;
import java.util.List;

import mawi.muellguidems.activities.MuellGuideMsApplication;
import mawi.muellguidems.activities.R;
import mawi.muellguidems.parseobjects.Entsorgungsart;
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
		}

		return drawableId;
	}
}
