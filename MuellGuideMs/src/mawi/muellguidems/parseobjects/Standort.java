package mawi.muellguidems.parseobjects;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Mappingobjekt für die Parse Klasse "Standort".
 */
@ParseClassName("Standort")
public class Standort extends ParseObject {

	public Standort() {
	}

	public String getId() {
		return getObjectId();
	}

	public String getBezeichnung() {
		return getString("bezeichnung");
	}

	public ParseObject getBezirk() {
		return getParseObject("fkBezirk");
	}

	public String getBezirkId() {
		return getParseObject("fkBezirk").getObjectId();
	}

	public String getEntsorgungsartId() {
		return getParseObject("fkEntsorgungsart").getObjectId();
	}

	public ParseGeoPoint getGpsStandort() {
		return getParseGeoPoint("gpsStandort");
	}

	public String getHinweis() {
		return getString("hinweis");
	}

	public String getPlz() {
		return getString("plz");
	}

	public String getStrasse() {
		return getString("strasse");
	}

	/**
	 * Standortobjekt mit gegebener Id erzeugen. Wird für ParseQuery benötigt,
	 * wenn man z.B. mit where... Pointer prüft. Der id-String kann dort nicht
	 * direkt übergeben werden.
	 * 
	 * @param objectId
	 * @return Standort
	 */
	public static Standort createWithoutDataByObjectId(String objectId) {
		return (Standort) createWithoutData("Standort", objectId);
	}

	public static ParseQuery<Standort> getQuery() {
		ParseQuery<Standort> query = ParseQuery.getQuery(Standort.class);
		query.include("Bezirk");
		query.include("Entsorgungsart");
		return query;
	}
}
