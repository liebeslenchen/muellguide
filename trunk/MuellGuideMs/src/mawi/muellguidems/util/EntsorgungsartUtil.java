package mawi.muellguidems.util;

import java.util.HashMap;
import java.util.List;

import mawi.muellguidems.parseobjects.Entsorgungsart;

import com.parse.ParseException;
import com.parse.ParseQuery;

/**
 * Hilfsklasse, die alle Entsorgungsarten als HashMap vorhält. Der key ist die
 * ObjectId und value ist das entsprechende Entsorgungsartobjekt.
 */
public class EntsorgungsartUtil {

	public static final HashMap<String, Entsorgungsart> ENTSORGUNGSART_HASH_MAP = loadAllEntsorgungsartenHashMap();

	private static HashMap<String, Entsorgungsart> loadAllEntsorgungsartenHashMap() {
		HashMap<String, Entsorgungsart> entsorgungsartMap = new HashMap<String, Entsorgungsart>();
		ParseQuery<Entsorgungsart> queryEntsorgung = Entsorgungsart.getQuery();

		List<Entsorgungsart> entsorgungList;
		try {
			entsorgungList = queryEntsorgung.find();
			for (Entsorgungsart entsorgungsart : entsorgungList) {
				entsorgungsartMap.put(entsorgungsart.getId(), entsorgungsart);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return entsorgungsartMap;
	}

}
