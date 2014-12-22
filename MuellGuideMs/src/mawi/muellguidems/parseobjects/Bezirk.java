package mawi.muellguidems.parseobjects;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Mappingobjekt für die Parse Klasse "Bezirk".
 */
@ParseClassName("Bezirk")
public class Bezirk extends ParseObject {

	public Bezirk() {
	}

	public String getId() {
		return getObjectId();
	}

	public String getBezeichnung() {
		return getString("bezeichnung");
	}

	public static ParseQuery<Bezirk> getQuery() {
		return ParseQuery.getQuery(Bezirk.class);
	}
}
