package mawi.muellguidems.parseobjects;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Mappingobjekt für die Parse Klasse "OeffungszeitenContainer".
 */
@ParseClassName("OeffungszeitenContainer")
public class OeffungszeitenContainer extends ParseObject {

	public OeffungszeitenContainer() {
	}

	public String getObjectId() {
		return getObjectId();
	}

	public String getWochentag() {
		return getString("wochentag");
	}

	public String getEnde() {
		return getString("ende");
	}

	public String getFkEntsorgungsart() {
		return getString("fkEntsorgungsart");
	}

	public String getStart() {
		return getString("start");
	}

	public static ParseQuery<OeffungszeitenContainer> getQuery() {
		return ParseQuery.getQuery(OeffungszeitenContainer.class);
	}
}
