package mawi.muellguidems.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mawi.muellguidems.activities.MuellGuideMsApplication;
import mawi.muellguidems.activities.R;
import mawi.muellguidems.adapter.AdapterChildItem;
import mawi.muellguidems.adapter.AdapterGroupItem;
import mawi.muellguidems.parseobjects.Entsorgungsart;
import mawi.muellguidems.parseobjects.Gegenstand;
import mawi.muellguidems.parseobjects.Standort;

import com.parse.ParseException;
import com.parse.ParseQuery;

public class DAO {

	public static ArrayList<AdapterGroupItem> getAlleGegenstaendeFuerExpandableAdapter() {
		ParseQuery<Gegenstand> query = Gegenstand.getQuery();
		ArrayList<AdapterGroupItem> result = new ArrayList<AdapterGroupItem>();
		HashMap<String, Entsorgungsart> entsorgungsartMap = EntsorgungsartUtil.ENTSORGUNGSART_HASH_MAP;
		try {
			List<Gegenstand> gegenstandList = query.find();

			for (Gegenstand gegenstand : gegenstandList) {
				AdapterGroupItem groupItem = new AdapterGroupItem();
				groupItem.setId(gegenstand.getId());
				groupItem.setBezeichnung(gegenstand.getBezeichnung());

				// An dieser Stelle wird das jeweilige Entsorungsart-Image
				// gesetzt !
				Entsorgungsart entsorgungsArtdesGegenstands = entsorgungsartMap
						.get(gegenstand.getEntsorgungsartId());

				String test = entsorgungsArtdesGegenstands.getBezeichnung();

				if (entsorgungsArtdesGegenstands
						.getBezeichnung()
						.equalsIgnoreCase(
								MuellGuideMsApplication
										.getContext()
										.getResources()
										.getString(
												R.string.db_entsorgungsart_value_altglas))) {
					// Altglas
					groupItem.setImage(R.drawable.altglas);
				} else if (entsorgungsArtdesGegenstands
						.getBezeichnung()
						.equalsIgnoreCase(
								MuellGuideMsApplication
										.getContext()
										.getResources()
										.getString(
												R.string.db_entsorgungsart_value_altkleider))) {

					// Altkleider-Image setzen
					groupItem.setImage(R.drawable.altkleider);
				} else if (entsorgungsArtdesGegenstands
						.getBezeichnung()
						.equalsIgnoreCase(
								MuellGuideMsApplication
										.getContext()
										.getResources()
										.getString(
												R.string.db_entsorgungsart_value_elektrokleingeraete))) {

					// Elektrokleingeräte-Image setzen
					groupItem.setImage(R.drawable.elektrokleingeraet);
				} else if (entsorgungsArtdesGegenstands
						.getBezeichnung()
						.equalsIgnoreCase(
								MuellGuideMsApplication
										.getContext()
										.getResources()
										.getString(
												R.string.db_entsorgungsart_value_papiermuell))) {

					// Papiermüll-Image setzen
					groupItem.setImage(R.drawable.papiermuell);
				} else if (entsorgungsArtdesGegenstands
						.getBezeichnung()
						.equalsIgnoreCase(
								MuellGuideMsApplication
										.getContext()
										.getResources()
										.getString(
												R.string.db_entsorgungsart_value_gelber_sack))) {

					// Gelber Sack-Image setzen
					groupItem.setImage(R.drawable.gelbersack);
				} else if (entsorgungsArtdesGegenstands
						.getBezeichnung()
						.equalsIgnoreCase(
								MuellGuideMsApplication
										.getContext()
										.getResources()
										.getString(
												R.string.db_entsorgungsart_value_biotonne))) {

					// Biotonne-Image setzen
					groupItem.setImage(R.drawable.biotonne);
				} else if (entsorgungsArtdesGegenstands
						.getBezeichnung()
						.equalsIgnoreCase(
								MuellGuideMsApplication
										.getContext()
										.getResources()
										.getString(
												R.string.db_entsorgungsart_value_restmuell))) {

					// Restmüll-Image setzen
					groupItem.setImage(R.drawable.restmuell);
				}

				ArrayList<AdapterChildItem> childList = new ArrayList<AdapterChildItem>();
				AdapterChildItem child = new AdapterChildItem();
				child.setId(entsorgungsartMap.get(
						gegenstand.getEntsorgungsartId()).getBezeichnung());

				// An dieser Stelle wird der jeweilige Hinweis-Text im Sub-Item
				// gesetzt !
				child.setBezeichnung(entsorgungsartMap.get(
						gegenstand.getEntsorgungsartId()).getBezeichnung());

				childList.add(child);

				groupItem.setChildren(childList);
				result.add(groupItem);

			}
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}

		return result;
	}

