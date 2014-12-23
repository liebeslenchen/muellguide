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

	public ParseObject getFkEntsorgungsart() {
		return getParseObject("fkEntsorgungsart");
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

	public static ParseQuery<Standort> getQuery() {
		ParseQuery<Standort> query = ParseQuery.getQuery(Standort.class);
		query.include("Bezirk");
		query.include("Entsorgungsart");
		return query;
	}
}
