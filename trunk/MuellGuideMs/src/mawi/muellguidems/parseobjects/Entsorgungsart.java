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

	public String getId() {
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
	 * Entsorgungsartobjekt mit gegebener Id erzeugen. Wird für ParseQuery
	 * benötigt, wenn man z.B. mit where... Pointer prüft. Der id-String kann
	 * dort nicht direkt übergeben werden.
	 * 
	 * @param objectId
	 * @return Entsorgungsart
	 */
	public static Entsorgungsart createWithoutDataByObjectId(String objectId) {
		return (Entsorgungsart) createWithoutData("Entsorgungsart", objectId);
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