	public static ArrayList<HashMap<String, String>> getEntsorgungsartenMitStandortFuerAdapter() {
		try {
			ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
			HashMap<String, Entsorgungsart> entsorgungsarten = EntsorgungsartUtil.ENTSORGUNGSART_HASH_MAP;

			for (Entsorgungsart entsorgungsart : entsorgungsarten.values()) {
				if (entsorgungsart.getHatStandort()) {
					HashMap<String, String> hm = new HashMap<String, String>();
					hm.put("id", entsorgungsart.getId());
					hm.put("bezeichnung", entsorgungsart.getBezeichnung());
					result.add(hm);
				}
			}

			return result;
		} catch (Exception ex) {
			return null;
		}
	}

	public static ArrayList<HashMap<String, String>> getStandortListByIdFuerAdapter(
			String entsorgungsartId) {
		try {
			ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();

			List<Standort> data = getAllStandorteForGivenEntsorgungsart(entsorgungsartId);

			for (Standort standort : data) {
				HashMap<String, String> standortMap = new HashMap<String, String>();
				standortMap.put("id", standort.getId());
				standortMap.put("bezeichnung", standort.getBezeichnung());
				result.add(standortMap);
			}

			return result;

		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Liest für eine gegebene {@link Standort}-ID das entsprechende Objekt aus
	 * Für den weiteren Gebrauch wird hier direkt eine {@link ArrayList}
	 * erzeugt.
	 * 
	 * @param standortId
	 * @return {@link ArrayList} vom Typ {@link Standort}
	 */
	public static ArrayList<Standort> getStandortListById(String standortId) {
		ArrayList<Standort> standorte = new ArrayList<Standort>();
		ParseQuery<Standort> stParseQuery = Standort.getQuery();

		try {
			standorte.add(stParseQuery.get(standortId));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return standorte;
	}

	/**
	 * Gibt eine {@link List} alle {@link Standort}e zurück, ohne
	 * Einschränkungen auf eine bestimmte ID oder {@link Entsorgungsart}
	 * 
	 * @return {@link List} von {@link Standort}e
	 */
	public static List<Standort> getStandortListForAllTypes() {

		ParseQuery<Standort> query = Standort.getQuery();
		List<Standort> standorte = null;
		try {
			standorte = query.find();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return standorte;
	}

	/**
	 * Gibt eine {@link List} aller {@link Standort}e zurück, die zu einer
	 * gegebenen {@link Entsorgungsart}-ID gehören
	 * 
	 * @param entsorgungsartId
	 * @return {@link List} mit {@link Standort}en
	 */
	public static List<Standort> getAllStandorteForGivenEntsorgungsart(
			String entsorgungsartId) {
		ParseQuery<Standort> query = Standort.getQuery();
		List<Standort> standortList = new ArrayList<Standort>();

		try {
			Entsorgungsart entsorgungsartWithoutData = Entsorgungsart
					.createWithoutDataByObjectId(entsorgungsartId);

			// Die Query erwartet ein Entsorgungsartobjekt. Man kann nicht
			// direkt den Id-String vergleichen, da die Spalte vom Typ "Pointer"
			// ist.
			standortList = query.whereEqualTo("fkEntsorgungsart",
					entsorgungsartWithoutData).find();

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return standortList;
	}
}
