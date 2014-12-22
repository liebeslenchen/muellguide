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

	public String getFkBezirk() {
		return getString("fkBezirk");
	}

	public String getFkEntsorgungsart() {
		return getString("fkEntsorgungsart");
	}

	public ParseGeoPoint getGpsStandort() {
		return getParseGeoPoint("gpsStandort");
	}

	public String getHausnummer() {
		return getString("hausnummer");
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

	public static ParseQuery<Standort> getQuery() {
		return ParseQuery.getQuery(Standort.class);
	}
}
