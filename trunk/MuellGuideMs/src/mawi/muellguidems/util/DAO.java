package mawi.muellguidems.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mawi.muellguidems.activities.R;
import mawi.muellguidems.adapter.AdapterChildItem;
import mawi.muellguidems.adapter.AdapterGroupItem;
import mawi.muellguidems.adapter.AdapterSingleItem;
import mawi.muellguidems.parseobjects.Bezirk;
import mawi.muellguidems.parseobjects.Entsorgungsart;
import mawi.muellguidems.parseobjects.Gegenstand;
import mawi.muellguidems.parseobjects.OeffungszeitenContainer;
import mawi.muellguidems.parseobjects.OeffungszeitenRecyclinghof;
import mawi.muellguidems.parseobjects.Standort;

import com.parse.ParseGeoPoint;
import com.parse.ParseQuery;

public class DAO {

	public static ArrayList<AdapterSingleItem> getHauptmenueEintraege()
			throws Exception {

		ArrayList<AdapterSingleItem> result = new ArrayList<AdapterSingleItem>();

		// Menüpunkt 1: Mülltrennung
		result.add(new AdapterSingleItem("muelltrennung", "Mülltrennung",
				"Was gehört in welche Tonne?", R.drawable.muelltrennung));

		// Menüpunkt 2: Müllentsorgung
		result.add(new AdapterSingleItem("entsorgung", "Entsorgung",
				"Wo werde ich meinen Müll los?", R.drawable.entsorgung));

		// Menüpunkt 3: Hilfe
		result.add(new AdapterSingleItem("hilfe", "Hilfe",
				"Symbolerklärung und Hinweise", R.drawable.hilfe));

		// Menüpunkt 4: Feedback
		result.add(new AdapterSingleItem("feedback", "Feedback",
				"Vorschläge machen, Wünsche äußern...", R.drawable.feedback));

		// Menüpunkt 5: Über uns
		result.add(new AdapterSingleItem("about", "Über uns",
				"Allgemeine Informationen...", R.drawable.ueber_uns));

		// TODO: Test-Menüpunkt --> vor RELEASE unbedingt entfernen !
		// result.add(new AdapterSingleItem("test", "Testwiese",
		// "Testen und so...", R.drawable.testwiese));

		return result;

	}

