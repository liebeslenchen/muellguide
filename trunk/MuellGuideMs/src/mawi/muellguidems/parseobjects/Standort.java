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

	public String getObjectId() {
		return getObjectId();
	}

	public String getBezeichnung() {
		return getString("bezeichnung");
	}

	public void setBezeichnung(String value) {
		put("bezeichnung", value);
	}

	public String getFkBezirk() {
		return getString("fkBezirk");
	}

	public void setFkBezirk(String value) {
		put("fkBezirk", value);
	}

	public String getFkEntsorgungsart() {
		return getString("fkEntsorgungsart");
	}

	public void setFkEntsorgungsart(String value) {
		put("fkEntsorgungsart", value);
	}

	public ParseGeoPoint getGpsStandort() {
		return getParseGeoPoint("gpsStandort");
	}

	public void setGpsStandort(ParseGeoPoint value) {
		put("gpsStandort", value);
	}

	public String getHausnummer() {
		return getString("hausnummer");
	}

	public void setHausnummer(String value) {
		put("hausnummer", value);
	}

	public String getHinweis() {
		return getString("hinweis");
	}

	public void setHinweis(String value) {
		put("hinweis", value);
	}

	public String getPlz() {
		return getString("plz");
	}

	public void setPlz(String value) {
		put("plz", value);
	}

	public String getStrasse() {
		return getString("strasse");
	}

	public void setStrasse(String value) {
		put("strasse", value);
	}

	public static ParseQuery<Standort> getQuery() {
		return ParseQuery.getQuery(Standort.class);
	}
}
