package mawi.muellguidems.parseobjects;

import mawi.muellguidems.util.EntsorgungsartEnum;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

/**
 * Mappingobjekt für die Parse Klasse "Entsorgungsart".
 */
@ParseClassName("Entsorgungsart")
public class Entsorgungsart extends ParseObject {

	public Entsorgungsart() {
	}

	public String getObjectId() {
		return getObjectId();
	}

	public String getBezeichnung() {
		return getString("bezeichnung");
	}

	public Boolean getHatStandort() {
		return getBoolean("hatStandort");
	}

	public String getHinweis() {
		return getString("hinweis");
	}

	/**
	 * Mapping zwischen Parse und EntsorgungsartEnum
	 * 
	 * @return EntsorgungsartEnum
	 */
	public EntsorgungsartEnum getEnumEntsorgungsart() {
		return EntsorgungsartEnum.getEnumEntsorgungsart(getBezeichnung());
	}

	public static ParseQuery<Entsorgungsart> getQuery() {
		return ParseQuery.getQuery(Entsorgungsart.class);
	}
}