	public static ArrayList<AdapterGroupItem> getAlleGegenstaendeFuerExpandableAdapter()
			throws Exception {
		ParseQuery<Gegenstand> query = Gegenstand.getQuery();
		ArrayList<AdapterGroupItem> result = new ArrayList<AdapterGroupItem>();
		HashMap<String, Entsorgungsart> entsorgungsartMap = EntsorgungsartUtil.ENTSORGUNGSART_HASH_MAP;

		// Das Limit wird gesetzt, da sonst nur die ersten 100 Gegenstände
		// geladen werden.
		List<Gegenstand> gegenstandList = query.orderByAscending("bezeichnung")
				.setLimit(200).find();

		for (Gegenstand gegenstand : gegenstandList) {
			AdapterGroupItem groupItem = new AdapterGroupItem();
			groupItem.setId(gegenstand.getId());
			groupItem.setBezeichnung(gegenstand.getBezeichnung());

			// An dieser Stelle wird das jeweilige Entsorungsart-Image
			// gesetzt !
			Entsorgungsart entsorgungsArtdesGegenstands = entsorgungsartMap
					.get(gegenstand.getEntsorgungsartId());

			// Images je nach Entsorgungsart setzen
			int drawbleId = EntsorgungsartUtil
					.getDrawableIdForEntsorgungsart(entsorgungsArtdesGegenstands);
			groupItem.setImage(drawbleId);

			ArrayList<AdapterChildItem> childList = new ArrayList<AdapterChildItem>();
			AdapterChildItem child = new AdapterChildItem();
			child.setId(entsorgungsartMap.get(gegenstand.getEntsorgungsartId())
					.getBezeichnung());

			// An dieser Stelle wird der jeweilige Hinweis-Text im Sub-Item
			// gesetzt !
			String childBezeichnung = "Entsorgungsart: "
					+ entsorgungsartMap.get(gegenstand.getEntsorgungsartId())
							.getBezeichnung();

			try {
				/*
				 * Warum try/catch? Wenn 'Hinweis' null (in PARSE: undefined)
				 * ist, kommt es zum Absturz! (Ist noch nicht geklärt, warum das
				 * so ist)
				 */
				if (!gegenstand.getHinweis().equals("")) {
					childBezeichnung += "\r\nHinweis: "
							+ gegenstand.getHinweis();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			child.setBezeichnung(childBezeichnung);

			childList.add(child);

			groupItem.setChildren(childList);
			result.add(groupItem);

		}

		return result;
	}

	public static ArrayList<AdapterSingleItem> getEntsorgungsartenMitStandortFuerAdapter()
			throws Exception {

		ArrayList<AdapterSingleItem> result = new ArrayList<AdapterSingleItem>();
		HashMap<String, Entsorgungsart> entsorgungsarten = EntsorgungsartUtil.ENTSORGUNGSART_HASH_MAP;

		for (Entsorgungsart entsorgungsart : entsorgungsarten.values()) {
			if (entsorgungsart.getHatStandort()) {
				AdapterSingleItem item = new AdapterSingleItem(
						entsorgungsart.getId(),
						entsorgungsart.getBezeichnung(), null, 0);

				int drawbleId = EntsorgungsartUtil
						.getDrawableIdForEntsorgungsart(entsorgungsart);
				item.setImage(drawbleId);

				result.add(item);
			}
		}

		return result;

	}

	public static ArrayList<AdapterSingleItem> getStandortListByIdFuerAdapter(
			String entsorgungsartId) throws Exception {

		ArrayList<AdapterSingleItem> result = new ArrayList<AdapterSingleItem>();
		HashMap<String, Entsorgungsart> entsorgungsarten = EntsorgungsartUtil.ENTSORGUNGSART_HASH_MAP;
		Entsorgungsart entsorgungsart = entsorgungsarten.get(entsorgungsartId);

		List<Standort> data = getAllStandorteForGivenEntsorgungsart(entsorgungsartId);

		for (Standort standort : data) {
			AdapterSingleItem item = new AdapterSingleItem(standort.getId(),
					standort.getBezeichnung(), null, 0);

			// Abhänig davon, ob GPS-Koordinaten vorhanden sind ist das Icon
			// grün oder grau
			ParseGeoPoint geoPoint = standort.getGpsStandort();
			int drawbleId;
			if (geoPoint != null) {
				drawbleId = EntsorgungsartUtil
						.getDrawableIdForEntsorgungsart(entsorgungsart);
			} else {
				drawbleId = EntsorgungsartUtil
						.getDrawableIdForEntsorgungsartGrey(entsorgungsart);
			}

			item.setImage(drawbleId);

			result.add(item);
		}

		return result;

	}

	/**
	 * Liest für eine gegebene {@link Standort}-ID das entsprechende Objekt aus
	 * Für den weiteren Gebrauch wird hier direkt eine {@link ArrayList}
	 * erzeugt.
	 * 
	 * @param standortId
	 * @return {@link ArrayList} vom Typ {@link Standort}
	 */
	public static ArrayList<Standort> getStandortListById(String standortId)
			throws Exception {
		ArrayList<Standort> standorte = new ArrayList<Standort>();
		standorte.add(getStandortById(standortId));
		return standorte;
	}

	/**
	 * Liest für eine gegebene {@link Standort}-ID das entsprechende
	 * Standort-Objekt aus.
	 * 
	 * @param standortId
	 * @return {@link Standort} oder null, falls ID nicht vorhanden ist
	 */
	public static Standort getStandortById(String standortId) throws Exception {
		Standort standort = null;
		ParseQuery<Standort> stParseQuery = Standort.getQuery();

		standort = stParseQuery.get(standortId);

		return standort;
	}

	/**
	 * Gibt eine {@link List} alle {@link Standort}e zurück, ohne
	 * Einschränkungen auf eine bestimmte ID oder {@link Entsorgungsart}
	 * 
	 * @return {@link List} von {@link Standort}e
	 */
	public static List<Standort> getStandortListForAllTypes() throws Exception {

		ParseQuery<Standort> query = Standort.getQuery();
		List<Standort> standorte = null;

		standorte = query.find();

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
			String entsorgungsartId) throws Exception {
		ParseQuery<Standort> query = Standort.getQuery();
		List<Standort> standortList = new ArrayList<Standort>();

		Entsorgungsart entsorgungsartWithoutData = Entsorgungsart
				.createWithoutDataByObjectId(entsorgungsartId);

		// Die Query erwartet ein Entsorgungsartobjekt. Man kann nicht
		// direkt den Id-String vergleichen, da die Spalte vom Typ "Pointer"
		// ist.
		// Das Limit wird gesetzt damit man alle Standorte bekommt.
		// Ansonsten werden nur die ersten 100 zurückgegeben. Relevant
		// für Altglas, da 290 Standort eingetragen sind.
		standortList = query
				.whereEqualTo("fkEntsorgungsart", entsorgungsartWithoutData)
				.orderByAscending("bezeichnung").setLimit(350).find();

		return standortList;
	}

	/**
	 * Liefert ein Bezirksobjekt für eine bestimmte BezirksId zurück.
	 * 
	 * @param bezirkId
	 * @return {@link Bezirk}
	 */
	public static Bezirk getBezirkById(String bezirkId) throws Exception {
		Bezirk bezirk = null;
		ParseQuery<Bezirk> parseQuery = Bezirk.getQuery();

		bezirk = parseQuery.get(bezirkId);

		return bezirk;
	}

	/**
	 * Liest die Öffnungszeiten für die Container aus und gibt Sie aufbereitet
	 * als {@link String} zurück
	 * 
	 * @param entsorgungsartId
	 * @return String, der die aufbereiteten Öffnungszeiten enthält
	 */
	public static String getContainerOeffnungszeitenAufbereitet(
			String entsorgungsartId) throws Exception {

		List<OeffungszeitenContainer> oeffnungszeitList = getContainerOeffnungszeitenList(entsorgungsartId);

		String oeffnungszeitenAufbereitet = "";
		for (OeffungszeitenContainer oeffungszeitenContainer : oeffnungszeitList) {
			oeffnungszeitenAufbereitet += oeffungszeitenContainer
					.getWochentag()
					+ ": "
					+ oeffungszeitenContainer.getStart()
					+ " - " + oeffungszeitenContainer.getEnde() + " Uhr \r\n";
		}

		return oeffnungszeitenAufbereitet;
	}

	/**
	 * Liest die Öffnungszeiten für Container anhand der {@link Entsorgungsart}
	 * -ID aus
	 * 
	 * @param entsorgungsartId
	 * @return {@link List} vom Typ {@link String} mit Öffnungszeiten
	 */
	public static List<OeffungszeitenContainer> getContainerOeffnungszeitenList(
			String entsorgungsartId) throws Exception {
		ParseQuery<OeffungszeitenContainer> query = OeffungszeitenContainer
				.getQuery();
		List<OeffungszeitenContainer> oeffnungszeitList = new ArrayList<OeffungszeitenContainer>();

		Entsorgungsart entsorgungsartWithoutData = Entsorgungsart
				.createWithoutDataByObjectId(entsorgungsartId);

		// Die Query erwartet ein Entsorgungsartobjekt. Man kann nicht
		// direkt den Id-String vergleichen, da die Spalte vom Typ "Pointer"
		// ist.
		oeffnungszeitList = query.whereEqualTo("fkEntsorgungsart",
				entsorgungsartWithoutData).find();

		return oeffnungszeitList;

	}

	/**
	 * Gibt alle Öffnungszeiten-Tage von Recyclinghöfen als String aufbereitet
	 * zurück
	 * 
	 * @param recyclinghofId
	 * @return {@link String} mit Öffnungszeiten und Umbrüchen
	 * @throws Exception
	 */
	public static String getRecyclinghofOeffnungszeitenAufbereitet(
			String recyclinghofId) throws Exception {
		List<OeffungszeitenRecyclinghof> oeffnungszeitList = getRecyclinghofOeffnungszeitenList(recyclinghofId);

		String oeffnungszeitenAufbereitet = "";
		for (OeffungszeitenRecyclinghof oeffungszeiten : oeffnungszeitList) {
			oeffnungszeitenAufbereitet += oeffungszeiten.getWochentag() + ": "
					+ oeffungszeiten.getStart() + " - "
					+ oeffungszeiten.getEnde() + " Uhr \r\n";
		}

		return oeffnungszeitenAufbereitet;
	}

	/**
	 * Liest die Öffnungzeiten für Recyclinghöfe aus und gibt sie als
	 * {@link List} vom Typ {@link OeffungszeitenRecyclinghof} zurück
	 * 
	 * @param recyclinghofId
	 * @return {@link List} vom Typ {@link OeffungszeitenRecyclinghof}
	 * @throws Exception
	 */
	public static List<OeffungszeitenRecyclinghof> getRecyclinghofOeffnungszeitenList(
			String recyclinghofId) throws Exception {
		ParseQuery<OeffungszeitenRecyclinghof> query = OeffungszeitenRecyclinghof
				.getQuery();
		List<OeffungszeitenRecyclinghof> oeffnungszeitList = new ArrayList<OeffungszeitenRecyclinghof>();

		Standort standortWithoutData = Standort
				.createWithoutDataByObjectId(recyclinghofId);

		// Die Query erwartet ein Entsorgungsartobjekt. Man kann nicht
		// direkt den Id-String vergleichen, da die Spalte vom Typ "Pointer"
		// ist.
		oeffnungszeitList = query.whereEqualTo("fkStandort",
				standortWithoutData).find();
		return oeffnungszeitList;

	}
}
