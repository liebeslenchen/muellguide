package mawi.muellguidems.parseobjects;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Mappingobjekt für die Parse Klasse "OeffungszeitenRecyclinghof".
 */
@ParseClassName("OeffungszeitenRecyclinghof")
public class OeffungszeitenRecyclinghof extends ParseObject {

	public OeffungszeitenRecyclinghof() {
	}

	public String getObjectId() {
		return getObjectId();
	}

	public String getEnde() {
		return getString("ende");
	}

	public String getFkStandort() {
		return getString("fkStandort");
	}

	public String getStart() {
		return getString("start");
	}

	public String getWochentag() {
		return getString("wochentag");
	}

	public static ParseQuery<OeffungszeitenRecyclinghof> getQuery() {
		return ParseQuery.getQuery(OeffungszeitenRecyclinghof.class);
	}
}
